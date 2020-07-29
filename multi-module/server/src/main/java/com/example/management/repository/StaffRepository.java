/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.repository;

import com.example.management.entity.StaffEntity;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Nguyen Hung Hau
 */
public interface StaffRepository extends CrudRepository<StaffEntity, Integer> {

}
