/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.component;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class TimeUtils {

    static Date createDate(int time, String unitBefore) {
        Date dateNow = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateNow);
        if (unitBefore.startsWith("hour")) {
            cal.add(Calendar.HOUR, time * -1);
        } else if (unitBefore.startsWith("minute")) {
            cal.add(Calendar.MINUTE, time * -1);
        } else {
            cal.add(Calendar.DATE, time * -1);
        }
        return cal.getTime();
    }

}
