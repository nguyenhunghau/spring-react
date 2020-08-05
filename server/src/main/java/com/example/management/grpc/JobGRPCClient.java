package com.example.management.grpc;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.example.management.entity.JobEntity;
import com.server.service.JobGrpc;
import com.server.service.JobResult;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
//</editor-fold>

/**
 *
 * @author Nguyen Hung Hau
 */
@Slf4j
@Service
public class JobGRPCClient {

    private final JobGrpc.JobBlockingStub blockingStub;
    private static final String SERVER_LINK = "localhost:50052";

    /**
     * Construct client for accessing HelloWorld server using the existing
     * channel.
     */
    public JobGRPCClient() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(SERVER_LINK).usePlaintext()
                .build();
        blockingStub = JobGrpc.newBlockingStub(channel);
    }

    public List<JobEntity> getDataListITViec(JobEntity entity) {
        com.server.service.JobEntity jobEntity = com.server.service.JobEntity.newBuilder().setTitle(entity.getTitle()).setLink(entity.getLink()).build();
        com.server.service.JobRequest jobRequest = com.server.service.JobRequest.newBuilder().setJobEntity(jobEntity).build();
        return convertData(blockingStub.getJobList(jobRequest).getJobEntityList());
    }
    
    private List<JobEntity> convertData(List<com.server.service.JobEntity> jobEntityList) {
         List<JobEntity> resultList = new ArrayList<>();
         for(com.server.service.JobEntity model: jobEntityList) {
             resultList.add(new JobEntity(0, model.getTitle(), model.getCompany(), "", new Date(), null, null, model.getLink(), null, null, new Date()));
         }
         return resultList;
    }
}
