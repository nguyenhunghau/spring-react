///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.management;
//
//import com.mycompany.grpc.server.GreeterGrpc;
//import io.grpc.Channel;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import io.grpc.netty.NettyChannelBuilder;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import java.util.concurrent.ForkJoinPool;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// *
// * @author USER
// */
//@Configuration
//public class GRPCConfig implements DisposableBean{
//    private ManagedChannel channel;
//    
//    @Bean
//    public void initGRPCClient() {
//        if (channel == null) {
//            int coreProcess = Runtime.getRuntime().availableProcessors();//Get num of core process of OS
//            EventLoopGroup bossEventGroup = new NioEventLoopGroup(coreProcess * 2);// should be = core process for best performance
//
//            //Create GRPC client to host
//            channel = NettyChannelBuilder.forAddress("localhost", 50051)
//                    .usePlaintext()//get response as plain text
//                    .enableRetry()//enable retry when lost connection
//                    .maxRetryAttempts(5)//attempt to connect 5 times before throw error
//                    .maxInboundMessageSize(Integer.MAX_VALUE)//
//                    .eventLoopGroup(bossEventGroup)// thread for execute request
//                    .enableFullStreamDecompression()//enable compression stream in client and server
//                    .executor(new ForkJoinPool(coreProcess * 4,
//                            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
//                            (Thread t, Throwable e) -> {
//                                t.interrupt();
//                            }, true))
//                    .build();
//            //</editor-fold>
//        }
//    }
//    
//    @Bean
//    public GreeterGrpc.GreeterBlockingStub googleBusinessServiceBlockingStub() {
//        return GreeterGrpc.newBlockingStub(channel);
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        if (channel != null) {
//            channel.shutdown();
//            channel = null;
//        }
//    }
//}
