package com.example.management.component;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.example.management.entity.JobEntity;
import com.example.management.exception.UrlException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//</editor-fold>

/**
 *
 * @author USER
 */
@Component
public class ProxyUrlUtils extends URLUtils {
    
    @Value("${login.email}")
    private String email;
    
    @Value("${login.password}")
    private String password;
    
    @Value("${login.session}")
    private String sessionToken;
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProxyUrlUtils.class);
    
    @Override
    public List<JobEntity> analyticsData(String url, Map<String, String> queryMap, String body) throws UrlException {
        List<JobEntity> resultList = new ArrayList<>();
        Response res = null;
        try {
            URL urlObject = new URL(url);
            String urlMain = urlObject.getProtocol() + "://" + urlObject.getHost();
            res = connectURL(url, getCookieMap(urlMain), true);
            String html = res.parse().html();
            if (res.statusCode() != 200 || html.isEmpty()) {
                throw new UrlException("Fail get html content");
            }
            Document document = Jsoup.parse(html);
            Elements elements = document.select(queryMap.get("ITEM"));//get list item result

            for (Element element : elements) {
                resultList.add(createJobEntity(url, element, queryMap));
            }
        } catch (IOException ex) {
            logger.error("Error when analyst data of " + url, ex);
            throw new UrlException("Fail get html content");
        }
        
        return resultList;
    }
    
    private JobEntity createJobEntity(String mainUrl, Element element, Map<String, String> queryMap) throws UrlException {
        String title = element.select(queryMap.get("TITLE")).text();
        String company = element.select(queryMap.get("COMPANY")).attr("alt");
        String requireYear = "";// element.select(queryMap.get("REQUIRE_YEAR")).text();
        String datePost = element.select(queryMap.get("DATE_POST")).text().trim();
        String description = makeDescription(element, queryMap.get("DESCRIPTION").split("\n"));
        String link = makeFullUrl(mainUrl, element.select(queryMap.get("LINK")).attr("href"));
        String tag = element.select(queryMap.get("TAG")).text();
        String address = element.select(queryMap.get("ADDRESS")).text();
        String[] dateArray = datePost.split(" ");
        Date date = dateArray.length > 1 ? TimeUtils.createDate(Integer.parseInt(dateArray[0]), dateArray[1]) : new Date();
        return new JobEntity(0, title, company, requireYear, date, null, description, link, tag, address, new Date());
    }
    
    private String makeDescription(Element element, String[] descriptionQueryArray) {
        Map<String, String> desMap = new HashMap<>();
        for (String query : descriptionQueryArray) {
            String keyOfDescription = query.substring(query.lastIndexOf(".") + 1);
            desMap.put(keyOfDescription, element.select(query).text());
        }
        return new Gson().toJson(desMap);
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
    
    private Map<String, String> getCookieMap(String urlMain) throws UrlException, IOException {
//        Response resp = connectURL(urlMain, new HashMap<String, String>(), false);
//        Document doc = resp.parse();
//        String csrf = doc.select("input[name=authenticity_token]").val();

        Map<String, String> cookieMap = new HashMap<>();
//        resp = login(csrf, urlMain + "/sign_in", resp.cookies(), true);
//        System.out.println(resp.parse().html());
////        if (resp.statusCode() != 200) {
////            throw new UrlException("Fail get html content");
////        }
////        Document doc = Jsoup.parse(resultConnect[0]);
//        String sessionId = resp.cookie("_ITViec_session");
//        System.out.println(sessionId);
        cookieMap.put("_ITViec_session", sessionToken);
        return cookieMap;
    }
    
    public JobEntity analyticsDetailData(Map<String, String> queryMap, JobEntity entity) throws UrlException {
        try {
            Response res = connectURL(entity.getLink(), getCookieMap(entity.getLink()), true);
            String html = res.parse().html();
            if (res.statusCode() != 200 || html.isEmpty()) {
                throw new UrlException("Fail get html content");
            }
            Document document = Jsoup.parse(html);
            //Query to select data
            JsonObject object = new JsonParser().parse(entity.getCompany()).getAsJsonObject();
            String companySize = document.select(queryMap.get("COMPANY-SIZE")).text();
            String companyCountry = queryMap.containsKey("COMPANY-COUNTRY")?
                    document.select(queryMap.get("COMPANY-COUNTRY")).text()
                    : "";
            String companyAddress = document.select(queryMap.get("COMPANY-ADDRESS")).text();
            String requirement = document.select(queryMap.get("REQUIREMENT")).text();
            
            object.addProperty("country", companyCountry);
            object.addProperty("size", companySize);
            entity.setRequirement(requirement);
            entity.setCompany(object.toString());
            entity.setAddress(companyAddress);
            entity.setUpdated(new Date());
        } catch (IOException ex) {
            throw new UrlException("Fail get html content of " + entity.getLink());
        }
        return entity;
    }
    
}
