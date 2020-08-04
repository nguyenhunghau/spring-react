package com.example.management.dto;

/**
 *
 * @author USER
 */
public class JobSearchDTO {
    
    private String company;
    private boolean showAll;

    public JobSearchDTO() {
    }
    
    public JobSearchDTO(String company, boolean showAll) {
        this.company = company;
        this.showAll = showAll;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean getShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }
    
}
