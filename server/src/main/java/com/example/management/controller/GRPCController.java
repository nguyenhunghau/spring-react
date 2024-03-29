/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.controller;

import com.example.management.service.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nguyen Hung Hau
 */
@RestController
public class GRPCController {
    
    @Autowired
    private GrpcClient grpcClient;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> user() {
        return new ResponseEntity<>(grpcClient.sendMessage("hunghau"), HttpStatus.OK);
    }
}
