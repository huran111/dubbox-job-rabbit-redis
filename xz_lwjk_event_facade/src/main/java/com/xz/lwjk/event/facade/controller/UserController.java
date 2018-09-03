package com.xz.lwjk.event.facade.controller;

import com.event.common.annotation.DubboType;
import com.event.common.dto.ApiResponseDTO;
import com.event.common.entity.User;
import com.xz.lwjk.event.facade.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 *
 */

@RestController
@Transactional(rollbackFor = Exception.class)
public class UserController {
    @Autowired
    IUserService userService;
    @ApiOperation(value = "获取用户信息", notes = "根据Id获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable(value = "id") String id) {
        return userService.findById(id);
    }

    @ApiOperation(value = "保存用户信息", notes = "保存用户信息")
    @ApiImplicitParam(dataType = "User", name = "user", value = "用户信息", required = true)
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    @DubboType(value = "dubbo")
    public ApiResponseDTO saveUser(@RequestBody User user) {
        userService.save(user);
        return new ApiResponseDTO(user);
    }

    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")
    @ApiImplicitParam(dataType = "Long", name = "id", value = "用户id", required = true, paramType = "path")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    @DubboType(value = "rest")
    public void deleteUser(@PathVariable(value = "id") String id) {

        userService.deleteById(id);

    }


}
