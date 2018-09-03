package com.xz.lwjk.event.facade.common.service.impl;

import com.event.common.entity.BaseEntity;
import com.xz.lwjk.event.facade.common.dao.IBaseRepository;
import com.xz.lwjk.event.facade.common.service.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @auther: huran
 * @Date: 2018/8/8 17:38
 * @Description:
 */
public class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> implements IBaseService<T, ID> {


}
