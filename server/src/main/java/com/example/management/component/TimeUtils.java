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
            cal.add(time * -1, Calendar.HOUR);
        } else if (unitBefore.startsWith("minute")) {
            cal.add(time * -1, Calendar.MINUTE);
        } else {
            cal.add(time * -1, Calendar.DATE);
        }
        return cal.getTime();
    }

}
