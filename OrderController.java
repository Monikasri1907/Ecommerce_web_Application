package com.ecommerce.controller;

import com.ecommerce.model.StoreOrder;
import com.ecommerce.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository repo;
    public OrderController(OrderRepository repo){this.repo=repo;}

    @GetMapping public List<StoreOrder> all(){return repo.findAll();}
    @PostMapping public StoreOrder place(@RequestBody StoreOrder order){
        order.setStatus("PLACED"); return repo.save(order);
    }
    @PutMapping("/{id}/status") public StoreOrder status(@PathVariable Long id,@RequestBody StatusRequest request){
        StoreOrder order=repo.findById(id).orElseThrow();
        order.setStatus(request.status()); return repo.save(order);
    }
    public record StatusRequest(String status) {}
}
