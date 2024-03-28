package com.shark.shortlink.admin.dto.req;


import lombok.Data;

/**
 * 短连接分组排序参数
 */
@Data
public class GroupSortReqDTO {
    /**
     * 分组ID
     */
    private String gid;

    /**
     * 排序
     */
    private Integer sortOrder;
}
