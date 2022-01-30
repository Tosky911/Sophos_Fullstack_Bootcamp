package com.sc.backend.service;

import java.util.List;

import com.sc.backend.entity.ProductEntity;

public interface ProductService{

	public List<ProductEntity> findAll(Long customerId) throws Exception;
	public ProductEntity findById(Long productId) throws Exception;
	public ProductEntity save(ProductEntity productEntity) throws Exception;
	public ProductEntity changeState(ProductEntity productEntity) throws Exception;
	public ProductEntity updateBalance(ProductEntity productEntity) throws Exception;
	public List<ProductEntity> listIdOtherAvailableProducts(Long customerId, Long productId) throws Exception;
	public ProductEntity cancelProduct(ProductEntity productEntity) throws Exception;
	public ProductEntity addToBalance(ProductEntity productEntity, int movement) throws Exception;
	public ProductEntity withdrawToBalance(ProductEntity productEntity, int movement) throws Exception;
	public ProductEntity findByCustomerIdAndProductId(Long customerId, Long productId) throws Exception;
	

}
