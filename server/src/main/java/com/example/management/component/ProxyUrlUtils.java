/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.component;

import com.example.management.entity.JobEntity;
import com.example.management.entity.QueryCheckerEntity;
import com.example.management.exception.UrlException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 *
 * @author USER
 */
@Component
public class ProxyUrlUtils implements URLUtils {

    @Override
    public List<JobEntity> analyticsData(String url, Map<String, String> queryMap) throws UrlException {
        String[] resultConnect = connectURL(url, true);
        if (!resultConnect[1].equals("200") || resultConnect[0].isEmpty()) {
            throw new UrlException("Failt get html content");
        }

        /**
         * Create document from html Get elements by QUERY_TYPE 'item' Each
         * element we will analytics TITLE', 'COMPANY', 'DATE_POST',
         * 'DESCRIPTION', 'LINK', 'TAG', 'ADDRESS' from link we continue connect
         * to get REQUIRE_YEAR, : later
         */
        Document document = Jsoup.parse(resultConnect[0]);
        Elements elements = document.select(queryMap.get("ITEM"));//get list item result
        List<JobEntity> resultList = new ArrayList<>();
        for (Element element : elements) {
            resultList.add(createJobEntity(element, queryMap));
        }
        return resultList;
    }

    private JobEntity createJobEntity(Element element, Map<String, String> queryMap) {
        String title = element.select(queryMap.get("TITLE")).text();
        String company = element.select(queryMap.get("COMPANY")).attr("alt");
        String requireYear = "";// element.select(queryMap.get("REQUIRE_YEAR")).text();
        String datePost = element.select(queryMap.get("DATE_POST")).text();
        String description = element.select(queryMap.get("DESCRIPTION")).text();
        String link = element.select(queryMap.get("LINK")).attr("href");
        String tag = element.select(queryMap.get("TAG")).text();
        String address = element.select(queryMap.get("ADDRESS")).text();
        return new JobEntity(Integer.SIZE, title, company, requireYear, null, description, link, tag, address, null);
    }

    public String[] connectURL(String url, boolean isFollowRedirect) {
        String[] result = {"", "408", ""};
        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", "host");
        System.getProperties().put("proxyPort", "port");
        System.setProperty("https.protocols", "TLSv1.2");
        try {
            Response res = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                    .timeout(30000).ignoreContentType(true).ignoreHttpErrors(true).followRedirects(isFollowRedirect).execute();
            result[1] = res.statusCode() + "";
            result[2] = res.contentType();
            if (res.statusCode() == 200) {
                result[0] = res.parse().html();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

}
