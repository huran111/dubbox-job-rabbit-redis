package com.xz.lwjk.event.facade.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.xz.lwjk.event.facade.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author
 * @Date: 2018/8/14 16:34
 * @Description:
 */
@Component
@Slf4j
public class SyncDirectory extends AbstractSimpleElasticJob {

    @Override
    public void process(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {
        log.info("数据同步开始..................................");
    }
}
