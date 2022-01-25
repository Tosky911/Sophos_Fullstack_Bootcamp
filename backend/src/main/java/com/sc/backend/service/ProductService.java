package com.sc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.ProductEntity;
import com.sc.backend.repository.ProductRepository;
import com.sc.backend.service.impl.ProductServiceImpl;
import com.sc.backend.service.impl.InterfaceTransactionService;

@Service
public class ProductService implements ProductServiceImpl{
	
	@Autowired
	InterfaceTransactionService transactionService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<ProductEntity> listProductId(Long userId){
		return productRepository.findByUserId(userId);
	}
	
	@Override
	public ProductEntity listOneProductId(Long productId) {
		return productRepository.findByProductId(productId);
	}
	
	@Override
	public ProductEntity addProduct(ProductEntity productEntity, Long userId) {
		return productRepository.save(productEntity);
	}
	
	@Override
	public ProductEntity changeState(ProductEntity productEntity) {
		return productRepository.save(productEntity);
	}
	
	@Override
	public ProductEntity updateBalance(ProductEntity productEntity) {
		return productRepository.save(productEntity);
	}
	
	@Override
	public List<ProductEntity> listIdOtherAvailableProducts(Long userId, Long productId){
		return productRepository.findByStateNotAndUserIdNotAndProductIdNot("Cancelado", userId, productId);
	}
	
	@Override
	public ProductEntity cancelProduct(ProductEntity productEntity) {
		return productRepository.save(productEntity);
	}
	
	@Override 
	public ProductEntity addToBalance(ProductEntity productEntity, int movement) {
		return productRepository.save(productEntity);
	}
	
	@Override
	public ProductEntity withdrawToBalance(ProductEntity productEntity, int movement) {
		return productRepository.save(productEntity);
	}
	
	
}
