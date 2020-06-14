/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.dto;

import com.example.management.entity.JobEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class JobCompanyDTO {
    
    private String company;
    private List<JobEntity> jobList;

    public JobCompanyDTO(String company, List<JobEntity> jobList) {
        this.company = company;
        this.jobList = new ArrayList<>(jobList);
    }
    
}
