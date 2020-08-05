//package com.server.grpc.component;
//
////<editor-fold defaultstate="collapsed" desc="IMPORT">
//import com.example.management.entity.JobEntity;
//import com.example.management.exception.UrlException;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.jayway.jsonpath.Configuration;
//import com.jayway.jsonpath.InvalidJsonException;
//import com.jayway.jsonpath.JsonPath;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.StringJoiner;
//import java.util.stream.Collectors;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
////</editor-fold>
//
///**
// *
// * @author Hung Hau
// */
//@Component
//@Slf4j
//public class JsonUrlUtils extends URLUtils {
//
//    private static final String DOMAIN = "https://www.vietnamworks.com/";
//
//    //<editor-fold defaultstate="collapsed" desc="ANALYST DATA">
//    @Override
//    public List<JobModel> analyticsData(String url, Map<String, String> queryMap, String body) throws UrlException {
//        List<JobEntity> resultList = new ArrayList<>();
//        try {
//            //Parse json from html result
//            String json = MyConnection.callPostMethod(url, body);
//            Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
//
//            //Get number item
//            int numberItem = JsonPath.read(document, "$.results[0].hits.length()");
//            for (int i = 0; i < numberItem; i++) {
//                resultList.add(makeJobEntity(document, queryMap, i));
//            }
//        } catch (InvalidJsonException | IOException ex) {
//            log.error("Error when parse Json of JsonPath", ex);
//        }
//        return resultList;
//    }
//    
//    private JobEntity makeJobEntity(Object document, Map<String, String> queryMap, int index) {
//        String[] descriptionQueryArray = makeQueryChecker(queryMap, "DESCRIPTION", index).split("\n");
//        String description = makeDescription(document, descriptionQueryArray);
//        String title = JsonPath.read(document, makeQueryChecker(queryMap, "TITLE", index));
//        String company = JsonPath.read(document, makeQueryChecker(queryMap, "COMPANY", index));
//        String link = makeLink(document, makeQueryChecker(queryMap, "LINK", index).split("\n"));
//        String tags = makeDataFromJsonArray(document, makeQueryChecker(queryMap, "TAG", index));
//        String address = makeDataFromJsonArray(document, makeQueryChecker(queryMap, "ADDRESS", index));
//        int publishDate = JsonPath.read(document, makeQueryChecker(queryMap, "DATE_POST", index));
//        int expiredDate = JsonPath.read(document, makeQueryChecker(queryMap, "DATE_EXPIRED", index));
//        JsonObject object = new JsonObject();
//        object.addProperty("Name", company);
//        return new JobEntity(0, title, object.toString(), "", makeDatePost(publishDate), makeDatePost(expiredDate), description, link, tags, address, new Date());
//    }
//
//    private String makeDescription(Object document, String[] descriptionQueryArray) {
//        Map<String, String> desMap = new HashMap<>();
//        for (String query : descriptionQueryArray) {
//            Object data = JsonPath.read(document, query);
//            String keyOfDescription = query.substring(query.lastIndexOf(".") + 1);
//            if (data instanceof List) {
//                desMap.put(keyOfDescription, String.join("\n", (List<String>) data));
//            } else {
//                desMap.put(keyOfDescription, String.valueOf(data));
//            }
//        }
//        return new Gson().toJson(desMap);
//    }
//
//    private String makeLink(Object document, String[] linkQueryArray) {
//        StringJoiner joiner = new StringJoiner("-");
//        for (String query : linkQueryArray) {
//            Object data = JsonPath.read(document, query);
//            joiner.add(String.valueOf(data));
//        }
//        return DOMAIN + joiner.toString() + "-jd";
//    }
//
//    private String makeDataFromJsonArray(Object document, String query) {
//        List<String> dataList = JsonPath.read(document, query);
//        return dataList.stream().map(item -> item.replace(" ", "-")).collect(Collectors.joining(" "));
//    }
//
//    private Date makeDatePost(int data) {
//        return new Date(Long.valueOf(data + "000"));
//    }
//
//    private String makeQueryChecker(Map<String, String> queryMap, String keyMap, int i) {
//        String value = queryMap.get(keyMap);
//        return value.replace("{item}", String.valueOf(i));
//    }
//    //</editor-fold>
//}
