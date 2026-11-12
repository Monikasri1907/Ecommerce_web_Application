import React, {useEffect,useMemo,useState} from 'react';
import {request} from './api';

export default function App(){
  const [products,setProducts]=useState([]);
  const [cart,setCart]=useState([]);
  const [user,setUser]=useState(()=>JSON.parse(localStorage.getItem('shopUser')||'null'));
  const [page,setPage]=useState('shop');
  const [message,setMessage]=useState('');
  const [orders,setOrders]=useState([]);
  const [form,setForm]=useState({name:'',email:'',password:''});
  const [product,setProduct]=useState({name:'',description:'',price:'',stock:''});

  async function loadProducts(){try{setProducts(await request('/products'));}catch(e){setMessage(e.message);}}
  async function loadOrders(){try{setOrders(await request('/orders'));}catch(e){setMessage(e.message);}}
  useEffect(()=>{loadProducts();},[]);
  const total=useMemo(()=>cart.reduce((s,p)=>s+p.price*p.qty,0),[cart]);

  function login(){
    request('/auth/login',{method:'POST',body:JSON.stringify({email:form.email,password:form.password})})
      .then(u=>{setUser(u);localStorage.setItem('shopUser',JSON.stringify(u));setMessage('Welcome '+u.name);})
      .catch(e=>setMessage(e.message));
  }
  function register(){
    request('/auth/register',{method:'POST',body:JSON.stringify(form)})
      .then(()=>{setMessage('Registered successfully. Please login.');setPage('login');})
      .catch(e=>setMessage(e.message));
  }
  function add(p){setCart(old=>{const found=old.find(x=>x.id===p.id);return found?old.map(x=>x.id===p.id?{...x,qty:x.qty+1}:x):[...old,{...p,qty:1}];});setMessage('Added to cart');}
  function checkout(){
    if(!user){setMessage('Please login before checkout');setPage('login');return;}
    request('/orders',{method:'POST',body:JSON.stringify({customerName:user.name,items:cart.map(x=>`${x.name} x ${x.qty}`).join(', '),total})})
      .then(()=>{setCart([]);setMessage('Order placed successfully');setPage('orders');loadOrders();})
      .catch(e=>setMessage(e.message));
  }
  async function saveProduct(e){
    e.preventDefault();
    try{await request('/products',{method:'POST',body:JSON.stringify({...product,price:Number(product.price),stock:Number(product.stock)})});setProduct({name:'',description:'',price:'',stock:''});loadProducts();setMessage('Product added');}
    catch(err){setMessage(err.message);}
  }
  async function deleteProduct(id){try{await request('/products/'+id,{method:'DELETE'});loadProducts();}catch(e){setMessage(e.message);}}
  function logout(){setUser(null);localStorage.removeItem('shopUser');setPage('shop');}

  return <div className="app">
    <header><h1>ShopEasy</h1><nav>
      <button onClick={()=>setPage('shop')}>Shop</button>
      <button onClick={()=>setPage('cart')}>Cart ({cart.reduce((s,p)=>s+p.qty,0)})</button>
      <button onClick={()=>{setPage('orders');loadOrders();}}>Orders</button>
      {user?.role==='ADMIN'&&<button onClick={()=>setPage('admin')}>Admin</button>}
      {user?<button onClick={logout}>Logout ({user.name})</button>:<button onClick={()=>setPage('login')}>Login</button>}
    </nav></header>
    {message&&<p className="message">{message}<button onClick={()=>setMessage('')}>×</button></p>}
    {page==='shop'&&<><h2>Featured Products</h2><div className="grid">{products.map(p=><article className="product" key={p.id}><div className="photo">🛍️</div><h3>{p.name}</h3><p>{p.description}</p><strong>₹{p.price}</strong><p>Stock: {p.stock}</p><button onClick={()=>add(p)}>Add to cart</button></article>)}</div></>}
    {page==='login'&&<section className="panel"><h2>Login</h2><input placeholder="Email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})}/><input type="password" placeholder="Password" value={form.password} onChange={e=>setForm({...form,password:e.target.value})}/><button onClick={login}>Login</button><p>New user? <button className="link" onClick={()=>setPage('register')}>Register</button></p></section>}
    {page==='register'&&<section className="panel"><h2>Create account</h2><input placeholder="Name" value={form.name} onChange={e=>setForm({...form,name:e.target.value})}/><input placeholder="Email" value={form.email} onChange={e=>setForm({...form,email:e.target.value})}/><input type="password" placeholder="Password" value={form.password} onChange={e=>setForm({...form,password:e.target.value})}/><button onClick={register}>Register</button></section>}
    {page==='cart'&&<section className="panel"><h2>Your Cart</h2>{cart.length===0?<p>Your cart is empty.</p>:cart.map(p=><p key={p.id}>{p.name} × {p.qty} — ₹{p.price*p.qty} <button onClick={()=>setCart(c=>c.filter(x=>x.id!==p.id))}>Remove</button></p>)}<h3>Total: ₹{total}</h3><button disabled={!cart.length} onClick={checkout}>Checkout</button></section>}
    {page==='orders'&&<section className="panel"><h2>Order Tracking</h2>{orders.length===0?<p>No orders found.</p>:orders.map(o=><article className="order" key={o.id}><b>Order #{o.id}</b><p>{o.items}</p><p>Total: ₹{o.total}</p><span>Status: {o.status}</span></article>)}</section>}
    {page==='admin'&&<section className="panel"><h2>Admin — Product Management</h2><form onSubmit={saveProduct}><input required placeholder="Product name" value={product.name} onChange={e=>setProduct({...product,name:e.target.value})}/><input placeholder="Description" value={product.description} onChange={e=>setProduct({...product,description:e.target.value})}/><input required type="number" min="0" placeholder="Price" value={product.price} onChange={e=>setProduct({...product,price:e.target.value})}/><input required type="number" min="0" placeholder="Stock" value={product.stock} onChange={e=>setProduct({...product,stock:e.target.value})}/><button>Add product</button></form>{products.map(p=><p key={p.id}>{p.name} — ₹{p.price} <button onClick={()=>deleteProduct(p.id)}>Delete</button></p>)}</section>}
    <footer>ShopEasy • E-Commerce Learning Project</footer>
  </div>;
}
