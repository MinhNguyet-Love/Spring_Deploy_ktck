package com.example.shopweb.controller;

import com.example.shopweb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, Model model) {
        cartService.addToCart(productId, quantity);
        // Add cart to model
        model.addAttribute("cart", cartService.getCartItems());
        return "redirect:/cart/view"; // Redirect to view cart page
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        // Add cart to model
        model.addAttribute("cart", cartService.getCartItems());
        return "cart"; // Return the cart view
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam Long cartItemId, @RequestParam int quantity) {
        cartService.updateCartItem(cartItemId, quantity);
        return "redirect:/cart/view"; // Redirect to view cart page
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart/view"; // Redirect to view cart page
    }
}
