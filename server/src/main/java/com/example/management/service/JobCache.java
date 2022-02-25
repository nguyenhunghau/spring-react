package com.example.management.service;

import com.example.management.entity.JobEntity;
import com.example.management.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = {"jobs"})
public class JobCache {

    @Autowired
    private JobRepository jobRepository;

    @Cacheable(key="#id")
    public JobEntity getOnCache(Integer id){
        System.out.println("############# Backend processing...");

        // simulation the time for processing
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        return jobRepository.findById(id).get();
    }

    @CachePut(key="#id")
    public JobEntity putOnCache(String firstName, Integer id){
        // find a customer in repository
        JobEntity cust = jobRepository.findById(id).get();

        // modify above customer by first-name
        cust.setAddress(firstName);

        // save to database
        return jobRepository.save(cust);
    }

    @CacheEvict(key = "#id")
    public void evict(String id){
    }
}
