package com.xz.lwjk.event.facade.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.event.common.entity.User;
import com.xz.lwjk.event.facade.common.service.IBaseService;
import com.xz.lwjk.event.facade.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huran
 * @Date: 2018/8/14 15:55
 * @Description: 定时查看用户信息job
 */
@Component
@Slf4j
public class UserSyncJob extends AbstractSimpleElasticJob{
    @Autowired
    IUserService userService;

    @Override
    public void process(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {
      List<User> list=  userService.findAll();
       log.info("用户信息为：[{}]",list.toString());
    }
}
