package com.shark.shortlink.admin.remote.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.remote.dto.req.*;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 短链接中台远程调用服务
 */
public interface ShortLinkRemoteService {

    /**
     * 创建短链接
     * @param shortLinkCreateReqDTO
     * @return 短链接创建响应
     */
    Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO);

    /**
     * 分页查询短链接
     * @param shortLinkPageReqDTO
     * @return 查询短链接响应
     */
    Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);

    /**
     * 修改短链接
     * @param shortLinkUpdateReqDTO 修改短链接请求参数
     */
    void updateShortLink(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO);

    /**
     * 根据 URL 获取标题
     * @param url 目标网站地址
     * @return 网站标题
     */
    Result<String> getTitleByUrl(@RequestParam("url") String url);


    /**
     * 保存回收站
     * @param  recycleBinSaveReqDTO 回收站参数
     */
    void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO);


    /**
     * 分页查询回收站短链接
     * @param recycleBinPageReqDTO 分页短链接请求参数
     * @return 查询短链接响应
     */
    Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(RecycleBinPageReqDTO recycleBinPageReqDTO) ;

    /**
     * 回收站恢复短链接功能
     * @param recycleBinRecoverReqDTO 请求参数
     */
    void recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO);

}