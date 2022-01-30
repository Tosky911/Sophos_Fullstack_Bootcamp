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
	
	//Producto (segun productId) de un cliente(segun customerId)
	@Query(value = "select * from products where (customer_id=?1) AND (product_id=?2)", nativeQuery= true)
	ProductEntity findByCustomerIdAndProductId(Long customerId, Long productId);
	
	//Los productos de un cliente (segun customerId) que sean diferentes del ingresado (segun productId)
	@Query(value = "select * from products where (state!=?1) AND (customer_id=?2) AND NOT (product_id=?3)", nativeQuery = true)
	List<ProductEntity> findByStateAndUserIdAndNotProductId(String state, Long customerId, Long productId);
}
