/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.repository;

import com.example.management.entity.WebAnalyticEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 */
@Repository
public interface WebAnalyticRepository extends CrudRepository<WebAnalyticEntity, Integer> {
   
//    @Query(value = "SELECT u FROM WebAnalyticEntity u WHERE u.id=1")
//    public WebAnalyticEntity findById(int id);
    
}
