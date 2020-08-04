package com.example.management.controller;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.example.management.dto.JobSearchDTO;
import com.example.management.entity.*;
import com.example.management.service.WebAnalyticService;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//</editor-fold>

/**
 *
 * @author Nguyen Hung Hau
 */
@RestController
@Slf4j
public class JobController {

    @Autowired
    private WebAnalyticService webAnalyticService;

    @RequestMapping(value = "/getListJob", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Integer>> index() {
        return new ResponseEntity<>(webAnalyticService.analytics(), HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<List<JobEntity>> list(@RequestBody JobSearchDTO jobSearchDTO) {
        return new ResponseEntity<>(webAnalyticService.findAll(jobSearchDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/getListTag", method = RequestMethod.GET)
    public ResponseEntity<List<TagEntity>> getListTag() {
        return new ResponseEntity<>(webAnalyticService.getListTag(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/getListDetail", method = RequestMethod.GET)
    public ResponseEntity<Object> getListDetail() {
        webAnalyticService.analyticsDetail(10);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/getListCompanyJob", method = RequestMethod.GET)
    public ResponseEntity<String> getListCompanyJob() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    String dtoAsString = objectMapper.writeValueAsString(webAnalyticService.findJobListByCompany());
        return new ResponseEntity<>(dtoAsString, HttpStatus.OK);
    }
}
