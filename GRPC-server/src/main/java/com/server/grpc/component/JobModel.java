package com.server.grpc.component;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author USER
 */
public class JobModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String title;
    private String company;
    private String requireYear;
    private Date datePost;
    private Date dateExpired;
    private String description;
    private String requirement;
    private String link;
    private String tagIds;
    private String address;
    private Date created;
    private Date updated;

    public JobModel() {
    }

    public JobModel(Integer id, String title, String company, String requireYear, Date datePost, Date dateExpired, 
            String description, String link, String tagIds, String address, Date created) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.requireYear = requireYear;
        this.datePost = datePost;
        this.description = description;
        this.link = link;
        this.tagIds = tagIds;
        this.address = address;
        this.created = created;
        this.dateExpired = dateExpired;
    }
    
    public JobModel(Integer id) {
        this.id = id;
    }

    public JobModel(Integer id, Date created) {
        this.id = id;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRequireYear() {
        return requireYear;
    }

    public void setRequireYear(String requireYear) {
        this.requireYear = requireYear;
    }

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
