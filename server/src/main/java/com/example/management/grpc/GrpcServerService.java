///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.example.management.grpc;
//
//import com.mycompany.grpc.server.GreeterGrpc;
//import com.mycompany.grpc.server.HelloReply;
//import com.mycompany.grpc.server.HelloRequest;
//import io.grpc.stub.StreamObserver;
//
///**
// *
// * @author USER
// */
//public class GrpcServerService extends GreeterGrpc.GreeterImplBase{
//    @Override
//    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
//        HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName()).build();
//        responseObserver.onNext(reply);
//        responseObserver.onCompleted();
//    }
//}
