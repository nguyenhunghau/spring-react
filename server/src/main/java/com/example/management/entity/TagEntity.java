///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.management.entity;
//
//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;
//
///**
// *
// * @author USER
// */
//@Entity
//@Table(name = "tag")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t")
//    , @NamedQuery(name = "Tag.findById", query = "SELECT t FROM Tag t WHERE t.id = :id")
//    , @NamedQuery(name = "Tag.findByName", query = "SELECT t FROM Tag t WHERE t.name = :name")
//    , @NamedQuery(name = "Tag.findByCreated", query = "SELECT t FROM Tag t WHERE t.created = :created")})
//public class TagEntity implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "ID")
//    private Integer id;
//    @Size(max = 50)
//    @Column(name = "NAME")
//    private String name;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "CREATED")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date created;
//
//    public TagEntity() {
//    }
//
//    public TagEntity(Integer id) {
//        this.id = id;
//    }
//
//    public TagEntity(Integer id, Date created) {
//        this.id = id;
//        this.created = created;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Date getCreated() {
//        return created;
//    }
//
//    public void setCreated(Date created) {
//        this.created = created;
//    }
//}
