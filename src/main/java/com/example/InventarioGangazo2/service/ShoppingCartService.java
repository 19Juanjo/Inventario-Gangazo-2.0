package com.example.InventarioGangazo2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.InventarioGangazo2.dto.ShoppingCartItemRequestDTO;
import com.example.InventarioGangazo2.dto.ShoppingCartItemResponseDTO;
import com.example.InventarioGangazo2.dto.ShoppingCartResponseDTO;
import com.example.InventarioGangazo2.entity.Products;
import com.example.InventarioGangazo2.entity.ShoppingCart;
import com.example.InventarioGangazo2.entity.ShoppingCartItem;
import com.example.InventarioGangazo2.entity.Users;
import com.example.InventarioGangazo2.repository.ProductsRepository;
import com.example.InventarioGangazo2.repository.ShoppingCartItemRepository;
import com.example.InventarioGangazo2.repository.ShoppingCartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShoppingCartService {
        private final ShoppingCartRepository shoppingCartRepository;
        private final ShoppingCartItemRepository shoppingCartItemRepository;
        private final ProductsRepository productsRepository;

    public Optional<ShoppingCartResponseDTO> getCartByUser(Users user) {

        if (user == null) {
            throw new RuntimeException("User is required");
        }

        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    return shoppingCartRepository.save(newCart);
                });

        return buildResponse(cart);
    }

    public Optional<ShoppingCartResponseDTO> addProduct(Users user, ShoppingCartItemRequestDTO request){

        if (user == null) {
            throw new RuntimeException("User is required");
        }

        if (request.getProductId() == null) {
            throw new RuntimeException("Product is required");
        }

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        ShoppingCart cart = shoppingCartRepository.findByUser(user).orElseGet(() -> {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUser(user);
            return shoppingCartRepository.save(newCart);
        });

        Products product = productsRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        ShoppingCartItem item = shoppingCartItemRepository
            .findByShoppingCartAndProduct(cart, product)
            .orElse(new ShoppingCartItem());

        item.setShoppingCart(cart);
        item.setProduct(product);

        int newQuantity = (item.getQuantity() == null ? 0 : item.getQuantity()) + request.getQuantity();

        if (product.getStock() < newQuantity) {
            throw new RuntimeException("Insufficient stock");
        }

        item.setQuantity(newQuantity);

        shoppingCartItemRepository.save(item);

        return buildResponse(cart);
    }

    public Optional<ShoppingCartResponseDTO> updateCart(Users user, ShoppingCartItemRequestDTO request){

        if (user == null) {
            throw new RuntimeException("User is required");
        }

        if (request.getProductId() == null) {
            throw new RuntimeException("Product is required");
        }

        if (request.getQuantity() == null) {
            throw new RuntimeException("Quantity is required");
        }

        ShoppingCart cart = shoppingCartRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        Products product = productsRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

        ShoppingCartItem item = shoppingCartItemRepository
            .findByShoppingCartAndProduct(cart, product)
            .orElseThrow(() -> new RuntimeException("Product not in cart"));

        if(request.getQuantity() <= 0){
            shoppingCartItemRepository.delete(item);
        } else {
            if (product.getStock() < request.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }
            item.setQuantity(request.getQuantity());
            shoppingCartItemRepository.save(item);   
        }

        return buildResponse(cart);
    }

    public Optional<ShoppingCartResponseDTO> removeProduct(Users user, Long productId){

        if (user == null) {
            throw new RuntimeException("User is required");
        }
        if (productId == null) {
            throw new RuntimeException("Product is required");
        }

        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ShoppingCartItem item = shoppingCartItemRepository
                .findByShoppingCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Product not in cart"));

        shoppingCartItemRepository.delete(item);

        return buildResponse(cart);        
    }

    public void clearCart(Users user) {
        if (user == null) {
            throw new RuntimeException("User is required");
        }
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        shoppingCartItemRepository.deleteByShoppingCart(cart);
    }

    private Optional<ShoppingCartResponseDTO> buildResponse(ShoppingCart cart) {

        List<ShoppingCartItem> items = shoppingCartItemRepository.findByShoppingCart(cart);
        List<ShoppingCartItemResponseDTO> responseItems = new ArrayList<>();
        double total = 0;

        for (ShoppingCartItem item : items) {
            double subtotal = item.getQuantity() * item.getProduct().getPrice();
            responseItems.add(new ShoppingCartItemResponseDTO(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity(),
                    subtotal
            ));
            total += subtotal;
        }
        return Optional.of(new ShoppingCartResponseDTO(cart.getId(), responseItems, total));
    }
}
