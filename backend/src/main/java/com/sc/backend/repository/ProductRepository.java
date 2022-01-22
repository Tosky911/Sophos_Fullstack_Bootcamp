package com.sc.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sc.backend.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	//Lista con los productos de un usuario
	List<Product> findByUserId (Long userId);
	
	//Producto segun Id
	Product findByProductId(Long productId);
	
	//Query por estado del producto
	@Query(value = "select * from products where (state!=?1) AND NOT (user_id=?2 and product_id=?3)", nativeQuery = true)
	List<Product> findByStateNotAndUserIdNotAndProductIdNot(String state, Long userId, Long productId);
}
