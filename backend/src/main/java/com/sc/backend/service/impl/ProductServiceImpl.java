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
	public List<ProductEntity> listProductId(Long customerId) throws Exception{
		return productRepository.findByCustomerId(customerId);
	}
	
	@Override
	public ProductEntity listOneProductId(Long productId) throws Exception{
		return productRepository.findByProductId(productId);
	}
	
	@Override
	public ProductEntity addProduct(ProductEntity productEntity) throws Exception{
		return productRepository.save(productEntity);
	}
	
	@Override
	public ProductEntity changeState(ProductEntity productEntity) throws Exception{
		return productRepository.save(productEntity);
	}
	
	@Override
	public ProductEntity updateBalance(ProductEntity productEntity) throws Exception{
		return productRepository.save(productEntity);
	}
	
	@Override
	public List<ProductEntity> listIdOtherAvailableProducts(Long customerId, Long productId) throws Exception{
		return productRepository.findByStateNotAndUserIdNotAndProductIdNot("Cancelado", customerId, productId);
	}
	
	@Override
	public ProductEntity cancelProduct(ProductEntity productEntity) throws Exception{
		return productRepository.save(productEntity);
	}
	
	@Override 
	public ProductEntity addToBalance(ProductEntity productEntity, int movement) throws Exception{
		return productRepository.save(productEntity);
	}
	
	@Override
	public ProductEntity withdrawToBalance(ProductEntity productEntity, int movement) throws Exception{
		return productRepository.save(productEntity);
	}

	@Override
	public ProductEntity findByCustomerIdAndProductId(Long customerId, Long productId) throws Exception {
		return productRepository.findByCustomerIdAndProductId(customerId, productId);
	}
	
}
