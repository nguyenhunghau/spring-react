package com.example.management.controller;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.example.management.component.EmailUtils;
import com.example.management.dto.JobCompanyDTO;
import com.example.management.entity.*;
import com.example.management.service.WebAnalyticService;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//</editor-fold>

/**
 *
 * @author USER
 */
@RestController
public class JobController {

    @Autowired
    private WebAnalyticService webAnalyticService;

    @Autowired
    private EmailUtils emailUtils;

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @RequestMapping(value = "/getListJob", method = RequestMethod.GET)
    public ResponseEntity<List<JobEntity>> index() {
        return new ResponseEntity<>(webAnalyticService.analytics(), HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<JobEntity>> list() {
        return new ResponseEntity<>(webAnalyticService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getListTag", method = RequestMethod.GET)
    public ResponseEntity<List<TagEntity>> getListTag() {
        return new ResponseEntity<>(webAnalyticService.getListTag(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/getListCompanyJob", method = RequestMethod.GET)
    public ResponseEntity<List<JobCompanyDTO>> getListCompanyJob() {
        return new ResponseEntity<>(webAnalyticService.findJobListByCompany(), HttpStatus.OK);
    }

//    @RequestMapping(value = "/send-mail", method = RequestMethod.GET)
//    public ResponseEntity<String> test() {
//        emailUtils.sendMail(
//                "Test Send Email",
//                "Hello SendGrid",
//                Collections.singletonList("nguyenhunghau.us@gmail.com"),
//                null,
//                null
//        );
//        return new ResponseEntity<>("abc", HttpStatus.OK);
//    }
}
