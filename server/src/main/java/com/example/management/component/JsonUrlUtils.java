/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.component;

import com.example.management.entity.JobEntity;
import com.example.management.exception.UrlException;
import com.jayway.jsonpath.JsonPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Connection;

/**
 *
 * @author Admin
 */
public class JsonUrlUtils extends URLUtils{

    @Override
    public List<JobEntity> analyticsData(String url, Map<String, String> queryMap) throws UrlException {
        
        //Connect to get data Json
        List<JobEntity> resultList = new ArrayList<>();
        Connection.Response res = null;
        try {
            res = connectURL(url, new HashMap<String, String>(), true);
            String html = res.parse().html();
            if (res.statusCode() != 200 || html.isEmpty()) {
                throw new UrlException("Fail get html content");
            }
            //Parse json from html result
            String json = "";
            //Get number item
            int numberItem = 0;
            String[] descriptionQueryArray = queryMap.get("").split(",");
            String description = "";
            for(String query: descriptionQueryArray) {
                List<String> authors = JsonPath.read(json, query);
                description += String.join("\n", authors);
            }
            //Handle link from alias
            
        } catch (Exception ex) {
            
        }
        
    }
    
}
