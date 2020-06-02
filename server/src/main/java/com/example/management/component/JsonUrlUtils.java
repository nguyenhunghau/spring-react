/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.component;

import com.example.management.entity.JobEntity;
import com.example.management.exception.UrlException;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Path;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
public class JsonUrlUtils extends URLUtils {

    @Override
    public List<JobEntity> analyticsData(String url, Map<String, String> queryMap) throws UrlException {

        //Connect to get data Json
        List<JobEntity> resultList = new ArrayList<>();
        Connection.Response res = null;
        try {
//            res = connectURL(url, new HashMap<String, String>(), true);
//            String html = res.parse().html();
//            if (res.statusCode() != 200 || html.isEmpty()) {
//                throw new UrlException("Fail get html content");
//            }
            //Parse json from html result
            String json = getJsonResult();
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

            //Get number item
            int numberItem = JsonPath.read(document, "$.results[0].hits.length()");
            for (int i = 0; i < numberItem; i++) {
                String[] descriptionQueryArray = makeQueryChecker(queryMap, "DESCRIPTION", i).split("\n");
                String description = "";
                for (String query : descriptionQueryArray) {
                    List<String> dataList = JsonPath.read(document, query);
                    description += String.join("\n", dataList);
                }
                String title = JsonPath.read(document, makeQueryChecker(queryMap, "TITLE", i));
                String company = JsonPath.read(document, makeQueryChecker(queryMap, "COMPANY", i));
                String link = JsonPath.read(document, makeQueryChecker(queryMap, "LINK", i));
                resultList.add(new JobEntity(0, title, company, title, null, description, link, link, title, null));
            }

            //Handle link from alias
        } catch (Exception ex) {
            Logger.getLogger(JsonUrlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }

    private String getJsonResult() throws UrlException {
        String data = JsonUrlUtils.class.getResource("/vietnamwork.json").getPath().toString().substring(1);
        java.nio.file.Path path = Paths.get(data);
        try {
            return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(JsonUrlUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(path.toString());
            throw new UrlException("Fail to read json content");
        }
    }

    private String makeQueryChecker(Map<String, String> queryMap, String keyMap, int i) {
        String value = queryMap.get(keyMap);
        return value.replace("{item}", String.valueOf(i));
    }
}
