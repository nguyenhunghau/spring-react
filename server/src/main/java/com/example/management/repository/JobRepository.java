/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.repository;

import com.example.management.entity.JobEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author USER
 */
public interface JobRepository extends CrudRepository<JobEntity, Integer> {
    
    @Query(value = "SELECT j FROM JobEntity j WHERE j.link =:link and j.title =:title")
    public JobEntity findByLinkAndTitle(String link, String title);

    @Query(value = "SELECT j FROM JobEntity j WHERE j.requirement is null")
    public List<JobEntity> findJobNotAnalyticsDetail(Pageable pageable);
}
