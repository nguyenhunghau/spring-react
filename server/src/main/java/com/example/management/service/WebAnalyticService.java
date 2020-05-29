package com.example.management.service;

import com.example.management.component.URLUtils;
import com.example.management.entity.JobEntity;
import com.example.management.entity.QueryCheckerEntity;
import com.example.management.entity.WebAnalyticEntity;
import com.example.management.exception.UrlException;
import com.example.management.repository.JobRepository;
import com.example.management.repository.QueryCheckerRepository;
import com.example.management.repository.WebAnalyticRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nguyen Hung hau
 */
@Service
public class WebAnalyticService {
    
    @Autowired
    private WebAnalyticRepository webAnalyticRepository;
    
    @Autowired
    private URLUtils urlUtils;
    
    @Autowired
    private QueryCheckerRepository queryCheckerRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    public List<JobEntity> analytics() {
        List<JobEntity> resultList = new ArrayList<>();
        List<WebAnalyticEntity> list = webAnalyticRepository.findActiveList();
        List<QueryCheckerEntity> queryCheckerList = queryCheckerRepository.findActiveList();
        Map<String, String> selectorMap = queryCheckerList.stream().collect(
                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        list.forEach(item -> {
            try {
                resultList.addAll(analyticsUrl(item.getLink(), selectorMap));
            } catch (UrlException ex) {
                Logger.getLogger(WebAnalyticService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return resultList;
    }
    
    private List<JobEntity> analyticsUrl(String url, Map<String, String> selectorMap) throws UrlException {
        List<JobEntity> list = urlUtils.analyticsData(url, selectorMap);
        jobRepository.saveAll(list);
        return list;
//        list.forEach(item -> {
//            jobRepository.save(item);
//        });
    }

}
