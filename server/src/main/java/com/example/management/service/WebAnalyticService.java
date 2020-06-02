package com.example.management.service;

import com.example.management.component.JsonUrlUtils;
import com.example.management.component.ProxyUrlUtils;
import com.example.management.entity.JobEntity;
import com.example.management.entity.QueryCheckerEntity;
import com.example.management.entity.TagEntity;
import com.example.management.entity.WebAnalyticEntity;
import com.example.management.exception.UrlException;
import com.example.management.repository.JobRepository;
import com.example.management.repository.QueryCheckerRepository;
import com.example.management.repository.TagRepository;
import com.example.management.repository.WebAnalyticRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
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
    private ProxyUrlUtils urlUtils;

    @Autowired
    private JsonUrlUtils jsonUrlUtils;

    @Autowired
    private QueryCheckerRepository queryCheckerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<JobEntity> analytics() {
        List<JobEntity> resultList = new ArrayList<>();
//        resultList.addAll(analyticsVietNamWork());
        resultList.addAll(analyticsItViec());
        return resultList;
    }

    public List<JobEntity> analyticsVietNamWork() {
        List<JobEntity> saveList = new ArrayList<>();
        WebAnalyticEntity webAnalyticEntity = webAnalyticRepository.findById(2).get();
        List<QueryCheckerEntity> queryCheckerList = queryCheckerRepository.findActiveList(2);
        Map<String, String> selectorMap = queryCheckerList.stream().collect(
                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        int page = 1;
        try {
            List<JobEntity> list = jsonUrlUtils.analyticsData(webAnalyticEntity.getLink(), selectorMap);
            boolean isStopRun = false;
            for (JobEntity entity : list) {
                JobEntity jobExist = jobRepository.findByDatePostAndTitle(entity.getDatePost(), entity.getTitle());
                if (jobExist != null) {
                    isStopRun = true;
                    break;
                }
                String tagIdJoiner = makeTagIdJoiner(entity.getTagIds());
                entity.setTagIds(tagIdJoiner);
                saveList.add(entity);
            }
            jobRepository.saveAll(saveList);
        } catch (UrlException ex) {
            Logger.getLogger(WebAnalyticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saveList;
    }

    public List<JobEntity> analyticsItViec() {
        List<JobEntity> resultList = new ArrayList<>();
        WebAnalyticEntity webAnalyticEntity = webAnalyticRepository.findById(1).get();
        List<QueryCheckerEntity> queryCheckerList = queryCheckerRepository.findActiveList(1);
        Map<String, String> selectorMap = queryCheckerList.stream().collect(
                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        int page = 1;
        while (true) {
            try {
                resultList.addAll(analyticsUrl(webAnalyticEntity.getLink() + "?page=" + page++, selectorMap));
            } catch (UrlException ex) {
                Logger.getLogger(WebAnalyticService.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
        return resultList;
    }

    private List<JobEntity> analyticsUrl(String url, Map<String, String> selectorMap) throws UrlException {
        List<JobEntity> list = urlUtils.analyticsData(url, selectorMap);
        if (list.isEmpty()) {
            throw new UrlException("Can find any Job");
        }
        boolean isStopRun = false;
        List<JobEntity> saveList = new ArrayList<>();
        for (JobEntity entity : list) {
            JobEntity jobExist = jobRepository.findByDatePostAndTitle(entity.getDatePost(), entity.getTitle());
            if (jobExist != null) {
                isStopRun = true;
                break;
            }
            String tagIdJoiner = makeTagIdJoiner(entity.getTagIds());
            entity.setTagIds(tagIdJoiner);
            saveList.add(entity);
        }
        System.out.println("Size of list " + saveList.size());
        jobRepository.saveAll(saveList);
        if (isStopRun) {
            throw new UrlException("Found all new jobs");
        }
        return list;
    }

    private String makeTagIdJoiner(String tagNames) {
        String[] tagNameArray = tagNames.split(" ");
        StringJoiner joiner = new StringJoiner(",");
        for (String tag : tagNameArray) {
            TagEntity entity = tagRepository.findByName(tag);
            if (entity == null) {
                entity = new TagEntity();
                entity.setName(tag);
                entity = tagRepository.save(entity);
            }

            joiner.add(String.valueOf(entity.getId()));
        }
        return joiner.toString();
    }

    public List<JobEntity> findAll() {
        List<JobEntity> jobList = (List<JobEntity>) jobRepository.findAll();
        for (JobEntity entity : jobList) {
            String[] tagIdArray = entity.getTagIds().split(",");
            entity.setTagIds(makeTagNameJoiner(tagIdArray));
        }
        return jobList;
    }

    private String makeTagNameJoiner(String[] tagIdArray) {
        StringJoiner joiner = new StringJoiner(" ");
        for (String id : tagIdArray) {
            Optional<TagEntity> entity = tagRepository.findById(Integer.parseInt(id));
            if (entity.isPresent()) {
                joiner.add(entity.get().getName());
            }
        }
        return joiner.toString();
    }

}
