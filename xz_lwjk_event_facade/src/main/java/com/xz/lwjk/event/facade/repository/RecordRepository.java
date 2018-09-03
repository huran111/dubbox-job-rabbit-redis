package com.xz.lwjk.event.facade.repository;

import com.event.common.entity.Record;
import com.xz.lwjk.event.facade.common.dao.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author
 * @Date: 2018/8/11 12:59
 * @Description:
 */
@Repository
public interface RecordRepository extends IBaseRepository<Record, String> {
    Record findById(String id);

    void deleteById(String id);
}
