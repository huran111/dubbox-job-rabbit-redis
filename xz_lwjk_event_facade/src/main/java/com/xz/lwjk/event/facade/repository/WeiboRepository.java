package com.xz.lwjk.event.facade.repository;

import com.xz.lwjk.event.facade.entity.User;
import com.xz.lwjk.event.facade.entity.Weibo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author huran
 * @Date: 2018/9/5 11:05
 * @Description:
 */
public interface WeiboRepository extends JpaRepository<Weibo, Long>, JpaSpecificationExecutor<Weibo> {
    @Query("select w from  Weibo  w where w.user.username=:username")
    List<Weibo> searchUserWeibo(@Param("username") String username);

    @Query("select  w from Weibo  w where  w.user.username=:username")
    List<Weibo> searchUserWeibo(@Param("username") String username, Sort sort);

    @Modifying
    @Transactional(readOnly = false)
    @Query("update  Weibo  w set w.weiboText=:text where w.user=:user")
    int setUserWeiboContent(@Param("text") String weiboText, @Param("user") User user);

    Page<Weibo> findByUserIsAndWeiboTextContaining(User user, String weiboText, Pageable pageable);

    @Transactional(readOnly = false)
    int deleteByUser(User user);

}
