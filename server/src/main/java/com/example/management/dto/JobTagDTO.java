package com.example.management.dto;

import com.example.management.entity.JobEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Hung Hau
 */
public class JobTagDTO {
    private String tagName;
    private List<JobEntity> jobList;

    public JobTagDTO(String tagName, List<JobEntity> jobList) {
        this.tagName = tagName;
        this.jobList = new ArrayList<>(jobList);
    }
}
