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

        Order order = new Order();
        order.setDate(new Timestamp(System.currentTimeMillis()));
        order.setTotal(0.0);

        order.setUsers_id(request.getUserId());

        order = orderRepository.save(order);

        double total = 0;   

        List<OrdenItemsResponseDTO> itemsResponse = new ArrayList<>();

        for (OrdenItemsRequestDTO item : request.getItems()) {

            Products product = productsRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto No Encontrado"));

            if (product.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente");
            }

            product.setStock(product.getStock() - item.getCantidad());
            productsRepository.save(product);

            OrdenItems orderItem = new OrdenItems();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(item.getCantidad());
            orderItem.setPrice(product.getPrice());

            ordenItemsRepository.save(orderItem);

            total += product.getPrice() * item.getCantidad();

            OrdenItemsResponseDTO itemResponse = new OrdenItemsResponseDTO();
            itemResponse.setProductId(product.getId());
            itemResponse.setNombre(product.getName());
            itemResponse.setCantidad(item.getCantidad());
            itemResponse.setPrecio(product.getPrice());

            itemsResponse.add(itemResponse); 
        }

        order.setTotal(total);
        orderRepository.save(order);

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setFecha(order.getDate());
        response.setTotal(order.getTotal());
        response.setItems(itemsResponse);

        return response;
    }

    public List<OrderResponseDTO> Purchasehistory(Long userId) {

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
            dto.setNombre(product != null ? product.getName() : "Sin nombre");
            dto.setCantidad(item.getQuantity());
            dto.setPrecio(item.getPrice());

            itemsResponse.add(dto);
        }

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setFecha(order.getDate());
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
            dto.setNombre(product != null ? product.getName() : "Sin nombre");
            dto.setCantidad(item.getQuantity());
            dto.setPrecio(item.getPrice());

            itemsResponse.add(dto);
        }

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setFecha(order.getDate());
        response.setTotal(order.getTotal());
        response.setItems(itemsResponse);

        responseList.add(response);
    }

    return responseList;
}

}