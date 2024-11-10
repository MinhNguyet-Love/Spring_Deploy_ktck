package com.example.shopweb.controller;

import com.example.shopweb.model.Feature;
import com.example.shopweb.repository.FeatureRepository;
import com.example.shopweb.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class FeaturesController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private FeatureRepository repo;

    // Show feature list on the features page
    @GetMapping("/")
    public String showFeatureList(Model model) {
        List<Feature> features = featureService.findAll();  // Use service to fetch data
        model.addAttribute("features", features);  // Add features to model
        return "index";  // Make sure you have a "features" view (Thymeleaf or JSP)
    }

    // Get all features
    @GetMapping("/api/features")
    @ResponseBody
    public List<Feature> getFeatureList() {
        return featureService.findAll();
    }

    // Get feature by ID
    @GetMapping("/api/features/{id}")
    @ResponseBody
    public ResponseEntity<Feature> getFeatureById(@PathVariable("id") long featureId) {
        Feature feature = featureService.findById(featureId);
        if (feature != null) {
            return ResponseEntity.status(200).body(feature);
        }
        return ResponseEntity.status(404).body(null);
    }

    // Create new feature
    @PostMapping("/api/features")
    @ResponseBody
    public ResponseEntity<Feature> createFeature(@RequestBody Feature feature) {
        Feature savedFeature = featureService.save(feature);
        return ResponseEntity.status(201).body(savedFeature);
    }

    // Update feature by ID
    @PutMapping("/api/features/{id}")
    @ResponseBody
    public ResponseEntity<Feature> updateFeature(@PathVariable("id") long featureId,
                                                 @RequestBody Feature featureData) {
        Feature existingFeature = featureService.findById(featureId);
        if (existingFeature != null) {
            // Update the fields of the existing feature with the data from the request
            existingFeature.setFeatureName(featureData.getFeatureName());
            existingFeature.setDescription(featureData.getDescription());
            existingFeature.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // This should be auto-handled if you have @PreUpdate
            featureService.save(existingFeature);
            return ResponseEntity.status(200).body(existingFeature);
        }
        return ResponseEntity.status(404).body(null);
    }

    // Delete feature by ID
    @DeleteMapping("/api/features/{id}")
    @ResponseBody
    public ResponseEntity<List<Feature>> removeFeatureById(@PathVariable("id") long featureId) {
        featureService.delete(featureId);
        List<Feature> updatedFeatures = featureService.findAll();
        return ResponseEntity.status(200).body(updatedFeatures);
    }
}
