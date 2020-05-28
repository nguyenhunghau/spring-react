/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.management.service;
import com.example.management.service.GreeterGrpc.GreeterBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 *
 * @author USER
 */
@Component
public class GrpcClient {

//    @Autowired
    private GreeterBlockingStub simpleStub;
//    private GreeterBlockingStub makeChannel() {
//        String user = "world";
//        String target = "localhost:50051";
//
//        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
//                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
//                // needing certificates.
//                .usePlaintext()
//                .build();
//        return GreeterGrpc.newBlockingStub(channel);
//    }

    @PostConstruct
    public void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 50052).usePlaintext().build();

        simpleStub
                = GreeterGrpc.newBlockingStub(managedChannel);
    }

    public String sendMessage(final String name) {
        try {
//            GreeterBlockingStub simpleStub = makeChannel();
            final HelloReply response = simpleStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }
}
