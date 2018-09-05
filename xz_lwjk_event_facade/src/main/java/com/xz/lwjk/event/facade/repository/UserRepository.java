package com.xz.lwjk.event.facade.repository;

import com.xz.lwjk.event.facade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * @author huran
 * @Date: 2018/9/5 10:39
 * @Description:
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{


    /**
     * 查询用户名称包含username字符串的用户对象
     * @param username
     * @return
     */
    List<User> findByUsernameContaining(String username);



    /**
     * 获得单个用户对象，根据username和pwd的字段匹配
     * @param username
     * @param pwd
     * @return
     */
    User getByUsernameIsAndUserpwdIs(String username,String pwd);

    /**
     * 精确匹配username的用户对象
     * @param username
     * @return
     */
    User getByUsernameIs(String username);
}
