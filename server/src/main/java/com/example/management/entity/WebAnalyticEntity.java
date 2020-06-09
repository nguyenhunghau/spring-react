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
@Table(name = "WEB_ANALYTIC")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "WebAnalytic.findAll", query = "SELECT w FROM WebAnalytic w")
//    , @NamedQuery(name = "WebAnalytic.findById", query = "SELECT w FROM WebAnalytic w WHERE w.id = :id")
//    , @NamedQuery(name = "WebAnalytic.findByName", query = "SELECT w FROM WebAnalytic w WHERE w.name = :name")
//    , @NamedQuery(name = "WebAnalytic.findByLink", query = "SELECT w FROM WebAnalytic w WHERE w.link = :link")
//    , @NamedQuery(name = "WebAnalytic.findByIsActive", query = "SELECT w FROM WebAnalytic w WHERE w.isActive = :isActive")
//    , @NamedQuery(name = "WebAnalytic.findByCreated", query = "SELECT w FROM WebAnalytic w WHERE w.created = :created")})
public class WebAnalyticEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 250)
    @Column(name = "NAME")
    private String name;
    @Size(max = 250)
    @Column(name = "LINK")
    private String link;
    @Column(name = "IS_ACTIVE")
    private Short isActive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public WebAnalyticEntity() {
    }

    public WebAnalyticEntity(Integer id) {
        this.id = id;
    }

    public WebAnalyticEntity(Integer id, Date created) {
        this.id = id;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
