/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.component;

import com.example.management.entity.JobEntity;
import com.example.management.entity.QueryCheckerEntity;
import com.example.management.exception.UrlException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author USER
 */
@Component
public class ProxyUrlUtils implements URLUtils {

    @Value("${login.email}")
    private String email;

    @Value("${login.password}")
    private String password;

    @Override
    public List<JobEntity> analyticsData(String url, Map<String, String> queryMap) throws UrlException {
//        Map<String, String> cookieMap = getCookieMap(url);\
        List<JobEntity> resultList = new ArrayList<>();
        Response res = null;
        try {
            URL urlObject = new URL(url);
            String urlMain = urlObject.getProtocol() + "://" + urlObject.getHost();
            res = connectURL(url, getCookieMap(urlMain), true);

            if (res.statusCode() != 200 || res.parse().html().isEmpty()) {
                throw new UrlException("Fail get html content");
            }
            Document document = Jsoup.parse(res.parse().html());
            Elements elements = document.select(queryMap.get("ITEM"));//get list item result

            for (Element element : elements) {
                resultList.add(createJobEntity(url, element, queryMap));
            }
        } catch (IOException ex) {
            Logger.getLogger(ProxyUrlUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new UrlException("Fail get html content");
        }

        return resultList;
    }

    private JobEntity createJobEntity(String mainUrl, Element element, Map<String, String> queryMap) throws UrlException {
        String title = element.select(queryMap.get("TITLE")).text();
        String company = element.select(queryMap.get("COMPANY")).attr("alt");
        String requireYear = "";// element.select(queryMap.get("REQUIRE_YEAR")).text();
        String datePost = element.select(queryMap.get("DATE_POST")).text();
        String description = element.select(queryMap.get("DESCRIPTION")).text();
        String link = makeFullUrl(mainUrl, element.select(queryMap.get("LINK")).attr("href"));
        String tag = element.select(queryMap.get("TAG")).text();
        String address = element.select(queryMap.get("ADDRESS")).text();
        Date date = TimeUtils.createDate(datePost);
        return new JobEntity(Integer.SIZE, title, company, requireYear, date, description, link, tag, address, null);
    }

    private String makeFullUrl(String mainUrl, String link) throws UrlException {
        URL url;
        try {
            url = new URL(mainUrl);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ProxyUrlUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new UrlException("Can't parse Url from " + mainUrl);
        }
        return url.getProtocol() + "://" + url.getHost() + link;
    }

    public Response login(String csrf, String url, Map<String, String> cookieMap, boolean isFollowRedirect) {
        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", "host");
        System.getProperties().put("proxyPort", "port");
        System.setProperty("https.protocols", "TLSv1.2");
        try {
            Response res = Jsoup.connect(url)
                    .data("user[email]", email)
                    .data("user[password]", password)
                    .data("x-csrf-token", csrf)
                    .data("authenticity_token", csrf)
                    .cookies(cookieMap)
                    .method(Connection.Method.POST)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                    .timeout(30000).ignoreContentType(true).ignoreHttpErrors(true).followRedirects(isFollowRedirect).execute();
            return res;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Response connectURL(String url, Map<String, String> cookieMap, boolean isFollowRedirect) {
        String[] result = {"", "408", ""};
        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", "host");
        System.getProperties().put("proxyPort", "port");
        System.setProperty("https.protocols", "TLSv1.2");
        try {
            Response res = Jsoup.connect(url)
                    .cookies(cookieMap)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                    .timeout(30000).ignoreContentType(true).ignoreHttpErrors(true).followRedirects(isFollowRedirect).execute();
//            result[1] = res.statusCode() + "";
//            result[2] = res.contentType();
//            if (res.statusCode() == 200) {
//                result[0] = res.parse().html();
//            }
            return res;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private Map<String, String> getCookieMap(String urlMain) throws UrlException, IOException {
        Response resp = connectURL(urlMain, new HashMap<String, String>(), false);
        Document doc = resp.parse();
        String csrf = doc.select("input[name=authenticity_token]").val();

        Map<String, String> cookieMap = new HashMap<>();
        resp = login(csrf, urlMain + "/sign_in", resp.cookies(), true);
        System.out.println(resp.parse().html());
//        if (resp.statusCode() != 200) {
//            throw new UrlException("Fail get html content");
//        }
//        Document doc = Jsoup.parse(resultConnect[0]);
        String sessionId = resp.cookie("_ITViec_session");
        System.out.println(sessionId);
        cookieMap.put("_ITViec_session", resp.cookie("_ITViec_session"));
        return cookieMap;
    }

}
