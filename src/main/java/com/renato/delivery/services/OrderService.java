package com.renato.delivery.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renato.delivery.dto.OrderDTO;
import com.renato.delivery.dto.ProductDTO;
import com.renato.delivery.entities.Order;
import com.renato.delivery.entities.Product;
import com.renato.delivery.enumerations.OrderStatus;
import com.renato.delivery.repositories.OrderRepository;
import com.renato.delivery.repositories.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public List<OrderDTO> findAllOrders() {
		List<Order> list = orderRepository.findOrdersWithProducts();
		return list.stream().map(OrderDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLatitude(),
				Instant.now(), OrderStatus.PENDING);
		
		for (ProductDTO p : dto.getProducts()) {
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
		order = orderRepository.save(order);
		return new OrderDTO(order);
	}
	
	@Transactional
	public OrderDTO setDelivered(Long id) {
		Order order = orderRepository.getOne(id);
		order.setStatus(OrderStatus.DELIVERED);
		order = orderRepository.save(order);
		return new OrderDTO(order);
	}
}
