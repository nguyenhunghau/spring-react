/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.controller;

import com.example.management.entity.StaffEntity;
import com.example.management.exception.NullException;
import com.example.management.service.StaffService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nguyen Hung Hau
 * @email nguyenhunghau@fabercompany.co.jp
 */
@RestController
public class AccountController {
    
    @Autowired
    private StaffService staffService;

    @RequestMapping("/getStatusAccountUsing")
    public ResponseEntity<List<StaffEntity>> index(@RequestParam(value = "id", defaultValue="0") String id) {
        
        if(id.equals("0")) {
            throw new NullException();
        }
        
        return new ResponseEntity<>(staffService.getStatusAccountUsing(), HttpStatus.OK);
    }
}
