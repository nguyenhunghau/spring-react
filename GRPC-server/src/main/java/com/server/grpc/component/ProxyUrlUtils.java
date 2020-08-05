package com.server.grpc.component;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.server.grpc.exception.UrlException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//</editor-fold>

/**
 *
 * @author Hung hau
 */

@Slf4j
public class ProxyUrlUtils extends URLUtils {

    private String sessionToken = "";

    //<editor-fold defaultstate="collapsed" desc="ANALYST DATA">
    @Override
    public List<JobModel> analyticsData(String url, Map<String, String> queryMap, String body) throws UrlException {
        List<JobModel> resultList = new ArrayList<>();
        try {
            Document document = getDocument(url);
            Elements elements = document.select(queryMap.get("ITEM"));//get list item result

            for (Element element : elements) {
                resultList.add(createJobEntity(url, element, queryMap));
            }
        } catch (IOException ex) {
            log.error("Error when analyst data of " + url, ex);
            throw new UrlException("Fail get html content");
        }

        return resultList;
    }

    private JobModel createJobEntity(String mainUrl, Element element, Map<String, String> queryMap) throws UrlException {
        String title = element.select(queryMap.get("TITLE")).text();
        String company = element.select(queryMap.get("COMPANY")).attr("alt");
        String requireYear = "";
        String datePost = element.select(queryMap.get("DATE_POST")).text().trim();
        String description = makeDescription(element, queryMap.get("DESCRIPTION").split("\n"));
        String link = makeFullUrl(mainUrl, element.select(queryMap.get("LINK")).attr("href"));
        String tag = element.select(queryMap.get("TAG")).text();
        String address = element.select(queryMap.get("ADDRESS")).text();
        String[] dateArray = datePost.split(" ");
        Date date = dateArray.length > 1 ? TimeUtils.createDate(Integer.parseInt(dateArray[0]), dateArray[1]) : new Date();
        JsonObject object = new JsonObject();
        object.addProperty("Name", company);
        return new JobModel(0, title, object.toString(), requireYear, date, null, description, link, tag, address, new Date());
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
        try {
            URL url = new URL(mainUrl);
            return url.getProtocol() + "://" + url.getHost() + link;
        } catch (MalformedURLException ex) {
            log.error("Error when crate URL object of " + mainUrl, ex);
            throw new UrlException("Can't parse Url from " + mainUrl);
        }
    }

    private Map<String, String> getCookieMap() throws UrlException, IOException {
        Map<String, String> cookieMap = new HashMap<>();
        cookieMap.put("_ITViec_session", sessionToken);
        return cookieMap;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ANALYST DETAIL">
    public JobModel analyticsDetailData(Map<String, String> queryMap, JobModel entity) throws UrlException {
        try {
            Document document = getDocument(entity.getLink());
            return modifyJobEntity(entity, document, queryMap);
        } catch (IOException ex) {
            log.error("Error when when get document", ex);
            throw new UrlException("Fail get html content of " + entity.getLink());
        }
    }

    private Document getDocument(String url) throws UrlException, IOException {
        Response res = connectURL(url, getCookieMap(), true);
        String html = res.parse().html();
        if (res.statusCode() != 200 || html.isEmpty()) {
            throw new UrlException("Fail get html content");
        }
        return Jsoup.parse(html);
    }

    private String makeCompanyData(JobModel entity, Document document, Map<String, String> queryMap) {
        JsonObject object = new JsonParser().parse(entity.getCompany()).getAsJsonObject();
        String companySize = document.select(queryMap.get("COMPANY-SIZE")).text();
        String companyCountry = queryMap.containsKey("COMPANY-COUNTRY")
                ? document.select(queryMap.get("COMPANY-COUNTRY")).text()
                : "";
        object.addProperty("country", companyCountry);
        object.addProperty("size", companySize);
        return object.toString();
    }

    private JobModel modifyJobEntity(JobModel entity, Document document, Map<String, String> queryMap) {
        String companyAddress = document.select(queryMap.get("COMPANY-ADDRESS")).text();
        String requirement = document.select(queryMap.get("REQUIREMENT")).text();
        entity.setRequirement(requirement);
        entity.setCompany(makeCompanyData(entity, document, queryMap));
        entity.setAddress(companyAddress);
        entity.setUpdated(new Date());
        return entity;
    }
    //</editor-fold>

}
