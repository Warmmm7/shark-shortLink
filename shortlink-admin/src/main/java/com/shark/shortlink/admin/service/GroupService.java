package com.shark.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shark.shortlink.admin.dao.entity.GroupDO;
import com.shark.shortlink.admin.dto.req.GroupSortReqDTO;
import com.shark.shortlink.admin.dto.req.GroupUpdateReqDTO;
import com.shark.shortlink.admin.dto.resp.GroupRespDTO;

import java.util.List;

public interface GroupService extends IService<GroupDO> {


    /**
     * 新增短链接分组
     * @param groupName 短链接分组名
     */
    void saveGroup(String groupName);

    /**
     * 新增短链接分组
     * @param username  用户名
     * @param groupName 短链接分组名
     */
    void saveGroup(String username, String groupName);

    /**
     * 查询用户短连接分组集合
     * @return
     */
    List<GroupRespDTO> listGroup();

    /**
     * 修改短连接分组
     * @param groupUpdateReqDTO
     */
    void update(GroupUpdateReqDTO groupUpdateReqDTO);

    /**
     * 删除短连接分组
     * @param gid
     */
    void detele(String gid);

    /**
     * 短连接分组排序
     * @param groupSortReqDTO
     */
    void sortGroup(List<GroupSortReqDTO> groupSortReqDTO);
}
