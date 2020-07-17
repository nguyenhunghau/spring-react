package com.example.management.service;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.example.management.component.JsonUrlUtils;
import com.example.management.component.ProxyUrlUtils;
import com.example.management.dto.JobCompanyDTO;
import com.example.management.dto.JobSearchDTO;
import com.example.management.entity.JobEntity;
import com.example.management.entity.QueryCheckerEntity;
import com.example.management.entity.TagEntity;
import com.example.management.entity.WebAnalyticEntity;
import com.example.management.exception.UrlException;
import com.example.management.repository.JobRepository;
import com.example.management.repository.QueryCheckerRepository;
import com.example.management.repository.TagRepository;
import com.example.management.repository.WebAnalyticRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
//</editor-fold>

/**
 *
 * @author Nguyen Hung hau
 */
@Service
public class WebAnalyticService {

    //<editor-fold defaultstate="collapsed" desc="VARIABLES">
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

    @Value("${data.vietnamwork}")
    private String bodyAPI;

    private static final Logger logger = LoggerFactory.getLogger(WebAnalyticService.class);
    //</editor-fold>

    public  Map<String, Integer> analyticsFuture(Future<Integer> futureIT, Future<List<JobEntity>> futureVNWork) {
        Map<String, Integer> resultMap = new HashMap<>();
        try {
            while (!(futureIT.isDone() && futureVNWork.isDone())) {
                if (futureIT.isDone() && futureVNWork.isDone()) {
                    resultMap.put("itviec", futureIT.get());
                    resultMap.put("vnwork", futureVNWork.get().size());
                }
            }
        } catch (InterruptedException ex) {
            logger.error("Error when sleep", ex);
        } catch (ExecutionException ex) {
            logger.error("Error when sleep", ex);
        }

        return resultMap;
    }
    //<editor-fold defaultstate="collapsed" desc="ANALYTICS JOB">
    public Map<String, Integer> analytics() {
        Map<String, Integer> resultMap = new HashMap<>();
        Future<Integer> futureIT = analyticsItViec();
        Future futureVNWork = analyticsVietNamWork();
        try {
            Thread.sleep(100);
            while (!(futureIT.isDone() && futureVNWork.isDone())) {
                if (futureIT.isDone() && futureVNWork.isDone()) {
                    resultMap.put("itviec", futureIT.get());
                    resultMap.put("vnwork", ((List<JobEntity>)futureVNWork.get()).size());
                }
            }
        } catch (InterruptedException ex) {
            logger.error("Error when sleep", ex);
        } catch (ExecutionException ex) {
            logger.error("Error when sleep", ex);
        }

        return resultMap;
    }

    public List<TagEntity> getListTag() {
        return (List<TagEntity>) tagRepository.findAll();
    }

