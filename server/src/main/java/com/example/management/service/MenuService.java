/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.management.service;

import com.example.management.dto.MenuDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Le Tan Phuc
 * @email letanphuc@fabercompany.co.jp
 */
@Service
public class MenuService {
    
    public List<MenuDTO> getList() {
        List<MenuDTO> childMenuDB = new ArrayList<>();
        childMenuDB.add(new MenuDTO("DashBoard 1", "/", "far fa-circle nav-icon", null));
        
        List<MenuDTO> resultList = new ArrayList<>();
        resultList.add(new MenuDTO("DashBoard", "", "nav-icon fas fa-tachometer-alt", childMenuDB));
        resultList.add(new MenuDTO("Good Company", "company", "nav-icon fas fa-tachometer-alt", null));
        return resultList;
    }
}
