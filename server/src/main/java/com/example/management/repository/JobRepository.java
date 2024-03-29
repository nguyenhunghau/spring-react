package com.example.management.repository;

import com.example.management.entity.JobEntity;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author USER
 */
public interface JobRepository extends CrudRepository<JobEntity, Integer> {
    
    @Query(value = "SELECT j FROM JobEntity j WHERE j.link =:link and j.title =:title")
    public JobEntity findByLinkAndTitle(String link, String title);

    @Query(value = "SELECT j FROM JobEntity j WHERE j.requirement is null")
    public List<JobEntity> findJobNotAnalyticsDetail(Pageable pageable);

    @Query(value = "SELECT j FROM JobEntity j WHERE dateExpired >= :date")
    public List<JobEntity> findAllNotExpired(@Param(value = "date") Date date);
    
    @Query(value = "SELECT j FROM JobEntity j WHERE j.id = (SELECT max(i.id) FROM JobEntity i)")
    public JobEntity findLastItem();

    @Async
    @Query("SELECT j FROM JobEntity j")
    public CompletableFuture<List<JobEntity>> findAllJobs();
}