    @Async("threadPoolTaskExecutor")
    public Future<List<JobEntity>> analyticsVietNamWork() {
        List<JobEntity> resultList = new ArrayList<>();
        WebAnalyticEntity webAnalyticEntity = webAnalyticRepository.findById(2).get();
        List<QueryCheckerEntity> queryCheckerList = queryCheckerRepository.findActiveList(2);
        Map<String, String> selectorMap = queryCheckerList.stream().collect(
                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        int page = 0;
        try {
            while (true) {
                List<JobEntity> saveList = new ArrayList<>();
                String body = bodyAPI.replace("{item}", String.valueOf(page++));
                List<JobEntity> list = jsonUrlUtils.analyticsData(webAnalyticEntity.getLink(), selectorMap, body);
                if (list.isEmpty()) {
                    return new AsyncResult(resultList);
                }
                boolean isStopRun = false;
                for (JobEntity entity : list) {
                    JobEntity jobExist = jobRepository.findByLinkAndTitle(entity.getLink(), entity.getTitle());
                    if (jobExist != null) {
                        isStopRun = true;
                        break;
                    }
                    String tagIdJoiner = makeTagIdJoiner(entity.getTagIds());
                    entity.setTagIds(tagIdJoiner);
                    saveList.add(entity);
                }
                jobRepository.saveAll(saveList);
                resultList.addAll(saveList);
                if (isStopRun) {
                    return new AsyncResult(resultList);
                }
            }

        } catch (UrlException ex) {
            logger.error("Error when analytics data VietnamWork", ex);
        }
        return new AsyncResult(new ArrayList<>());
    }

    @Async("threadPoolTaskExecutor")
    public Future<Integer> analyticsItViec() {
        int result = 0;
        WebAnalyticEntity webAnalyticEntity = webAnalyticRepository.findById(1).get();
        List<QueryCheckerEntity> queryCheckerList = queryCheckerRepository.findActiveList(1);
        Map<String, String> selectorMap = queryCheckerList.stream().collect(
                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        int page = 1;
        while (true) {
            try {
                result += analyticsUrl(webAnalyticEntity.getLink() + "?page=" + page++, selectorMap).size();
            } catch (UrlException ex) {
                logger.error("Error when analytics data IT Viec", ex);
                result += Integer.parseInt(ex.getMessage().split("\n")[1]);
                break;
            }
        }
        return new AsyncResult(result);
    }

    private List<JobEntity> analyticsUrl(String url, Map<String, String> selectorMap) throws UrlException {
        List<JobEntity> list = urlUtils.analyticsData(url, selectorMap, "");
        if (list.isEmpty()) {
            throw new UrlException("Can find any Job\n0");
        }
        boolean isStopRun = false;
        List<JobEntity> saveList = new ArrayList<>();
        for (JobEntity entity : list) {
            JobEntity jobExist = jobRepository.findByLinkAndTitle(entity.getLink(), entity.getTitle());
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
            throw new UrlException("Found all new jobs\n" + saveList.size());
        }
        return saveList;
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET ALL">
    public List<JobEntity> findAll(JobSearchDTO jobSearchDTO) {
        List<JobEntity> resultList = new ArrayList<>();
        String company = "";
        try {
            company = URLDecoder.decode(jobSearchDTO.getCompany(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            java.util.logging.Logger.getLogger(WebAnalyticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<JobEntity> jobList = jobSearchDTO.getShowAll() ? (List<JobEntity>) jobRepository.findAll() : jobRepository.findAllNotExpired(new Date());
        List<TagEntity> tagList = (List<TagEntity>) tagRepository.findAll();
        for (JobEntity entity : jobList) {
            JsonObject object = new JsonParser().parse(entity.getCompany()).getAsJsonObject();
            if (!company.isEmpty() && !company.equals(object.get("Name").getAsString())) {
                continue;
            }
            String[] tagIdArray = entity.getTagIds().split(",");
            entity.setTagIds(makeTagNameJoiner(tagIdArray, tagList));
            resultList.add(entity);
        }
        return resultList;
    }

    private String makeTagNameJoiner(String[] tagIdArray, List<TagEntity> tagList) {
        StringJoiner joiner = new StringJoiner(" ");
        for (String id : tagIdArray) {
            Optional<TagEntity> entity = tagList.stream().filter(item -> item.getId() == Integer.parseInt(id))
                    .findFirst();
            if (entity.isPresent()) {
                joiner.add(entity.get().getName());
            }
        }
        return joiner.toString();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET JOB BY COMPANY">
    public List<JobCompanyDTO> findJobListByCompany() {
        List<JobCompanyDTO> resultList = new ArrayList<>();
        List<JobEntity> list = (List<JobEntity>) jobRepository.findAll();
        list.sort(Comparator.comparing(JobEntity::getCompany));
        String company = "";
        List<JobEntity> jobCompanyList = new ArrayList<>();
        List<TagEntity> tagEntityList = (List<TagEntity>) tagRepository.findAll();
        for (JobEntity entity : list) {
            if (!company.isEmpty() && !company.equals(entity.getCompany())) {
                resultList.add(new JobCompanyDTO(company, makeCompanyTags(jobCompanyList, tagEntityList), jobCompanyList));
                jobCompanyList.clear();
            }
            jobCompanyList.add(entity);
            company = entity.getCompany();
        }
        resultList.add(new JobCompanyDTO(company, makeCompanyTags(jobCompanyList, tagEntityList), jobCompanyList));
        return resultList;
    }

    private String makeCompanyTags(List<JobEntity> jobCompanyList, List<TagEntity> tagEntityList) {
        Set<String> tagSet = new HashSet<>();
        for (JobEntity job : jobCompanyList) {
            tagSet.addAll(new HashSet<>(Arrays.asList(job.getTagIds().split(","))));
        }
        return makeTagNameJoiner(tagSet.toArray(new String[tagSet.size()]), tagEntityList);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ANALYTICS DETAIL URLS">
    public void analyticsDetail(int numItem) {
        List<QueryCheckerEntity> queryCheckerITViecList = queryCheckerRepository.findActiveList(1);
        List<QueryCheckerEntity> queryCheckerVNWorkList = queryCheckerRepository.findActiveList(2);

        List<JobEntity> list = jobRepository.findJobNotAnalyticsDetail(new PageRequest(0, numItem));
        for (JobEntity job : list) {
            try {
                JobEntity newEntity;
                if (job.getLink().contains("itviec")) {
                    newEntity = analyticsDetailJob(job, queryCheckerITViecList);
                } else {
                    newEntity = analyticsDetailJob(job, queryCheckerVNWorkList);
                }
                jobRepository.save(newEntity);
            } catch (UrlException ex) {
                logger.error("Error when analytics data detail of link " + job.getLink(), ex);
            }
        }
    }

    private JobEntity analyticsDetailJob(JobEntity job, List<QueryCheckerEntity> queryCheckerList) throws UrlException {
        Map<String, String> selectorMap = queryCheckerList.stream().collect(
                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        return urlUtils.analyticsDetailData(selectorMap, job);
    }
    //</editor-fold>

}
