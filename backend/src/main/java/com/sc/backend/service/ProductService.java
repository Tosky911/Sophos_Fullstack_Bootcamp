package com.sc.backend.service;

import java.util.List;

import com.sc.backend.entity.ProductEntity;

public interface ProductService{

	public List<ProductEntity> listProductId(Long customerId);
	public ProductEntity listOneProductId(Long productId);
	public ProductEntity addProduct(ProductEntity productEntity, Long customerId);
	public ProductEntity changeState(ProductEntity productEntity);
	public ProductEntity updateBalance(ProductEntity productEntity);
	public List<ProductEntity> listIdOtherAvailableProducts(Long customerId, Long productId);
	public ProductEntity cancelProduct(ProductEntity productEntity);
	public ProductEntity addToBalance(ProductEntity productEntity, int movement);
	public ProductEntity withdrawToBalance(ProductEntity productEntity, int movement);
	

}
