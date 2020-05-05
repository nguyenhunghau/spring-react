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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nguyen Hung Hau
 * @email nguyenhunghau@fabercompany.co.jp
 */
@Entity
@Table(name = "a_staff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffEntity.findAll", query = "SELECT s FROM StaffEntity s")
    ,
               @NamedQuery(name = "StaffEntity.findById",
                query = "SELECT s FROM StaffEntity s WHERE s.id = :id")
    ,
               @NamedQuery(name = "StaffEntity.findByName",
                query = "SELECT s FROM StaffEntity s WHERE s.name = :name")
    ,
               @NamedQuery(name = "StaffEntity.findByEmail",
                query = "SELECT s FROM StaffEntity s WHERE s.email = :email")
    ,
               @NamedQuery(name = "StaffEntity.findByCreated",
                query = "SELECT s FROM StaffEntity s WHERE s.created = :created")
    ,
               @NamedQuery(name = "StaffEntity.findByCreatedBy",
                query = "SELECT s FROM StaffEntity s WHERE s.createdBy = :createdBy")
    ,
               @NamedQuery(name = "StaffEntity.findByIsCsMember",
                query = "SELECT s FROM StaffEntity s WHERE s.isCsMember = :isCsMember")
    ,
               @NamedQuery(name = "StaffEntity.findByIsLoginManagement",
                query = "SELECT s FROM StaffEntity s WHERE s.isLoginManagement = :isLoginManagement")})

public class StaffEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 250)
    @Column(name = "NAME")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 250)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Size(max = 250)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "IS_CS_MEMBER")
    private Boolean isCsMember;
    @Column(name = "IS_LOGIN_MANAGEMENT")
    private Boolean isLoginManagement;

    public StaffEntity() {
    }

    public StaffEntity(Integer id, String name, String email, Date created, String createdBy,
                       Boolean isCsMember, Boolean isLoginManagement) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created = created;
        this.createdBy = createdBy;
        this.isCsMember = isCsMember;
        this.isLoginManagement = isLoginManagement;
    }

    public StaffEntity(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsCsMember() {
        return isCsMember;
    }

    public void setIsCsMember(Boolean isCsMember) {
        this.isCsMember = isCsMember;
    }

    public Boolean getIsLoginManagement() {
        return isLoginManagement;
    }

    public void setIsLoginManagement(Boolean isLoginManagement) {
        this.isLoginManagement = isLoginManagement;
    }
}
