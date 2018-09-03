package com.xz.lwjk.event.facade.service;

import com.event.common.entity.User;
import com.xz.lwjk.event.facade.common.dao.IBaseRepository;
import com.xz.lwjk.event.facade.common.service.IBaseService;

import java.util.List;

public interface IUserService extends IBaseService<User, String> {
    void save(User user);

    User findOneByName(final String name);

    void deleteById(String id);

    User findById(String id);
    List<User> findAll();
}
