package com.sc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.Product;
import com.sc.backend.interfaceService.InterfaceProductService;
import com.sc.backend.interfaceService.InterfaceTransactionService;
import com.sc.backend.repository.ProductRepository;

@Service
public class ProductService implements InterfaceProductService{
	
	@Autowired
	InterfaceTransactionService transactionService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> listProductId(Long userId){
		return productRepository.findByUserId(userId);
	}
	
	@Override
	public Product listOneProductId(Long productId) {
		return productRepository.findByProductId(productId);
	}
	
	@Override
	public Product addProduct(Product product, Long userId) {
		return productRepository.save(product);
	}
	
	@Override
	public Product changeState(Product product) {
		return productRepository.save(product);
	}
	
	@Override
	public Product updateBalance(Product product) {
		return productRepository.save(product);
	}
	
	@Override
	public List<Product> listIdOtherAvailableProducts(Long userId, Long productId){
		return productRepository.findByStateNotAndUserIdNotAndProductIdNot("Cancelado", userId, productId);
	}
	
	@Override
	public Product cancelProduct(Product product) {
		return productRepository.save(product);
	}
	
	@Override 
	public Product addToBalance(Product product, int movement) {
		return productRepository.save(product);
	}
	
	@Override
	public Product withdrawToBalance(Product product, int movement) {
		return productRepository.save(product);
	}
	
	
}
