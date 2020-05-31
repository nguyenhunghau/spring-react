/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.repository;

import com.example.management.entity.TagEntity;
import com.example.management.entity.WebAnalyticEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer>  {
    
    public TagEntity findByName(String name);
}
