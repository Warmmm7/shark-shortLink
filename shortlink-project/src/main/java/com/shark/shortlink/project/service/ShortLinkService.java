package com.shark.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shark.shortlink.project.dao.entity.ShortLinkDO;
import com.shark.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.shark.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.shark.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkPageRespDTO;

import java.util.List;

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

    /**
     * 分页查询短链接
     * @param shortLinkPageReqDTO
     * @return
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);

    /**
     * 查询短链接分组内的链接数量
     * @param gids 分组标识
     * @return
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> gids);

    /**
     * 更新短链接信息
     * @param shortLinkUpdateReqDTO
     */
    void updateShortLink(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO);
}
