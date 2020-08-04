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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "QUERY_CHECKER")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "QueryChecker.findAll", query = "SELECT q FROM QueryChecker q")
//    , @NamedQuery(name = "QueryChecker.findById", query = "SELECT q FROM QueryChecker q WHERE q.id = :id")
//    , @NamedQuery(name = "QueryChecker.findByQueryType", query = "SELECT q FROM QueryChecker q WHERE q.queryType = :queryType")
//    , @NamedQuery(name = "QueryChecker.findByQuaryValue", query = "SELECT q FROM QueryChecker q WHERE q.quaryValue = :quaryValue")
//    , @NamedQuery(name = "QueryChecker.findByIsActive", query = "SELECT q FROM QueryChecker q WHERE q.isActive = :isActive")
//    , @NamedQuery(name = "QueryChecker.findByCreated", query = "SELECT q FROM QueryChecker q WHERE q.created = :created")})
public class QueryCheckerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 12)
    @Column(name = "QUERY_TYPE")
    private String queryType;
    @Size(max = 250)
    @Column(name = "QUARY_VALUE")
    private String quaryValue;
    @Column(name = "IS_ACTIVE")
    private Short isActive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    @ManyToOne
    @JoinColumn(name = "WEB_ANALYTIC_ID")
    private WebAnalyticEntity webAnalytic;

    public QueryCheckerEntity() {
    }

    public QueryCheckerEntity(Integer id) {
        this.id = id;
    }

    public QueryCheckerEntity(Integer id, Date created) {
        this.id = id;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getQueryValue() {
        return quaryValue;
    }

    public void setQuaryValue(String quaryValue) {
        this.quaryValue = quaryValue;
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

    public WebAnalyticEntity getWebAnalytic() {
        return webAnalytic;
    }

    public void setWebAnalytic(WebAnalyticEntity webAnalytic) {
        this.webAnalytic = webAnalytic;
    }
   
}
