package com.xz.lwjk.event.facade.common.dao;

import com.google.common.collect.Lists;
import org.omg.CORBA.portable.ValueOutputStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.tree.VoidDescriptor;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * @author : hr
 * @Date: 2018/8/8 15:05
 * @Description:
 */
@NoRepositoryBean
public interface IBaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T>,PagingAndSortingRepository<T,ID> {

}
