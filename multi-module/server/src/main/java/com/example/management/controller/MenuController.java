/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.controller;

import com.example.management.dto.MenuDTO;
import com.example.management.service.MenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Le Tan Phuc
 * @email letanphuc@fabercompany.co.jp
 */
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResponseEntity<List<MenuDTO>> user() {
        return new ResponseEntity<>(menuService.getList(), HttpStatus.OK);
    }
}
