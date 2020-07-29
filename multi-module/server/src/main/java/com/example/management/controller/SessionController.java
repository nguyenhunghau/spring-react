/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nguyen Hung Hau
 * @email nguyenhunghau@fabercompany.co.jp
 */
@Controller
public class SessionController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String message, final Model model) {
        if (message != null && !message.isEmpty()) {
            if (message.equals("timeout")) {
                model.addAttribute("message", "Time out");
            }
            if (message.equals("max_session")) {
                model.addAttribute("message", "This accout has been login from another device!");
            }
            if (message.equals("logout")) {
                model.addAttribute("message", "Logout!");
            }
            if (message.equals("error")) {
                model.addAttribute("message", "Login Failed!");
            }
        }
        return "login";
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }

}
