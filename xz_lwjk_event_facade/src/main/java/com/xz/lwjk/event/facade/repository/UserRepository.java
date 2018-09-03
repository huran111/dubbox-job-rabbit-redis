package com.xz.lwjk.event.facade.repository;

import com.event.common.entity.User;
import com.xz.lwjk.event.facade.common.dao.IBaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author
 * @Date: 2018/8/8 15:38
 * @Description:
 */
@Repository
public interface UserRepository extends IBaseRepository<User, String> {
        User findById(String id);
        void deleteById(String id);
}
