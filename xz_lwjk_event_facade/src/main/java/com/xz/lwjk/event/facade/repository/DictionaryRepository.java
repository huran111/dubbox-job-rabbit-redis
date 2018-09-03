package com.xz.lwjk.event.facade.repository;

import com.event.common.entity.SysDictionary;
import com.xz.lwjk.event.facade.common.dao.IBaseRepository;

import org.springframework.stereotype.Repository;

/**
 * @auther: huran
 * @Date: 2018/8/9 11:17
 * @Description:
 */
@Repository
public interface DictionaryRepository extends IBaseRepository<SysDictionary, String> {
    /**
     *
     * @param id z主键
     * @return
     */
    SysDictionary findById(String id);
}
