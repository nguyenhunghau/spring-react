package com.example.management.component;

import com.example.management.entity.JobEntity;
import com.example.management.exception.UrlException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nguyen Hung Hau
 */
@Component
@Slf4j
public abstract class URLUtils {

    public abstract List<JobEntity> analyticsData(String url, Map<String, String> queryMap, String body) throws UrlException;

    public Connection.Response connectURL(String url, Map<String, String> cookieMap, boolean isFollowRedirect) throws IOException {
        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", "host");
        System.getProperties().put("proxyPort", "port");
        System.setProperty("https.protocols", "TLSv1.2");
        Connection.Response res = Jsoup.connect(url)
                .cookies(cookieMap)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                .timeout(30000).ignoreContentType(true).ignoreHttpErrors(true).followRedirects(isFollowRedirect).execute();
        return res;
    }
}
