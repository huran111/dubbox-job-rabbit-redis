package com.xz.lwjk.event.facade.dao;

import com.alibaba.dubbo.config.annotation.Service;
import com.xz.lwjk.event.facade.entity.User;
import com.xz.lwjk.event.facade.entity.Weibo;
import com.xz.lwjk.event.facade.repository.WeiboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author BUCHU
 * @Date: 2018/9/5 18:21
 * @Description:
 */
@Service
public class WeiboService {
    @Autowired
    private WeiboRepository weiboRepository;

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public List<Weibo> importWeiboList(List<Weibo> weibos, User user) {
        int index = 0;
        Date nowDateTime = new Date(System.currentTimeMillis());
        for (Weibo weiboItem : weibos) {
            weiboItem.setUser(user);
            weiboItem.setCreateDate(nowDateTime);
            if (5 <= index++) {
                throw new RuntimeException("Weibo out of limit!!!");
            }
            this.weiboRepository.save(weiboItem);
        }
        return weibos;
    }
}
