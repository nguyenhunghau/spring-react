///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.management;
//
//import lombok.Data;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
//import org.springframework.web.filter.CommonsRequestLoggingFilter;
//
///**
// *
// * @author USER
// */
//@Data
//public class ApiConfig {
//    @Bean
//    public ProtobufJsonFormatHttpMessageConverter protobufHttpMessageConverter() {
//        return new ProtobufJsonFormatHttpMessageConverter();
//    }
//
//    @Bean
//    public CommonsRequestLoggingFilter requestLoggingFilter() {
//        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
//        filter.setIncludeQueryString(true);
//        filter.setIncludePayload(true);
//        filter.setIncludeHeaders(true);
//        filter.setIncludeClientInfo(true);
//        return filter;
//    }
//}
