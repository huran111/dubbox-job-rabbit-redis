package com.xz.lwjk.event.facade.service.impl;

import com.event.common.entity.User;
import com.xz.lwjk.event.facade.common.service.impl.BaseServiceImpl;
import com.xz.lwjk.event.facade.repository.UserRepository;
import com.xz.lwjk.event.facade.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@Service(value = "userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<User, String> implements IUserService {


    @Autowired
    UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


    @Override
    public User findOneByName(final String name) {
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate username = criteriaBuilder.equal(root.get("username"), name);
                return criteriaBuilder.and(username);
            }
        };
        return null;
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(String id) {
        User user = userRepository.findById(id);

        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> list = userRepository.findAll();
        return list;
    }
}
