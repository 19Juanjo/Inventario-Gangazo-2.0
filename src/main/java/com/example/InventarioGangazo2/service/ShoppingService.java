package com.example.InventarioGangazo2.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.InventarioGangazo2.dto.OrdenItemsRequestDTO;
import com.example.InventarioGangazo2.dto.OrdenItemsResponseDTO;
import com.example.InventarioGangazo2.dto.OrderRequestDTO;
import com.example.InventarioGangazo2.dto.OrderResponse;
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

    public OrderResponse RealizarCompra(OrderRequestDTO request) {

        Order order = new Order();
        order.setFecha(new Timestamp(System.currentTimeMillis()));
        order.setTotal(0.0);

        order.setUsuario_id(request.getUserId());

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
            orderItem.setPedidoId(order.getId());
            orderItem.setProductoId(product.getId());
            orderItem.setCantidad(item.getCantidad());
            orderItem.setPrecio(product.getPrecio());

            ordenItemsRepository.save(orderItem);

            total += product.getPrecio() * item.getCantidad();

            OrdenItemsResponseDTO itemResponse = new OrdenItemsResponseDTO();
            itemResponse.setProductId(product.getId());
            itemResponse.setNombre(product.getNombre());
            itemResponse.setCantidad(item.getCantidad());
            itemResponse.setPrecio(product.getPrecio());

            itemsResponse.add(itemResponse); 
        }

        order.setTotal(total);
        orderRepository.save(order);

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setFecha(order.getFecha());
        response.setTotal(order.getTotal());
        response.setItems(itemsResponse);

        return response;
    }

    public List<OrderResponse> historial(Long userId) {

    List<Order> orders = orderRepository.findByUsuario_id(userId);

    List<OrderResponse> responseList = new ArrayList<>();

    for (Order order : orders) {

        List<OrdenItems> items = ordenItemsRepository.findByPedidoId(order.getId());

        List<OrdenItemsResponseDTO> itemsResponse = new ArrayList<>();

        for (OrdenItems item : items) {

            Products product = productsRepository.findById(item.getProductoId())
                    .orElse(null);

            OrdenItemsResponseDTO dto = new OrdenItemsResponseDTO();
            dto.setProductId(item.getProductoId());
            dto.setNombre(product != null ? product.getNombre() : "Sin nombre");
            dto.setCantidad(item.getCantidad());
            dto.setPrecio(item.getPrecio());

            itemsResponse.add(dto);
        }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setFecha(order.getFecha());
        response.setTotal(order.getTotal());
        response.setItems(itemsResponse);

        responseList.add(response);
    }

    return responseList;
}
}