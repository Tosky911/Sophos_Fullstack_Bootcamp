package com.sc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.Product;

@Service
public class ProductService implements InterfaceProductService{
	
	@Autowired
	InterfaceTransactionService transactionService;
	
	@Autowired
	private InterfaceProduct interfaceProduct;
	
	@Override
	public List<Product> listProductId(Long userId){
		return interfaceProduct.findByUserId(userId);
	}
	
	@Override
	public Product listOneProductId(Long productId) {
		return interfaceProduct.findByProductId(productId);
	}
	
	@Override
	public Product addProduct(Product product, Long userId) {
		return interfaceProduct.save(product);
	}
	
	@Override
	public Product changeState(Product product) {
		return interfaceProduct.save(product);
	}
	
	@Override
	public Product updateBalance(Product product) {
		return interfaceProduct.save(product);
	}
	
	@Override
	public List<Product> listIdOtherAvailableProducts(Long userId, Long productId){
		return interfaceProduct.findByStateNotAndUserIdNotAndProductIdNot("Cancelado", userId, productId);
	}
	
	@Override
	public Product cancelProduct(Product product) {
		return interfaceProduct.save(Product);
	}
	
	@Override 
	public Product addToBalance(Product product, int movement) {
		return interfaceProduct.save(Product);
	}
	
	@Override
	public Product withdrawToBalance(Product product, int movement) {
		return interfaceProduct.save(Product);
	}
	
	
}
