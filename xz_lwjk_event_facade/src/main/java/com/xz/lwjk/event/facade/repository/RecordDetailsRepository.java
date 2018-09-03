package com.xz.lwjk.event.facade.repository;

import com.event.common.entity.RecordDetails;
import com.xz.lwjk.event.facade.common.dao.IBaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @Date: 2018/8/11 13:00
 * @Description:
 */
@Repository
public interface RecordDetailsRepository extends IBaseRepository<RecordDetails, String> {
    RecordDetails findById(String id);
}
