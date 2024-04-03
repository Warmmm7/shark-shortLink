package com.shark.shortlink.admin.remote;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.dto.req.RecycleBinPageReqDTO;
import com.shark.shortlink.admin.dto.req.RecycleBinRecoverReqDTO;
import com.shark.shortlink.admin.dto.req.RecycleBinSaveReqDTO;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 短链接中台远程调用服务
 */
public interface ShortLinkRemoteService {

    /**
     * 创建短链接
     * @param shortLinkCreateReqDTO
     * @return 短链接创建响应
     */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        String resultBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/create", JSON.toJSONString(shortLinkCreateReqDTO));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }

    /**
     * 分页查询短链接
     * @param shortLinkPageReqDTO
     * @return 查询短链接响应
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", shortLinkPageReqDTO.getGid());
        requestMap.put("current", shortLinkPageReqDTO.getCurrent());
        requestMap.put("size", shortLinkPageReqDTO.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /**
     * 修改短链接
     * @param shortLinkUpdateReqDTO 修改短链接请求参数
     */
    default void updateShortLink(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/update", JSON.toJSONString(shortLinkUpdateReqDTO));
    }

    /**
     * 根据 URL 获取标题
     * @param url 目标网站地址
     * @return 网站标题
     */
    default Result<String> getTitleByUrl(@RequestParam("url") String url) {
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/short-link/v1/title?url=" + url);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }


    /**
     * 保存回收站
     * @param  recycleBinSaveReqDTO 回收站参数
     */
    default void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/save", JSON.toJSONString(recycleBinSaveReqDTO));
    }


    /**
     * 分页查询回收站短链接
     * @param recycleBinPageReqDTO 分页短链接请求参数
     * @return 查询短链接响应
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(RecycleBinPageReqDTO recycleBinPageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gidList", recycleBinPageReqDTO.getGidList());
        requestMap.put("current", recycleBinPageReqDTO.getCurrent());
        requestMap.put("size", recycleBinPageReqDTO.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/page", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /**
     * 回收站恢复短链接功能
     * @param recycleBinRecoverReqDTO 请求参数
     */
    default void recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/recover", JSON.toJSONString(recycleBinRecoverReqDTO));
    }

}