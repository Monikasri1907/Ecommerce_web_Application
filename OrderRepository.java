package com.ecommerce.repository;
import com.ecommerce.model.StoreOrder;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<StoreOrder, Long> {}
