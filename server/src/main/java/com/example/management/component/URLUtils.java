/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.component;

import com.example.management.entity.JobEntity;
import com.example.management.exception.UrlException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author USER
 */
@Component
public interface URLUtils {
    
    public List<JobEntity> analyticsData(String url, Map<String, String> queryMap) throws UrlException;
}
