package com.xz.lwjk.event.facade.common.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @auther: hr
 * @Date: 2018/8/8 20:06
 * @Description:
 */
public class PageableTools {
    /**
     * 获取基础分页对象
     * @param page 获取第几页
     * @param size 每页条数
     * @param dtos 排序对象数组
     * @return
     */
    public static Pageable basicPage(Integer page, Integer size, SortDto... dtos) {
        Sort sort = SortTools.basicSort(dtos);
        page = (page==null || page<0)?0:page;
        size = (size==null || size<=0)?15:size;
        Pageable pageable = new PageRequest(page, size, sort);
        return pageable;
    }

    /**
     * 获取基础分页对象，每页条数默认15条
     *  - 默认以id降序排序
     * @param page 获取第几页
     * @return
     */
    public static Pageable basicPage(Integer page) {
        return basicPage(page, 0, new SortDto("desc", "id"));
    }

    /**
     * 获取基础分页对象，每页条数默认15条
     * @param page 获取第几页
     * @param dtos 排序对象数组
     * @return
     */
    public static Pageable basicPage(Integer page, SortDto... dtos) {
        return basicPage(page, 0, dtos);
    }

    /**
     * 获取基础分页对象，排序方式默认降序
     * @param page 获取第几页
     * @param size 每页条数
     * @param orderField 排序字段
     * @return
     */
    public static Pageable basicPage(Integer page, Integer size, String orderField) {
        return basicPage(page, size, new SortDto("desc", orderField));
    }

    /**
     * 获取基础分页对象
     *  - 每页条数默认15条
     *  - 排序方式默认降序
     * @param page 获取第几页
     * @param orderField 排序字段
     * @return
     */
    public static Pageable basicPage(Integer page, String orderField) {
        return basicPage(page, 0, new SortDto("desc", orderField));
    }

}
