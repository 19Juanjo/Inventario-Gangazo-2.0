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
import com.example.InventarioGangazo2.entity.ShoppingCart;
import com.example.InventarioGangazo2.entity.ShoppingCartItem;
import com.example.InventarioGangazo2.entity.Users;
import com.example.InventarioGangazo2.repository.OrdenItemsRepository;
import com.example.InventarioGangazo2.repository.OrderRepository;
import com.example.InventarioGangazo2.repository.ProductsRepository;
import com.example.InventarioGangazo2.repository.ShoppingCartItemRepository;
import com.example.InventarioGangazo2.repository.ShoppingCartRepository;
import com.example.InventarioGangazo2.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingService {
    private final OrdenItemsRepository ordenItemsRepository;
    private final OrderRepository orderRepository;
    private final ProductsRepository productsRepository;
    private final UsersRepository usersRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final ShoppingCartService shoppingCartService;

    public OrderResponseDTO MakePurchase(OrderRequestDTO request) {
        if (request.getUserId() == null) {
            throw new RuntimeException("User is required");
        }
        Users user = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found or empty"));

        List<ShoppingCartItem> cartItems = shoppingCartItemRepository.findByShoppingCart(cart);

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setDate(new Timestamp(System.currentTimeMillis()));
        order.setTotal(0.0);
        order.setUserId(request.getUserId());
        order = orderRepository.save(order);

        double total = 0;
        List<OrdenItemsResponseDTO> itemsResponse = new ArrayList<>();

        for (ShoppingCartItem cartItem : cartItems) {
            Products product = cartItem.getProduct();

            if (product == null) {
                throw new RuntimeException("Product not found in cart item");
            }
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - cartItem.getQuantity());
            productsRepository.save(product);

            OrdenItems orderItem = new OrdenItems();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            ordenItemsRepository.save(orderItem);

            total += product.getPrice() * cartItem.getQuantity();

            OrdenItemsResponseDTO itemResponse = new OrdenItemsResponseDTO();
            itemResponse.setProductId(product.getId());
            itemResponse.setName(product.getName());
            itemResponse.setQuantity(cartItem.getQuantity());
            itemResponse.setPrice(product.getPrice());
            itemsResponse.add(itemResponse);
        }

        order.setTotal(total);
        orderRepository.save(order);

        shoppingCartService.clearCart(user);

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
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {
            List<OrdenItems> items = ordenItemsRepository.findByOrderId(order.getId());
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

            List<OrdenItems> items = ordenItemsRepository.findByOrderId(order.getId());
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