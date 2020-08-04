package com.example.management.repository;

import com.example.management.entity.WebAnalyticEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nguyen Hung Hau
 */
@Repository
public interface WebAnalyticRepository extends CrudRepository<WebAnalyticEntity, Integer> {
    
}
