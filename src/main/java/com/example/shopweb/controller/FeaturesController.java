package com.example.shopweb.controller;

import com.example.shopweb.model.Feature;
import com.example.shopweb.model.Recommend;
import com.example.shopweb.service.FeatureService;
import com.example.shopweb.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FeaturesController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private RecommendService recommendService;

    // Hiển thị danh sách Features và Recommends trên trang chủ
    @GetMapping("/")
    public String showLists(Model model) {
        // Lấy danh sách Features
        List<Feature> features = featureService.findAll();
        model.addAttribute("features", features);

        // Lấy danh sách Recommends
        List<Recommend> recommends = recommendService.findAll();
        if (recommends.isEmpty()) {
            System.out.println("No recommendations found!");
        } else {
            System.out.println("Found " + recommends.size() + " recommendations.");
        }
        model.addAttribute("recommends", recommends);

        // Trả về view index
        return "index";
    }

    // Lấy tất cả Feature (API)
    @GetMapping("/api/features")
    @ResponseBody
    public List<Feature> getFeatureList() {
        return featureService.findAll();
    }

    // Lấy Feature theo ID (API)
    @GetMapping("/api/features/{id}")
    @ResponseBody
    public ResponseEntity<Feature> getFeatureById(@PathVariable("id") long featureId) {
        try {
            Feature feature = featureService.findById(featureId);
            return ResponseEntity.status(200).body(feature);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Tạo Feature mới (API)
    @PostMapping("/api/features")
    @ResponseBody
    public ResponseEntity<Feature> createFeature(@RequestBody Feature feature) {
        Feature savedFeature = featureService.save(feature);
        return ResponseEntity.status(201).body(savedFeature);
    }

    // Cập nhật Feature theo ID (API)
    @PutMapping("/api/features/{id}")
    @ResponseBody
    public ResponseEntity<Feature> updateFeature(@PathVariable("id") long featureId, @RequestBody Feature featureData) {
        try {
            Feature existingFeature = featureService.findById(featureId);
            existingFeature.setFeatureName(featureData.getFeatureName()); // Fixed here: use featureName, not name
            existingFeature.setDescription(featureData.getDescription());
            existingFeature.setPrice(featureData.getPrice());
            existingFeature.setImageUrl(featureData.getImageUrl());
            featureService.save(existingFeature);
            return ResponseEntity.status(200).body(existingFeature);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Xóa Feature theo ID (API)
    @DeleteMapping("/api/features/{id}")
    @ResponseBody
    public ResponseEntity<List<Feature>> removeFeatureById(@PathVariable("id") long featureId) {
        try {
            featureService.delete(featureId);
            List<Feature> updatedFeatures = featureService.findAll();
            return ResponseEntity.status(200).body(updatedFeatures);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Lấy tất cả Recommend (API)
    @GetMapping("/api/recommends")
    @ResponseBody
    public List<Recommend> getRecommendList() {
        return recommendService.findAll();
    }

    // Lấy Recommend theo ID (API)
    @GetMapping("/api/recommends/{id}")
    @ResponseBody
    public ResponseEntity<Recommend> getRecommendById(@PathVariable("id") long recommendId) {
        try {
            Recommend recommend = recommendService.findById(recommendId);
            return ResponseEntity.status(200).body(recommend);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Tạo Recommend mới (API)
    @PostMapping("/api/recommends")
    @ResponseBody
    public ResponseEntity<Recommend> createRecommend(@RequestBody Recommend recommend) {
        Recommend savedRecommend = recommendService.save(recommend);
        return ResponseEntity.status(201).body(savedRecommend);
    }

    // Cập nhật Recommend theo ID (API)
    @PutMapping("/api/recommends/{id}")
    @ResponseBody
    public ResponseEntity<Recommend> updateRecommend(@PathVariable("id") long recommendId, @RequestBody Recommend recommendData) {
        try {
            Recommend existingRecommend = recommendService.findById(recommendId);
            existingRecommend.setRecommendName(recommendData.getRecommendName());
            existingRecommend.setDescription(recommendData.getDescription());
            existingRecommend.setPrice(recommendData.getPrice());
            existingRecommend.setImageUrl(recommendData.getImageUrl());
            recommendService.save(existingRecommend);
            return ResponseEntity.status(200).body(existingRecommend);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Xóa Recommend theo ID (API)
    @DeleteMapping("/api/recommends/{id}")
    @ResponseBody
    public ResponseEntity<List<Recommend>> removeRecommendById(@PathVariable("id") long recommendId) {
        try {
            recommendService.delete(recommendId);
            List<Recommend> updatedRecommends = recommendService.findAll();
            return ResponseEntity.status(200).body(updatedRecommends);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
