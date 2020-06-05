package com.example.management.repository;

import com.example.management.entity.QueryCheckerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Nguyen Hung Hau
 */
public interface QueryCheckerRepository extends CrudRepository<QueryCheckerEntity, Integer> {
    
    @Query(value = "SELECT u FROM QueryCheckerEntity u WHERE u.isActive=1 and webAnalytic.id=:webAnalyticId")
    public List<QueryCheckerEntity> findActiveList(int webAnalyticId);
}
