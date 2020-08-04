package com.example.management.service;

import com.example.management.dto.MenuDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Hung Hau
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
