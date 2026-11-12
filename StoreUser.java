package com.ecommerce.model;

import jakarta.persistence.*;

@Entity
@Table(name="store_users")
public class StoreUser {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique=true) private String email;
    private String password;
    private String role;

    public StoreUser() {}
    public StoreUser(String name,String email,String password,String role) {
        this.name=name; this.email=email; this.password=password; this.role=role;
    }
    public Long getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getRole(){return role;}
    public void setName(String name){this.name=name;}
    public void setEmail(String email){this.email=email;}
    public void setPassword(String password){this.password=password;}
    public void setRole(String role){this.role=role;}
}
