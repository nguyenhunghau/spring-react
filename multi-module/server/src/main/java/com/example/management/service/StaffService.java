/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.management.service;

import com.example.management.entity.StaffEntity;
import com.example.management.repository.StaffRepository;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Hung Hau
 * @email nguyenhunghau@fabercompany.co.jp
 */
@Service
public class StaffService {
    
    @Autowired
    StaffRepository staffRepository;
    
    public List<StaffEntity> getStatusAccountUsing() {
        //String[] dayRange = getTimeRange(days);
        //get list Staff
        List<StaffEntity> listStaff = (List<StaffEntity>)staffRepository.findAll();
        //add not assign 
        StaffEntity notAssign = new StaffEntity(0, "Not Assign", "", null, "", false, false);
        listStaff.add(notAssign);
        return listStaff;
    }
    
    public List<StaffEntity> getStatusAccountUsing(Integer typeStatus, Integer days, Integer numMonth, Integer typeDateStart, Integer typeUse, Integer typeGroup) {
        //String[] dayRange = getTimeRange(days);
        //get list Staff
        List<StaffEntity> listStaff = (List<StaffEntity>)staffRepository.findAll();
        //add not assign 
        StaffEntity notAssign = new StaffEntity(0, "Not Assign", "", null, "", false, false);
        listStaff.add(notAssign);
        
        
//        //get number client of staff
//        Map<Integer, Integer> mapNumClientOfStaff = staffDAO.getNumberClientOfStaff();
//        List<AccountUsingDTO> listAccountAction = staffDAO.getAccountUsingStatus(dayRange[0], dayRange[1], typeUse, "-2", typeStatus, 1, numMonth, typeDateStart, typeGroup);
//        Map<Integer, AccountUsingDTO> mapAccountAction = new HashMap<>();
//        for (AccountUsingDTO accountUsingDTO : listAccountAction) {
//            mapAccountAction.put(accountUsingDTO.getStaffCs(), accountUsingDTO);
//        }
//        for (StaffDTO staffDTO : listStaff) {
//            staffDTO.setNumUserAction(mapAccountAction.containsKey(staffDTO.getId()) ? Integer.parseInt(mapAccountAction.get(staffDTO.getId()).getNumUser()) : 0);
//            staffDTO.setNumUserAssign(mapNumClientOfStaff.containsKey(staffDTO.getId()) ? mapNumClientOfStaff.get(staffDTO.getId()) : 0);
//        }
        return listStaff;
    }
    
    public String[] getTimeRange(Integer days) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int now = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -now + 1);
        Date start = c.getTime();
        c.add(Calendar.DATE, 6);
        Date end = c.getTime();
        if (days > 0) {
            c.add(Calendar.DATE, -13 * days);
            start = c.getTime();
            c.add(Calendar.DATE, 6 * days);
            end = c.getTime();
        }
        String[] dayRange = {dateFormat.format(start), dateFormat.format(end)};
        return dayRange;
    }
}
