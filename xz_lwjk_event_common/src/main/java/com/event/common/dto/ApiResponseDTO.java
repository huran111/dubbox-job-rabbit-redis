package com.event.common.dto;

import lombok.Data;

/**
 * @author
 * @Date: 2018/8/10 15:48
 * @Description: 通用dto
 */
@Data
public class ApiResponseDTO {
    private String code;
    private String msg;
    private String busCode;
    private Object data;

    public ApiResponseDTO() {
    }

    public ApiResponseDTO(Object object) {
        this.code = "1";
        this.busCode = "1";
        this.msg = "调用成功";
        this.data = object;
    }
}
