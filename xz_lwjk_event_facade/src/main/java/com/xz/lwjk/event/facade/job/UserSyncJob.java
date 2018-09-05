package com.xz.lwjk.event.facade.job;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author huran
 * @Date: 2018/8/14 15:55
 * @Description: 定时查看用户信息job
 */
@Component
@Slf4j
public class UserSyncJob extends AbstractSimpleElasticJob{


    @Override
    public void process(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {

       log.info("用户信息为：[{}]");
    }
}
