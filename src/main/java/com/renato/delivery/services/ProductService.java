package com.renato.delivery.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renato.delivery.dto.ProductDTO;
import com.renato.delivery.entities.Product;
import com.renato.delivery.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAllProducts(){
		List<Product> list = repository.findAllByOrderByNameAsc();
		return list.stream().map(ProductDTO::new).collect(Collectors.toList());
	}

}


