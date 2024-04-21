package com.shark.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shark.shortlink.project.dao.entity.ShortLinkDO;
import com.shark.shortlink.project.dto.biz.ShortLinkStatsRecordDTO;
import com.shark.shortlink.project.dto.req.ShortLinkBatchCreateReqDTO;
import com.shark.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.shark.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.shark.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkBatchCreateRespDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

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
     * 查询短链接分组内数量
     * @param requestParam 查询短链接分组内数量请求参数
     * @return 查询短链接分组内数量响应
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> requestParam);

    /**
     * 更新短链接信息
     * @param shortLinkUpdateReqDTO
     */
    void updateShortLink(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO);

    /**
     * 短链接跳转
     * @param shortUri 短链接后缀
     * @param request
     * @param response
     */
    void restoreUrl(String shortUri, ServletRequest request, ServletResponse response);

    /**
     * 批量创建短链接
     * @param requestParam 请求参数
     * @return
     */
    ShortLinkBatchCreateRespDTO batchCreateShortLink(ShortLinkBatchCreateReqDTO requestParam);


    /**
     * 短链接统计
     * @param fullShortUrl         完整短链接
     * @param gid                  分组标识
     * @param shortLinkStatsRecord 短链接统计实体参数
     */
    void shortLinkStats(String fullShortUrl, String gid, ShortLinkStatsRecordDTO shortLinkStatsRecord);
}
