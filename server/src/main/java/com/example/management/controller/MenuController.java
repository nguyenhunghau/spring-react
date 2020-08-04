package com.example.management.controller;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.example.management.dto.MenuDTO;
import com.example.management.service.MenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//</editor-fold>

/**
 *
 * @author Nguyen Hung Hau
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
