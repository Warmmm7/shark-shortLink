package com.shark.shortlink.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shark.shortlink.admin.common.biz.user.UserContext;
import com.shark.shortlink.admin.common.convention.exception.ServiceException;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.dao.entity.GroupDO;
import com.shark.shortlink.admin.dao.mapper.GroupMapper;
import com.shark.shortlink.admin.remote.dto.req.RecycleBinPageReqDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.shark.shortlink.admin.remote.service.ShortLinkActualRemoteService;
import com.shark.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * URL 回收站接口实现层
 */
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl implements RecycleBinService {
    private final GroupMapper groupMapper;
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;
    /**
     * SpringCloud Feign 调用
     */

    @Override
    public Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(RecycleBinPageReqDTO recycleBinPageReqDTO) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0);
        List<GroupDO> groupDOList = groupMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(groupDOList)) {
            throw new ServiceException("用户无分组信息");
        }
        recycleBinPageReqDTO.setGidList(groupDOList.stream().map(GroupDO::getGid).toList());
        return shortLinkActualRemoteService.pageRecycleBinShortLink(recycleBinPageReqDTO.getGidList(), recycleBinPageReqDTO.getCurrent(), recycleBinPageReqDTO.getSize());
    }
}
