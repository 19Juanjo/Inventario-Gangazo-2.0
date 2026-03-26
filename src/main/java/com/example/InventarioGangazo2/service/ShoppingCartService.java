package com.example.InventarioGangazo2.service;

import java.util.ArrayList;
import java.util.List;

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

        public ShoppingCartResponseDTO getCartByUser(Users user) {
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    return shoppingCartRepository.save(newCart);
                });

        return buildResponse(cart);
    }

    public ShoppingCartResponseDTO addProduct(Users user, ShoppingCartItemRequestDTO request){

        ShoppingCart cart = shoppingCartRepository.findByUser(user).orElseGet(() -> {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUser(user);
            return shoppingCartRepository.save(newCart);
        });

        Products product = productsRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        ShoppingCartItem item = shoppingCartItemRepository
            .findByShoppingCartAndProduct(cart, product)
            .orElse(new ShoppingCartItem());
        
        item.setShoppingCart(cart);

        item.setProduct(product);

        item.setQuantity(item.getQuantity() == null ? request.getCantidad() : item.getQuantity() + request.getCantidad()
        );

        shoppingCartItemRepository.save(item);

        return buildResponse(cart);
    }

    public ShoppingCartResponseDTO updateCart(Users user, ShoppingCartItemRequestDTO request){

        ShoppingCart cart = shoppingCartRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Cart no found"));

        Products products = productsRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product no found"));
        
        ShoppingCartItem item = shoppingCartItemRepository
            .findByShoppingCartAndProduct(cart, products)
            .orElseThrow(() -> new RuntimeException("Product not in cart"));

        if(request.getCantidad() <= 0){
            shoppingCartItemRepository.delete(item);
        }else{
            item.setQuantity(request.getCantidad());
            shoppingCartItemRepository.save(item);   
        }

        return buildResponse(cart);
    }

    public ShoppingCartResponseDTO removeProduct(Users user, Long producto_id){

        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("cart not found"));

        Products product = productsRepository.findById(producto_id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ShoppingCartItem item = shoppingCartItemRepository
                .findByShoppingCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Not this in cart"));

        shoppingCartItemRepository.delete(item);

        return buildResponse(cart);        
    }

    public void clearCart(Users user) {

        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart no found"));

        shoppingCartItemRepository.deleteByShoppingCart(cart);
    }

    private ShoppingCartResponseDTO buildResponse(ShoppingCart cart) {

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

    return new ShoppingCartResponseDTO(cart.getId(), responseItems, total);
    }
}
