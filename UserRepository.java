package com.ecommerce.repository;
import com.ecommerce.model.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<StoreUser, Long> {
    Optional<StoreUser> findByEmail(String email);
}
