package com.xz.lwjk.event.facade.controller;

import com.xz.lwjk.event.facade.entity.User;
import com.xz.lwjk.event.facade.entity.Weibo;
import com.xz.lwjk.event.facade.repository.UserRepository;
import com.xz.lwjk.event.facade.repository.WeiboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : huran
 * @Date: 2018/9/5 10:41
 * @Description:
 */
@Controller
public class TestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeiboRepository weiboRepository;
    @RequestMapping("/searchUser/{username}")
    public @ResponseBody
    List<User> searchUser(@PathVariable("username") String username) {
        List<User> result = this.userRepository.findByUsernameContaining(username);
        return result;
    }
    @RequestMapping("/username/{username}")
    @ResponseBody
    public List<Weibo> getUserWeibo(@PathVariable("username") String username) {
        return this.weiboRepository.searchUserWeibo(username);
    }
    @RequestMapping("/usernameSort/{username}")
    @ResponseBody
    public List<Weibo> getUserWeiboSort(@PathVariable("username") String username) {
        return this.weiboRepository.searchUserWeibo(username,new Sort(new Sort.Order(Sort.Direction.DESC,"weiboId")));
    }
    @RequestMapping("/simpleSearch")
    public Page<Weibo> simpleSearch(String username, String weiboText, int pageNo, int pageSize){
        User user = this.userRepository.getByUsernameIs(username);
        return this.weiboRepository.findByUserIsAndWeiboTextContaining(user,weiboText,new PageRequest(pageNo,pageSize));
    }
    @RequestMapping("/searchWeibo")
    public Page<Weibo> searchWeibo(final String username, final String weiboText, final Date startDate, final Date endDate, int pageNo, int pageSize) {
        Page<Weibo> page = this.weiboRepository.findAll(new Specification<Weibo>() {
            @Override
            public Predicate toPredicate(Root<Weibo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new LinkedList<>();

                if (!StringUtils.isEmpty(username)) {
                    //Join有两种方式
//                    Join<Weibo,User> userJoin = root.join("user",JoinType.INNER);
//                    predicates.add(criteriaBuilder.equal(userJoin.get("username"), username));
                    predicates.add(criteriaBuilder.equal(root.get("user").get("username"),username));
                }
                if (!StringUtils.isEmpty(weiboText)) {
                    predicates.add(criteriaBuilder.like(root.get("weiboText"), "%" + weiboText + "%"));
                }
                if(startDate!=null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createDate").as(Date.class),startDate));
                }
                if(endDate != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createDate").as(Date.class),endDate));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },new PageRequest(pageNo,pageSize));
        return page;
    }
}
