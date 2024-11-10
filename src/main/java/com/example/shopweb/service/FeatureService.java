package com.example.shopweb.service;

import com.example.shopweb.model.Feature;
import com.example.shopweb.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    // Find all features
    public List<Feature> findAll() {
        return featureRepository.findAll();
    }

    // Find feature by ID
    public Feature findById(Long id) {
        return featureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature not found"));
    }

    // Save new or updated feature
    public Feature save(Feature feature) {
        return featureRepository.save(feature);
    }

    // Update feature (this is handled by save method since save can insert or update)
    public Feature update(Feature feature) {
        return featureRepository.save(feature);
    }

    // Delete feature by ID
    public void delete(Long id) {
        featureRepository.deleteById(id);
    }
}
