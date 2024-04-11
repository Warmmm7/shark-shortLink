package com.shark.shortlink.admin.remote.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.remote.dto.req.*;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkStatsRespDTO;
import com.shark.shortlink.admin.remote.service.ShortLinkRemoteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShortLinkRemoteServiceImpl implements ShortLinkRemoteService {
    @Value("${short-link.remote.createUrl.default}")
    private String remoteCreateShortLinkDefaultUrl;
    @Value("${short-link.remote.pageUrl.default}")
    private String remotePageShortLinkDefaultUrl;
    @Value("${short-link.remote.updateUrl.default}")
    private String remoteUpdateShortLinkDefaultUrl;
    @Value("${short-link.remote.getTitleUrl.default}")
    private String remoteGetTitleShortLinkDefaultUrl;
    @Value("${short-link.remote.recycleSaveUrl.default}")
    private String remoteRecycleSaveShortLinkDefaultUrl;
    @Value("${short-link.remote.recyclePageUrl.default}")
    private String remoteRecyclePageShortLinkDefaultUrl;
    @Value("${short-link.remote.recycleRecoverUrl.default}")
    private String remoteRecycleRecoverShortLinkDefaultUrl;
    @Value("${short-link.remote.statsUrl.default}")
    private String remoteStatsShortLinkDefaultUrl;


    /**
     * 创建短链接
     * @param shortLinkCreateReqDTO
     * @return 短链接创建响应
     */
    @Override
    public Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        String resultBodyStr = HttpUtil.post(remoteCreateShortLinkDefaultUrl, JSON.toJSONString(shortLinkCreateReqDTO));
        return JSON.parseObject(resultBodyStr, new TypeReference<Result<ShortLinkCreateRespDTO>>() {});
    }

    /**
     * 分页查询短链接
     * @param shortLinkPageReqDTO
     * @return 查询短链接响应
     */
    @Override
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", shortLinkPageReqDTO.getGid());
        requestMap.put("current", shortLinkPageReqDTO.getCurrent());
        requestMap.put("size", shortLinkPageReqDTO.getSize());
        String resultPageStr = HttpUtil.get(remotePageShortLinkDefaultUrl, requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /**
     * 修改短链接
     * @param shortLinkUpdateReqDTO 修改短链接请求参数
     */
    @Override
    public void updateShortLink(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO) {
        HttpUtil.post(remoteUpdateShortLinkDefaultUrl, JSON.toJSONString(shortLinkUpdateReqDTO));
    }

    /**
     * 根据 URL 获取标题
     * @param url 目标网站地址
     * @return 网站标题
     */
    @Override
    public Result<String> getTitleByUrl(@RequestParam("url") String url) {
        String resultStr = HttpUtil.get(remoteGetTitleShortLinkDefaultUrl + url);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 保存回收站
     * @param  recycleBinSaveReqDTO 回收站参数
     */
    @Override
    public void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        HttpUtil.post(remoteRecycleSaveShortLinkDefaultUrl, JSON.toJSONString(recycleBinSaveReqDTO));
    }

    /**
     * 分页查询回收站短链接
     * @param recycleBinPageReqDTO 分页短链接请求参数
     * @return 查询短链接响应
     */
    @Override
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(RecycleBinPageReqDTO recycleBinPageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gidList", recycleBinPageReqDTO.getGidList());
        requestMap.put("current", recycleBinPageReqDTO.getCurrent());
        requestMap.put("size", recycleBinPageReqDTO.getSize());
        String resultPageStr = HttpUtil.get(remoteRecyclePageShortLinkDefaultUrl, requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /**
     * 回收站恢复短链接功能
     * @param recycleBinRecoverReqDTO 请求参数
     */
    @Override
    public void recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        HttpUtil.post(remoteRecycleRecoverShortLinkDefaultUrl, JSON.toJSONString(recycleBinRecoverReqDTO));
    }

    /**
     * 访问单个短链接指定时间内监控数据
     * @param requestParam 访问短链接监控请求参数
     * @return 短链接监控信息
     */
    @Override
    public Result<ShortLinkStatsRespDTO> oneShortLinkStats(ShortLinkStatsReqDTO requestParam) {
        String resultBodyStr = HttpUtil.get(remoteStatsShortLinkDefaultUrl, BeanUtil.beanToMap(requestParam));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }

}
