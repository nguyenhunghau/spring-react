package com.example.management.repository;

import com.example.management.entity.TagEntity;
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
