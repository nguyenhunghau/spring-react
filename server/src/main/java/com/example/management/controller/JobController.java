/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.controller;

import com.example.management.entity.JobEntity;
import com.example.management.service.WebAnalyticService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USER
 */
@RestController()
public class JobController {
    
    @Autowired
    private WebAnalyticService webAnalyticService;
    
    @RequestMapping(value = "/getListJob", method = RequestMethod.GET)
    public ResponseEntity<List<JobEntity>> index() {
        return new ResponseEntity<>(webAnalyticService.analytics(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<JobEntity>> list() {
        return new ResponseEntity<>(webAnalyticService.findAll(), HttpStatus.OK);
    }
}
