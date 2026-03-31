package com.example.InventarioGangazo2.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.InventarioGangazo2.dto.OrdenItemsRequestDTO;
import com.example.InventarioGangazo2.dto.OrdenItemsResponseDTO;
import com.example.InventarioGangazo2.dto.OrderRequestDTO;
import com.example.InventarioGangazo2.dto.OrderResponseDTO;
import com.example.InventarioGangazo2.entity.OrdenItems;
import com.example.InventarioGangazo2.entity.Order;
import com.example.InventarioGangazo2.entity.Products;
import com.example.InventarioGangazo2.repository.OrdenItemsRepository;
import com.example.InventarioGangazo2.repository.OrderRepository;
import com.example.InventarioGangazo2.repository.ProductsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingService {
    private final OrdenItemsRepository ordenItemsRepository;
    private final OrderRepository orderRepository;
    private final ProductsRepository productsRepository;

     public OrderResponseDTO Makepurchase(OrderRequestDTO request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("User is required");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Order must contain at least one item");
        }

        Order order = new Order();
        order.setDate(new Timestamp(System.currentTimeMillis()));
        order.setTotal(0.0);
        order.setUsers_id(request.getUserId());

        order = orderRepository.save(order);

        double total = 0;   
        List<OrdenItemsResponseDTO> itemsResponse = new ArrayList<>();

        for (OrdenItemsRequestDTO item : request.getItems()) {
            if (item.getProductId() == null) {
                throw new RuntimeException("Product is required");
            }
            if (item.getQuantity() <= 0) {
                throw new RuntimeException("Quantity must be greater than 0");
            }
            Products product = productsRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }

            product.setStock(product.getStock() - item.getQuantity());
            productsRepository.save(product);

            OrdenItems orderItem = new OrdenItems();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());

            ordenItemsRepository.save(orderItem);

            total += product.getPrice() * item.getQuantity();

            OrdenItemsResponseDTO itemResponse = new OrdenItemsResponseDTO();
            itemResponse.setProductId(product.getId());
            itemResponse.setName(product.getName());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(product.getPrice());

            itemsResponse.add(itemResponse); 
        }

        order.setTotal(total);
        orderRepository.save(order);

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setDate(order.getDate());
        response.setTotal(order.getTotal());
        response.setItems(itemsResponse);

        return response;
    }

    public List<OrderResponseDTO> Purchasehistory(Long userId) {
        if (userId == null) {
            throw new RuntimeException("User is required");
        }
        List<Order> orders = orderRepository.findByUsuario_id(userId);
        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {
            List<OrdenItems> items = ordenItemsRepository.findByPedidoId(order.getId());
            List<OrdenItemsResponseDTO> itemsResponse = new ArrayList<>();
            for (OrdenItems item : items) {

                Products product = productsRepository.findById(item.getProductId())
                        .orElse(null);

                OrdenItemsResponseDTO dto = new OrdenItemsResponseDTO();
                dto.setProductId(item.getProductId());
                dto.setName(product != null ? product.getName() : "without name");
                dto.setQuantity(item.getQuantity());
                dto.setPrice(item.getPrice());

                itemsResponse.add(dto);
            }
            OrderResponseDTO response = new OrderResponseDTO();
            response.setId(order.getId());
            response.setDate(order.getDate());
            response.setTotal(order.getTotal());
            response.setItems(itemsResponse);

            responseList.add(response);
        }
        return responseList;
    }

    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {

            List<OrdenItems> items = ordenItemsRepository.findByPedidoId(order.getId());
            List<OrdenItemsResponseDTO> itemsResponse = new ArrayList<>();
            for (OrdenItems item : items) {

                Products product = productsRepository.findById(item.getProductId())
                        .orElse(null);

                OrdenItemsResponseDTO dto = new OrdenItemsResponseDTO();
                dto.setProductId(item.getProductId());
                dto.setName(product != null ? product.getName() : "without name");
                dto.setQuantity(item.getQuantity());
                dto.setPrice(item.getPrice());

                itemsResponse.add(dto);
            }
            OrderResponseDTO response = new OrderResponseDTO();
            response.setId(order.getId());
            response.setDate(order.getDate());
            response.setTotal(order.getTotal());
            response.setItems(itemsResponse);

            responseList.add(response);
        }
        return responseList;
    }

}