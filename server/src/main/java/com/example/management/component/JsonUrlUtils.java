package com.example.management.component;

import com.example.management.entity.JobEntity;
import com.example.management.exception.UrlException;
import com.example.management.service.WebAnalyticService;
import com.google.gson.Gson;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
public class JsonUrlUtils extends URLUtils {

    private static final String DOMAIN = "https://www.vietnamworks.com/";

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WebAnalyticService.class);

    @Override
    public List<JobEntity> analyticsData(String url, Map<String, String> queryMap, String body) throws UrlException {

        //Connect to get data Json
        List<JobEntity> resultList = new ArrayList<>();
        try {
            //Parse json from html result
            String json = MyConnection.callPostMethod(url, body);
            System.out.println(json);
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

            //Get number item
            int numberItem = JsonPath.read(document, "$.results[0].hits.length()");
            for (int i = 0; i < numberItem; i++) {
                String[] descriptionQueryArray = makeQueryChecker(queryMap, "DESCRIPTION", i).split("\n");
                String description = makeDescription(document, descriptionQueryArray);
                String title = JsonPath.read(document, makeQueryChecker(queryMap, "TITLE", i));
                String company = JsonPath.read(document, makeQueryChecker(queryMap, "COMPANY", i));
                String link = makeLink(document, makeQueryChecker(queryMap, "LINK", i).split("\n"));
                String tags = makeDataFromJsonArray(document, makeQueryChecker(queryMap, "TAG", i));
                String address = makeDataFromJsonArray(document, makeQueryChecker(queryMap, "ADDRESS", i));
                int publishDate = JsonPath.read(document, makeQueryChecker(queryMap, "DATE_POST", i));
                int expiredDate = JsonPath.read(document, makeQueryChecker(queryMap, "DATE_EXPIRED", i));
                resultList.add(new JobEntity(0, title, company, "", makeDatePost(publishDate), makeDatePost(expiredDate), description, link, tags, address, new Date()));
            }
        } catch (InvalidJsonException ex) {
            logger.error("Error when parse Json of JsonPath", ex);
        }
        return resultList;
    }

    private String makeDescription(Object document, String[] descriptionQueryArray) {
        Map<String, String> desMap = new HashMap<>();
        for (String query : descriptionQueryArray) {
            Object data = JsonPath.read(document, query);
            String keyOfDescription = query.substring(query.lastIndexOf(".") + 1);
            if (data instanceof List) {
                desMap.put(keyOfDescription, String.join("\n", (List<String>) data));
            } else {
                desMap.put(keyOfDescription, String.valueOf(data));
            }
        }
        return new Gson().toJson(desMap);
    }

    private String makeLink(Object document, String[] linkQueryArray) {
        StringJoiner joiner = new StringJoiner("-");
        for (String query : linkQueryArray) {
            Object data = JsonPath.read(document, query);
            joiner.add(String.valueOf(data));
        }
        return DOMAIN + joiner.toString() + "-jd";
    }

    private String makeDataFromJsonArray(Object document, String query) {
        List<String> dataList = JsonPath.read(document, query);
        return dataList.stream().map(item -> item.replace(" ", "-")).collect(Collectors.joining(" "));
    }

    private Date makeDatePost(int data) {
        return new Date(Long.valueOf(data + "000"));
    }

    private String getJsonResult() throws UrlException {
        String data = JsonUrlUtils.class.getResource("/vietnamwork.json").getPath().substring(1);
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
