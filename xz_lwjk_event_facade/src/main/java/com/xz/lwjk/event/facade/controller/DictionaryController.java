package com.xz.lwjk.event.facade.controller;

import com.event.common.entity.SysDictionary;

import com.xz.lwjk.event.facade.repository.DictionaryRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class DictionaryController {
    @Autowired
    DictionaryRepository dictionaryRepository;


    @ApiOperation(value = "获取字典信息", notes = "根据Id获取字典详细信息")
    @ApiImplicitParam(name = "id", value = "字典ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "dictionary/{id}", method = RequestMethod.GET)
    public SysDictionary getUserById(@PathVariable(value = "id") String id) {
        SysDictionary dictionary = dictionaryRepository.findById(id);
        return dictionary;
    }

    @ApiOperation(value = "保存字典信息", notes = "保存字典信息")
    @ApiImplicitParam(dataType = "Dictionary", name = "dictionary", value = "字典信息", required = true)
    @RequestMapping(value = "/saveDictionary", method = RequestMethod.POST)
    public String saveDictionary(@RequestBody SysDictionary dictionary) {
        dictionaryRepository.save(dictionary);
        return "success";
    }

    @ApiOperation(value = "删除字典信息", notes = "删除字典信息")
    @ApiImplicitParam(dataType = "Long", name = "id", value = "字典id", required = true, paramType = "path")
    @RequestMapping(value = "deleteDictionary/{id}", method = RequestMethod.GET)
    public void deleteDictionary(@PathVariable(value = "id") Long id) {
    }

}
