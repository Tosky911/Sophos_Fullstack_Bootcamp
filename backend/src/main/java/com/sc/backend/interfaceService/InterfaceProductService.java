package com.sc.backend.interfaceService;

import java.util.List;

import com.sc.backend.entity.ProductEntity;

public interface InterfaceProductService {
	public List<ProductEntity> listProductId(Long userId);
	public ProductEntity listOneProductId(Long productId);
	public ProductEntity addProduct(ProductEntity productEntity, Long userId);
	public ProductEntity changeState(ProductEntity productEntity);
	public ProductEntity updateBalance(ProductEntity productEntity);
	public List<ProductEntity> listIdOtherAvailableProducts(Long userId, Long productId);
	public ProductEntity cancelProduct(ProductEntity productEntity);
	public ProductEntity addToBalance(ProductEntity productEntity, int movement);
	public ProductEntity withdrawToBalance(ProductEntity productEntity, int movement);
	
}
