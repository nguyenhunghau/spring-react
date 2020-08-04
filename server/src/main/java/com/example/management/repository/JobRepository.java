package com.example.management.repository;

import com.example.management.entity.JobEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
}
