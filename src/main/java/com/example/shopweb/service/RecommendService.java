package com.example.shopweb.service;

import com.example.shopweb.model.Recommend;
import com.example.shopweb.repository.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecommendService {

    @Autowired
    private RecommendRepository recommendRepository;

    // Tìm tất cả các đề xuất
    public List<Recommend> findAll() {
        return recommendRepository.findAll();
    }

    // Tìm đề xuất theo ID
    public Recommend findById(Long id) {
        return recommendRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recommendation not found"));
    }

    // Lưu đề xuất mới hoặc cập nhật đề xuất
    public Recommend save(Recommend recommend) {
        return recommendRepository.save(recommend);
    }

    // Cập nhật đề xuất (đã có sẵn trong phương thức save)
    public Recommend update(Recommend recommend) {
        if (recommend.getRecommendId() == null || !recommendRepository.existsById(recommend.getRecommendId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recommendation not found for update");
        }
        return recommendRepository.save(recommend);
    }

    // Xóa đề xuất theo ID
    public void delete(Long id) {
        if (!recommendRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recommendation not found for deletion");
        }
        recommendRepository.deleteById(id);
    }
}
