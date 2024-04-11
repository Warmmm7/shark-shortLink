package com.shark.shortlink.admin.controller;


import com.shark.shortlink.admin.remote.dto.req.ShortLinkStatsReqDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkStatsRespDTO;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.remote.service.ShortLinkRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接监控控制层
 */
@RestController
public class ShortLinkStatsController {

    /**
     * 后续重构为 SpringCloud Feign 调用
     */
    private final ShortLinkRemoteService shortLinkRemoteService;
    @Autowired
    public ShortLinkStatsController(ShortLinkRemoteService shortLinkRemoteService) {
        this.shortLinkRemoteService = shortLinkRemoteService;
    }

    /**
     * 访问单个短链接指定时间内监控数据
     */
    @GetMapping("/api/short-link/admin/v1/stats")
    public Result<ShortLinkStatsRespDTO> shortLinkStats(ShortLinkStatsReqDTO requestParam) {
        return shortLinkRemoteService.oneShortLinkStats(requestParam);
    }
}