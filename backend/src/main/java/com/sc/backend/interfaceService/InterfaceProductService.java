package com.sc.backend.interfaceService;

import java.util.List;

import com.sc.backend.entity.Product;

public interface InterfaceProductService {
	public List<Product> listProductId(Long userId);
	public Product listOneProductId(Long productId);
	public Product addProduct(Product product, Long userId);
	public Product changeState(Product product);
	public Product updateBalance(Product product);
	public List<Product> listIdOtherAvailableProducts(Long userId, Long productId);
	public Product cancelProduct(Product product);
	public Product addToBalance(Product product, int movement);
	public Product withdrawToBalance(Product product, int movement);
	
}
