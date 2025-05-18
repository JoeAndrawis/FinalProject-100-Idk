package com.example.courseservice.repository;

import com.example.courseservice.model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MaterialRepository extends MongoRepository<Material, String> {
    List<Material> findByCourseId(String courseId);
}