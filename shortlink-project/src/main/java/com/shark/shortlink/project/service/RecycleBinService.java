package com.shark.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shark.shortlink.project.dao.entity.ShortLinkDO;
import com.shark.shortlink.project.dto.req.RecycleBinPageReqDTO;
import com.shark.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.shark.shortlink.project.dto.req.RecycleBinRemoveReqDTO;
import com.shark.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 * 回收站管理接口层
 */
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存回收站
     * @param recycleBinSaveReqDTO 请求参数
     */
    void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO);

    /**
     * 分页查询回收站短链接
     * @param recycleBinPageReqDTO 分页查询短链接请求参数
     * @return 短链接分页返回结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(RecycleBinPageReqDTO recycleBinPageReqDTO);

    /**
     * 恢复回收站短链接
     * @param recycleBinRecoverReqDTO 请求参数
     */
    void recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO);

    /**
     * 从回收站移除短链接
     * @param requestParam 移除短链接请求参数
     */
    void removeRecycleBin(RecycleBinRemoveReqDTO requestParam);
}