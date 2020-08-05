package com.server.grpc.server;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.server.grpc.service.WebAnalyticService;
import com.server.service.JobGrpc;
import com.server.service.JobRequest;
import com.server.service.JobResult;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
//</editor-fold>

/**
 *
 * @author HungHau
 */
@Slf4j
public class JobServer {
    
    private final static int PORT = 50052;
    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        server = ServerBuilder.forPort(PORT)
                .addService(new JobImpl())
                .build()
                .start();
        log.info("Server started, listening on " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    JobServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * JobServer launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final JobServer server = new JobServer();
        server.start();
        server.blockUntilShutdown();
    }
    
    private class JobImpl extends JobGrpc.JobImplBase {

        @Override
        public void getJobList(JobRequest req, StreamObserver<JobResult> responseObserver) {
            WebAnalyticService webAnalyticService = new WebAnalyticService();
            JobResult result = JobResult.newBuilder().addAllJobEntity(webAnalyticService.analyticsItViec(req.getJobEntity())).build();
            responseObserver.onNext(result);
            responseObserver.onCompleted();
        }
    }
}
