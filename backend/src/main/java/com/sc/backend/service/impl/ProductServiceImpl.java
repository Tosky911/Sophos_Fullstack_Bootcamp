package com.sc.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.ProductEntity;
import com.sc.backend.repository.ProductRepository;
import com.sc.backend.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	TransactionServiceImpl transactionServiceImpl;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<ProductEntity> listProductId(Long customerId){
		return productRepository.findByCustomerId(customerId);
	}
	
	@Override
	public ProductEntity listOneProductId(Long productId) {
		return productRepository.findByProductId(productId);
	}
	
	@Override
	public ProductEntity addProduct(ProductEntity productEntity, Long customerId) {
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
	public List<ProductEntity> listIdOtherAvailableProducts(Long customerId, Long productId){
		return productRepository.findByStateNotAndUserIdNotAndProductIdNot("Cancelado", customerId, productId);
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
