package com.shark.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 短链接分组创建参数
 */
@Data
public class GroupSaveReqDTO {

    /**
     * 分组名
     */
    private String name;
}
