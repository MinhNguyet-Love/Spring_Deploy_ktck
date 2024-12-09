package com.example.shopweb.controller;

import com.example.shopweb.model.Product;
import com.example.shopweb.repository.ProductRepository;
import com.example.shopweb.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository repo;

    // Image storage path
    private final String IMAGE_PATH = "D:\\Nam3\\Web1\\shopweb\\public\\images\\";



    // Show product list on the shop page
    @GetMapping("/shop")
    public String showProductList(Model model) {
        List<Product> products = repo.findAll();
        model.addAttribute("products", products);
        return "shop";
    }

    // Get all products
    @GetMapping("/products")
    @ResponseBody
    public List<Product> getProductList() {
        return productService.findAll();
    }

    // Get product by ID
    @GetMapping("/products/{id}")
    @ResponseBody
    public ResponseEntity<Product> getProductById(@PathVariable("id") int productId) {
        Product product = productService.findById((long) productId);
        if (product != null) {
            return ResponseEntity.status(200).body(product);
        }
        return ResponseEntity.status(404).body(null);
    }

    // Create new product
    @PostMapping("/products")
    @ResponseBody
    public ResponseEntity<Product> createProduct(@RequestParam("product") String productData,
                                                 @RequestParam("imageFile") MultipartFile imageFile) {
        Product product = parseProductData(productData); // Implement this method to parse JSON
        String imagePath = saveImage(imageFile); // Save image and get the path
        product.setImageUrl(imagePath);
        productService.save(product);
        return ResponseEntity.status(201).body(product);
    }

    // Update product by ID
    @PutMapping("/products/{id}")
    @ResponseBody
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int productId,
                                                 @RequestParam("product") String productData,
                                                 @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        Product product = productService.findById((long) productId);
        if (product != null) {
            // Update product fields
            updateProductFields(product, productData);
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = saveImage(imageFile); // Save image and get the path
                product.setImageUrl(imagePath);
            }
            productService.save(product);
            return ResponseEntity.status(200).body(product);
        }
        return ResponseEntity.status(404).body(null);
    }

    // Delete product by ID
    @DeleteMapping("/products/{id}")
    @ResponseBody
    public List<Product> removeProductById(@PathVariable("id") int productId) {
        productService.delete((long) productId);
        return productService.findAll();
    }

    // Show admin page
    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin"; // Return the view admin.html
    }

    // Save image
    private String saveImage(MultipartFile imageFile) {
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        String filePath = IMAGE_PATH + fileName;

        // Create directory if it doesn't exist
        File directory = new File(IMAGE_PATH);
        if (!directory.exists()) {
            directory.mkdirs(); // Create all necessary directories
        }

        try {
            imageFile.transferTo(new File(filePath)); // Save file to path
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage());
        }
        return filePath; // Return saved image path
    }

//    // Show shop page
//    @GetMapping("/")
//    public String showShopPage() {
//        return "index"; // Return file index.html
//    }



    @GetMapping("/product-details")
    public String showLoginPage() {
        return "product-details";
    }

    @GetMapping("/cart")
    public String showCartPage() {
        return "cart";
    }

    @GetMapping("/login")
    public String showProductPage() {
        return "login";
    }

    // Parse product data from JSON
    private Product parseProductData(String productData) {
        // Use a JSON library (like Jackson) to convert JSON to Product
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(productData, Product.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse product data: " + e.getMessage());
        }
    }

    // Update fields of a product
    private void updateProductFields(Product product, String productData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product updatedProduct = objectMapper.readValue(productData, Product.class);
            product.setProductName(updatedProduct.getProductName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setCategoryId(updatedProduct.getCategoryId());
            // You can also update the imageUrl if needed
            product.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Update timestamp
        } catch (IOException e) {
            throw new RuntimeException("Failed to update product fields: " + e.getMessage());
        }
    }
}
