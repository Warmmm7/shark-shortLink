package com.shark.shortlink.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkGroupStatsAccessRecordReqDTO;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkGroupStatsReqDTO;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkStatsReqDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkStatsRespDTO;
import com.shark.shortlink.admin.remote.service.ShortLinkActualRemoteService;
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
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;
    @Autowired
    public ShortLinkStatsController(ShortLinkActualRemoteService shortLinkActualRemoteService) {
        this.shortLinkActualRemoteService = shortLinkActualRemoteService;
    }

    /**
     * 访问单个短链接指定时间内监控数据
     */
    @GetMapping("/api/short-link/admin/v1/stats")
    public Result<ShortLinkStatsRespDTO> shortLinkStats(ShortLinkStatsReqDTO requestParam) {
        return shortLinkActualRemoteService.oneShortLinkStats(requestParam.getFullShortUrl(), requestParam.getGid(), requestParam.getStartDate(), requestParam.getEndDate());
    }

    /**
     * 访问单个短链接指定时间内访问记录监控数据
     */
    @GetMapping("/api/short-link/admin/v1/stats/access-record")
    public Result<Page<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam) {
        return shortLinkActualRemoteService.shortLinkStatsAccessRecord(requestParam.getFullShortUrl(), requestParam.getGid(), requestParam.getStartDate(), requestParam.getEndDate());
    }

    /**
     * 访问分组短链接指定时间内监控数据
     */
    @GetMapping("/api/short-link/admin/v1/stats/group")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStats(ShortLinkGroupStatsReqDTO requestParam) {
        return shortLinkActualRemoteService.groupShortLinkStats(requestParam.getGid(), requestParam.getStartDate(), requestParam.getEndDate());
    }

    /**
     * 访问分组短链接指定时间内访问记录监控数据
     */
    @GetMapping("/api/short-link/admin/v1/stats/access-record/group")
    public Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam) {
        return shortLinkActualRemoteService.groupShortLinkStatsAccessRecord(requestParam.getGid(), requestParam.getStartDate(), requestParam.getEndDate());
    }
}