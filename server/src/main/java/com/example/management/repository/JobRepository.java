/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.repository;

import com.example.management.entity.JobEntity;
import com.example.management.entity.WebAnalyticEntity;
import java.util.Date;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author USER
 */
public interface JobRepository extends CrudRepository<JobEntity, Integer> {
    
    public JobEntity findByDatePostAndTitle(Date datePost, String title);
}
