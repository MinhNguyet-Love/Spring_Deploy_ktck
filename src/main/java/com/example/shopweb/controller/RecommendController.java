//package com.example.shopweb.controller;
//
//import com.example.shopweb.model.Recommend;
//import com.example.shopweb.service.RecommendService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//public class RecommendController {
//
//    @Autowired
//    private RecommendService recommendService;
//
//    @GetMapping("/")
//    public String showRecommendList(Model model) {
//        List<Recommend> recommends = recommendService.findAll();
//        if (recommends.isEmpty()) {
//            System.out.println("No recommendations found!");
//        } else {
//            System.out.println("Found " + recommends.size() + " recommendations.");
//        }
//        model.addAttribute("recommends", recommends);
//        return "index"; // Đảm bảo rằng view name chính xác
//    }
//
//
//    // Lấy tất cả đề xuất (API)
//    @GetMapping("/api")
//    @ResponseBody
//    public List<Recommend> getRecommendList() {
//        return recommendService.findAll();
//    }
//
//    // Lấy đề xuất theo ID (API)
//    @GetMapping("/api/{id}")
//    @ResponseBody
//    public ResponseEntity<Recommend> getRecommendById(@PathVariable("id") long recommendId) {
//        try {
//            Recommend recommend = recommendService.findById(recommendId);
//            return ResponseEntity.status(200).body(recommend);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(404).body(null);
//        }
//    }
//
//    // Tạo đề xuất mới (API)
//    @PostMapping("/api")
//    @ResponseBody
//    public ResponseEntity<Recommend> createRecommend(@RequestBody Recommend recommend) {
//        Recommend savedRecommend = recommendService.save(recommend);
//        return ResponseEntity.status(201).body(savedRecommend);
//    }
//
//    // Cập nhật đề xuất theo ID (API)
//    @PutMapping("/api/{id}")
//    @ResponseBody
//    public ResponseEntity<Recommend> updateRecommend(@PathVariable("id") long recommendId,
//                                                     @RequestBody Recommend recommendData) {
//        try {
//            Recommend existingRecommend = recommendService.findById(recommendId);
//            // Cập nhật các trường của đề xuất hiện tại với dữ liệu từ request
//            existingRecommend.setRecommendName(recommendData.getRecommendName());
//            existingRecommend.setDescription(recommendData.getDescription());
//            existingRecommend.setPrice(recommendData.getPrice());
//            existingRecommend.setImageUrl(recommendData.getImageUrl());
//            recommendService.save(existingRecommend);
//            return ResponseEntity.status(200).body(existingRecommend);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(404).body(null);
//        }
//    }
//
//    // Xóa đề xuất theo ID (API)
//    @DeleteMapping("/api/{id}")
//    @ResponseBody
//    public ResponseEntity<List<Recommend>> removeRecommendById(@PathVariable("id") long recommendId) {
//        try {
//            recommendService.delete(recommendId);
//            List<Recommend> updatedRecommends = recommendService.findAll();
//            return ResponseEntity.status(200).body(updatedRecommends);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(404).body(null);  // Nếu không tìm thấy đề xuất
//        }
//    }
//}
