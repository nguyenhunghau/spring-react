package com.server.grpc.service;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.server.grpc.component.JobModel;
import com.server.grpc.component.ProxyUrlUtils;
import com.server.grpc.component.URLUtils;
import com.server.grpc.exception.UrlException;
import com.server.service.JobEntity;
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
import java.util.logging.Level;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
//</editor-fold>

/**
 *
 * @author Nguyen Hung hau
 */
@Slf4j
public class WebAnalyticService {

    //<editor-fold defaultstate="collapsed" desc="VARIABLES">
//    @Autowired
//    private WebAnalyticRepository webAnalyticRepository;
//
//    @Autowired
    private URLUtils urlUtils = new ProxyUrlUtils();
    private final String link = "https://itviec.com/it-jobs/java/ho-chi-minh-hcm";
//
//    @Autowired
//    private JsonUrlUtils jsonUrlUtils;
//
//    @Autowired
//    private QueryCheckerRepository queryCheckerRepository;
//
//    @Autowired
//    private JobRepository jobRepository;
//
//    @Autowired
//    private TagRepository tagRepository;
//
//    @Value("${data.vietnamwork}")
    private String bodyAPI;

    private static final Map<String, String> selectorMap = new HashMap<>();

    static {
        selectorMap.put("TITLE", ".details .title");
        selectorMap.put("COMPANY", ".logo-wrapper img");
        selectorMap.put("DATE_POST", ".distance-time-job-posted");
        selectorMap.put("DESCRIPTION", ".details .salary\n.details .benefits\n.details .description");
        selectorMap.put("LINK", ".details .title a");
        selectorMap.put("TAG", ".tag-list a span");
        selectorMap.put("ADDRESS", ".city .address");
        selectorMap.put("ITEM", "#jobs .job");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ANALYTICS JOB">
//    public Map<String, Integer> analytics(JobEntity entity) {
//        Map<String, Integer> resultMap = new HashMap<>();
//        resultMap.put("itviec", analyticsItViec(entity));
////        resultMap.put("vnwork", analyticsVietNamWork());
//        return resultMap;
//    }

    //<editor-fold defaultstate="collapsed" desc="ANALYST JOB OF VN WORK">
//    private int analyticsVietNamWork() {
//        int result = 0;
//        WebAnalyticEntity webAnalyticEntity = webAnalyticRepository.findById(2).get();
//        List<QueryCheckerEntity> queryCheckerList = queryCheckerRepository.findActiveList(2);
//        Map<String, String> selectorMap = queryCheckerList.stream().collect(
//                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
//        int page = 0;
//        try {
//            while (true) {
//                String body = bodyAPI.replace("{item}", String.valueOf(page++));
//                List<JobModel> list = jsonUrlUtils.analyticsData(webAnalyticEntity.getLink(), selectorMap, body);
//                if (list.isEmpty()) {
//                    return result;
//                }
//                List<JobModel> saveList = saveJobModelList(list);
//                result += saveList.size();
//                if (saveList.size() < list.size()) {
//                    return result;
//                }
//            }
//
//        } catch (UrlException ex) {
//            log.error("Error when analytics data VietnamWork", ex);
//            return result;
//        }
//    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="ANALYST JOB OF IT VIEC">
    public List<JobEntity> analyticsItViec(JobEntity entity) {
        List<JobEntity> resultList = new ArrayList<>();
//        WebAnalyticEntity webAnalyticEntity = webAnalyticRepository.findById(1).get();
//        List<QueryCheckerEntity> queryCheckerList = queryCheckerRepository.findActiveList(1);
//        Map<String, String> selectorMap = queryCheckerList.stream().collect(
//                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
        int page = 1;
        try {
            while (true) {
                List<JobModel> jobEntityList = urlUtils.analyticsData(link + "?page=" + page++, selectorMap, "");
                if (jobEntityList.isEmpty()) {
                    return resultList;
                }
                List<JobModel> saveList = getJobListNotExisted(jobEntityList, entity);
                resultList.addAll(convertData(saveList));
//                if (saveList.size() < jobEntityList.size()) {
                    return resultList;
//                }
            }
        } catch (UrlException ex) {
            log.error("Error when analytics data IT Viec", ex);
            return resultList;
        }
    }
    //</editor-fold>

    private List<JobModel> getJobListNotExisted(List<JobModel> dataList, JobEntity jobEntity) {
        List<JobModel> resultList = new ArrayList<>();
        for (JobModel entity : dataList) {
            if (jobEntity.getLink().equals(entity.getLink()) && jobEntity.getTitle().equals(entity.getTitle())) {
                break;
            }
            String tagIdJoiner = entity.getTagIds();// makeTagIdJoiner(entity.getTagIds());
            entity.setTagIds(tagIdJoiner);
            resultList.add(entity);
        }
        return resultList;
    }
    
    private List<JobEntity> convertData(List<JobModel> jobEntityList) {
         List<JobEntity> resultList = new ArrayList<>();
         for(JobModel model: jobEntityList) {
             JobEntity entity =JobEntity.newBuilder()
                     .setCompany(model.getCompany())
                     .setLink(model.getLink())
                     .setTitle(model.getTitle())
                     .setDescription(model.getDescription())
                     .build();
             resultList.add(entity);
         }
         return resultList;
    }

//    private String makeTagIdJoiner(String tagNames) {
//        String[] tagNameArray = tagNames.split(" ");
//        StringJoiner joiner = new StringJoiner(",");
//        for (String tag : tagNameArray) {
//            TagEntity entity = tagRepository.findByName(tag);
//            if (entity == null) {
//                entity = new TagEntity();
//                entity.setName(tag);
//                entity = tagRepository.save(entity);
//            }
//
//            joiner.add(String.valueOf(entity.getId()));
//        }
//        return joiner.toString();
//    }
    //</editor-fold>
//    //<editor-fold defaultstate="collapsed" desc="GET ALL">
//    public List<JobModel> findAll(JobSearchDTO jobSearchDTO) {
//        List<JobModel> resultList = new ArrayList<>();
//        String company = "";
//        try {
//            company = URLDecoder.decode(jobSearchDTO.getCompany(), "UTF-8");
//        } catch (UnsupportedEncodingException ex) {
//            java.util.logging.Logger.getLogger(WebAnalyticService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        List<JobModel> jobList = jobSearchDTO.getShowAll() ? (List<JobModel>) jobRepository.findAll() : jobRepository.findAllNotExpired(new Date());
//        List<TagEntity> tagList = (List<TagEntity>) tagRepository.findAll();
//        for (JobModel entity : jobList) {
//            JsonObject object = new JsonParser().parse(entity.getCompany()).getAsJsonObject();
//            if (!company.isEmpty() && !company.equals(object.get("Name").getAsString())) {
//                continue;
//            }
//            String[] tagIdArray = entity.getTagIds().split(",");
//            entity.setTagIds(makeTagNameJoiner(tagIdArray, tagList));
//            resultList.add(entity);
//        }
//        return resultList;
//    }
//
//    private String makeTagNameJoiner(String[] tagIdArray, List<TagEntity> tagList) {
//        StringJoiner joiner = new StringJoiner(" ");
//        for (String id : tagIdArray) {
//            Optional<TagEntity> entity = tagList.stream().filter(item -> item.getId() == Integer.parseInt(id))
//                    .findFirst();
//            if (entity.isPresent()) {
//                joiner.add(entity.get().getName());
//            }
//        }
//        return joiner.toString();
//    }
//    //</editor-fold>
//    //<editor-fold defaultstate="collapsed" desc="GET JOB BY COMPANY">
//    public List<JobCompanyDTO> findJobListByCompany() {
//        List<JobCompanyDTO> resultList = new ArrayList<>();
//        List<JobModel> list = (List<JobModel>) jobRepository.findAll();
//        list.sort(Comparator.comparing(JobModel::getCompany));
//        String company = "";
//        List<JobModel> jobCompanyList = new ArrayList<>();
//        List<TagEntity> tagEntityList = (List<TagEntity>) tagRepository.findAll();
//        for (JobModel entity : list) {
//            if (!company.isEmpty() && !company.equals(entity.getCompany())) {
//                resultList.add(new JobCompanyDTO(company, makeCompanyTags(jobCompanyList, tagEntityList), jobCompanyList));
//                jobCompanyList.clear();
//            }
//            jobCompanyList.add(entity);
//            company = entity.getCompany();
//        }
//        resultList.add(new JobCompanyDTO(company, makeCompanyTags(jobCompanyList, tagEntityList), jobCompanyList));
//        return resultList;
//    }
//
//    private String makeCompanyTags(List<JobModel> jobCompanyList, List<TagEntity> tagEntityList) {
//        Set<String> tagSet = new HashSet<>();
//        for (JobModel job : jobCompanyList) {
//            tagSet.addAll(new HashSet<>(Arrays.asList(job.getTagIds().split(","))));
//        }
//        return makeTagNameJoiner(tagSet.toArray(new String[tagSet.size()]), tagEntityList);
//    }
//    //</editor-fold>
//    //<editor-fold defaultstate="collapsed" desc="ANALYTICS DETAIL URLS">
//    public void analyticsDetail(int numItem) {
//        List<QueryCheckerEntity> queryCheckerITViecList = queryCheckerRepository.findActiveList(1);
//        List<QueryCheckerEntity> queryCheckerVNWorkList = queryCheckerRepository.findActiveList(2);
//
//        List<JobModel> list = jobRepository.findJobNotAnalyticsDetail(new PageRequest(0, numItem));
//        for (JobModel job : list) {
//            try {
//                JobModel newEntity;
//                if (job.getLink().contains("itviec")) {
//                    newEntity = analyticsDetailJob(job, queryCheckerITViecList);
//                } else {
//                    newEntity = analyticsDetailJob(job, queryCheckerVNWorkList);
//                }
//                jobRepository.save(newEntity);
//            } catch (UrlException ex) {
//                log.error("Error when analytics data detail of link " + job.getLink(), ex);
//            }
//        }
//    }
//
//    private JobModel analyticsDetailJob(JobModel job, List<QueryCheckerEntity> queryCheckerList) throws UrlException {
//        Map<String, String> selectorMap = queryCheckerList.stream().collect(
//                Collectors.toMap(QueryCheckerEntity::getQueryType, QueryCheckerEntity::getQueryValue));
//        return urlUtils.analyticsDetailData(selectorMap, job);
//    }
//    //</editor-fold>
}
