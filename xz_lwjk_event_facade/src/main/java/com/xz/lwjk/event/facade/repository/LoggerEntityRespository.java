package com.xz.lwjk.event.facade.repository;

import com.event.common.entity.LoggerEntity;
import com.xz.lwjk.event.facade.common.dao.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author huran
 * @Date: 2018/8/10 09:23
 * @Description:
 */
@Repository
public interface LoggerEntityRespository extends IBaseRepository<LoggerEntity,String>{

}
