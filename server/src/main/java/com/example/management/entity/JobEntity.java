/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "job")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j")
//    , @NamedQuery(name = "Job.findById", query = "SELECT j FROM Job j WHERE j.id = :id")
//    , @NamedQuery(name = "Job.findByTitle", query = "SELECT j FROM Job j WHERE j.title = :title")
//    , @NamedQuery(name = "Job.findByCompany", query = "SELECT j FROM Job j WHERE j.company = :company")
//    , @NamedQuery(name = "Job.findByRequireYear", query = "SELECT j FROM Job j WHERE j.requireYear = :requireYear")
//    , @NamedQuery(name = "Job.findByDatePost", query = "SELECT j FROM Job j WHERE j.datePost = :datePost")
//    , @NamedQuery(name = "Job.findByTagIds", query = "SELECT j FROM Job j WHERE j.tagIds = :tagIds")
//    , @NamedQuery(name = "Job.findByAddress", query = "SELECT j FROM Job j WHERE j.address = :address")
//    , @NamedQuery(name = "Job.findByCreated", query = "SELECT j FROM Job j WHERE j.created = :created")})
public class JobEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 200)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 200)
    @Column(name = "COMPANY")
    private String company;
    @Size(max = 10)
    @Column(name = "REQUIRE_YEAR")
    private String requireYear;
    @Column(name = "DATE_POST")
    @Temporal(TemporalType.DATE)
    private Date datePost;
    @Lob
    @Size(max = 65535)
    @Column(name = "DESCRIPTION")
    private String description;
    @Lob
    @Size(max = 65535)
    @Column(name = "LINK")
    private String link;
    @Size(max = 250)
    @Column(name = "TAG_IDS")
    private String tagIds;
    @Size(max = 250)
    @Column(name = "ADDRESS")
    private String address;
    @Basic(optional = false)
    
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public JobEntity() {
    }

    public JobEntity(Integer id, String title, String company, String requireYear, Date datePost, String description, String link, String tagIds, String address, Date created) {
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
    }

    
    public JobEntity(Integer id) {
        this.id = id;
    }

    public JobEntity(Integer id, Date created) {
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
}
