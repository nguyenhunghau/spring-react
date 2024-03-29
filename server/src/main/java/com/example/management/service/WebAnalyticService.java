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
import com.example.management.grpc.JobGRPCClient;
import com.example.management.repository.JobRepository;
import com.example.management.repository.QueryCheckerRepository;
import com.example.management.repository.TagRepository;
import com.example.management.repository.WebAnalyticRepository;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
//</editor-fold>

/**
 *
 * @author Nguyen Hung hau
 */
@Service
@Slf4j
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
    
    @Autowired
    private JobGRPCClient jobGRPCClient;

    @Autowired
    private JobCache jobCache;

    @Value("${data.vietnamwork}")
    private String bodyAPI;
    //</editor-fold>

    public JobEntity findById(Integer id)  {
        JobEntity entity = jobCache.getOnCache(id);
        return entity;
    }

    //<editor-fold defaultstate="collapsed" desc="ANALYTICS JOB">
    public Map<String, Integer> analytics() {
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("itviec", analyticsItViec());
        resultMap.put("vnwork", analyticsItViec());
        return resultMap;
    }

    //<editor-fold defaultstate="collapsed" desc="ANALYST JOB OF VN WORK">
    private int analyticsVietNamWork() {
        int result = 0;
        WebAnalyticEntity webAnalyticEntity = webAnalyticRepository.findById(2).get();
        List<QueryCheckerEntity> queryCheckerList = queryCheckerRepository.findActiveList(2);
        Map<String, String> selectorMap = queryCheckerList.stream().collect(
                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        int page = 0;
        try {
            while (true) {
                String body = bodyAPI.replace("{item}", String.valueOf(page++));
                List<JobEntity> list = jsonUrlUtils.analyticsData(webAnalyticEntity.getLink(), selectorMap, body);
                if (list.isEmpty()) {
                    return result;
                }
                List<JobEntity> saveList = saveJobEntityList(list);
                result += saveList.size();
                if (saveList.size() < list.size()) {
                    return result;
                }
            }

        } catch (UrlException ex) {
            log.error("Error when analytics data VietnamWork", ex);
            return result;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ANALYST JOB IT VIEC GRPC">
    public int analyticsItViecUseGrpc() {
        JobEntity jobExist = jobRepository.findLastItem();
        List<JobEntity> jobEntityList = jobGRPCClient.getDataListITViec(jobExist);
        jobRepository.saveAll(jobEntityList);
        return jobEntityList.size();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ANALYST JOB OF IT VIEC">
    public int analyticsItViec() {
        int result = 0;
        WebAnalyticEntity webAnalyticEntity = webAnalyticRepository.findById(1).get();
        List<QueryCheckerEntity> queryCheckerList = queryCheckerRepository.findActiveList(1);
        Map<String, String> selectorMap = queryCheckerList.stream().collect(
                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        int page = 1;
        try {
            while (true) {
                List<JobEntity> jobEntityList = urlUtils.analyticsData(webAnalyticEntity.getLink() + "?page=" + page++, selectorMap, "");
                if (jobEntityList.isEmpty()) {
                    return result;
                }
                List<JobEntity> saveList = saveJobEntityList(jobEntityList);
                result += saveList.size();
                if (saveList.size() < jobEntityList.size()) {
                    return result;
                }
            }
        } catch (UrlException ex) {
            log.error("Error when analytics data IT Viec", ex);
            return result;
        }
    }
    //</editor-fold>
    
    private List<JobEntity> saveJobEntityList(List<JobEntity> dataList) {
        List<JobEntity> saveList = new ArrayList<>();
        List<JobEntity> result = new ArrayList<>();
        int i = 0;
        for (JobEntity entity : dataList) {
            JobEntity jobExist = jobRepository.findByLinkAndTitle(entity.getLink(), entity.getTitle());
            if (jobExist != null) {
                break;
            }
            String tagIdJoiner = makeTagIdJoiner(entity.getTagIds());
            entity.setTagIds(tagIdJoiner);
            saveList.add(entity);
            i++;
            if(i > 10) {
                i = 0;
                result.addAll((List<JobEntity>)jobRepository.saveAll(saveList));
                saveList.clear();
            }
        }
        result.addAll((List<JobEntity>)jobRepository.saveAll(saveList));
        return result;
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
        } catch (UnsupportedEncodingException | NullPointerException ex) {
            java.util.logging.Logger.getLogger(WebAnalyticService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<JobEntity> jobList = jobSearchDTO.getShowAll() ? (List<JobEntity>) jobRepository.findAll() : jobRepository.findAllNotExpired(new Date());
        List<TagEntity> tagList = (List<TagEntity>) tagRepository.findAll();
        for (JobEntity entity : jobList) {
//            JsonObject object = new JsonParser().parse(entity.getCompany()).getAsJsonObject();
//            if (!company.isEmpty() && !company.equals(object.get("Name").getAsString())) {
//                continue;
//            }
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
    public List<JobCompanyDTO> findJobListByCompany() throws ExecutionException, InterruptedException {
        List<JobCompanyDTO> resultList = new ArrayList<>();
        Future<List<JobEntity>> future = jobRepository.findAllJobs();
        List<JobEntity> list = future.get();// (List<JobEntity>) jobRepository.findAll();
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
                log.error("Error when analytics data detail of link " + job.getLink(), ex);
            }
        }
    }

    private JobEntity analyticsDetailJob(JobEntity job, List<QueryCheckerEntity> queryCheckerList) throws UrlException {
        Map<String, String> selectorMap = queryCheckerList.stream().collect(
                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        return urlUtils.analyticsDetailData(selectorMap, job);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET TAG LIST">
    public List<TagEntity> getListTag() {
        return (List<TagEntity>) tagRepository.findAll();
    }
    //</editor-fold>
}
