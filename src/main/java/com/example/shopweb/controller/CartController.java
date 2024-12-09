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

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, Model model) {
        cartService.addToCart(productId, quantity);
        return "redirect:/cart/view"; // Quay lại trang giỏ hàng
    }

    // Xem giỏ hàng
    @GetMapping("/view")
    public String viewCart(Model model) {
        model.addAttribute("cart", cartService.getCartItems());
        return "cart"; // Trả về trang giỏ hàng
    }

    // Cập nhật sản phẩm trong giỏ hàng
    @PostMapping("/update")
    public String updateCartItem(@RequestParam Long cartItemId, @RequestParam int quantity, Model model) {
        cartService.updateCartItem(cartItemId, quantity);
        return "redirect:/cart/view"; // Quay lại trang giỏ hàng
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId, Model model) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart/view"; // Quay lại trang giỏ hàng
    }

    // Thanh toán giỏ hàng
    @PostMapping("/pay")
    public String payCart(Model model) {
        // Xử lý thanh toán và xóa sản phẩm trong giỏ hàng
        cartService.clearCart();

        // Thêm thông báo thanh toán thành công
        model.addAttribute("message", "Thanh toán thành công!");

        // Quay lại trang giỏ hàng với thông báo
        model.addAttribute("cart", cartService.getCartItems());
        return "cart"; // Trả về trang giỏ hàng với thông báo
    }
}
