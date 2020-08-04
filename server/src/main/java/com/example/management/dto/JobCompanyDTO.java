package com.example.management.dto;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.example.management.entity.JobEntity;
import java.util.ArrayList;
import java.util.List;
//</editor-fold>

/**
 *
 * @author Admin
 */
public class JobCompanyDTO {
    
    private String company;
    private String tags;
    private List<JobEntity> jobList;

    public JobCompanyDTO(String company, String tags, List<JobEntity> jobList) {
        this.company = company;
        this.tags = tags;
        this.jobList = new ArrayList<>(jobList);
    }
}
