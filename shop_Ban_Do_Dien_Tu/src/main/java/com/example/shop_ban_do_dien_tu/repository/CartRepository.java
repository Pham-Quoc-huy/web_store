    package com.example.shop_ban_do_dien_tu.repository;

    import com.example.shop_ban_do_dien_tu.model.Cart;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.Optional;

    public interface CartRepository extends JpaRepository<Cart, Long> {
        Optional<Cart> findByUserId(Long userId);
        boolean existsByUserId(Long userId);
    }
