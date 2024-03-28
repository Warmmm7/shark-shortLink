package com.shark.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shark.shortlink.project.dao.entity.ShortLinkDO;
import com.shark.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkCreateRespDTO;

/**
 * 短连接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {
    /**
     * 创建短连接
     * @param shortLinkCreateReqDTO
     * @return
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO);
}
