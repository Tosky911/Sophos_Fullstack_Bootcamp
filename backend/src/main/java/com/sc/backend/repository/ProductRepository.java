package com.sc.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sc.backend.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
	
	//Lista con los productos de un usuario
	List<ProductEntity> findByCustomerId (Long customerId);
	
	//Producto segun Id
	ProductEntity findByProductId(Long productId);
	
	//Query por estado del producto
	@Query(value = "select * from products where (state!=?1) AND NOT (user_id=?2 and product_id=?3)", nativeQuery = true)
	List<ProductEntity> findByStateNotAndUserIdNotAndProductIdNot(String state, Long customerId, Long productId);
}
