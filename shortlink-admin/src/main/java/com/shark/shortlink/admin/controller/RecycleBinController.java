package com.shark.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.common.convention.result.Results;
import com.shark.shortlink.admin.remote.dto.req.RecycleBinPageReqDTO;
import com.shark.shortlink.admin.remote.dto.req.RecycleBinRecoverReqDTO;
import com.shark.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.shark.shortlink.admin.remote.service.ShortLinkActualRemoteService;
import com.shark.shortlink.admin.service.RecycleBinService;
import com.shark.shortlink.admin.remote.dto.req.RecycleBinRemoveReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回收站管理控制层
 */
@RestController

public class RecycleBinController {


    private final RecycleBinService recycleBinService;
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    @Autowired
    public RecycleBinController(RecycleBinService recycleBinService, ShortLinkActualRemoteService shortLinkActualRemoteService) {
        this.recycleBinService = recycleBinService;
        this.shortLinkActualRemoteService = shortLinkActualRemoteService;
    }


    /**
     * 保存回收站
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        shortLinkActualRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 分页查询回收站短链接
     */
    @GetMapping("/api/short-link/admin/v1/recycle-bin/page")
    public Result<Page<ShortLinkPageRespDTO>> pageShortLink(RecycleBinPageReqDTO requestParam) {
        return recycleBinService.pageRecycleBinShortLink(requestParam);
    }

    /**
     * 恢复短链接
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam) {
        shortLinkActualRemoteService.recoverRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 移动至回收站
     * @param requestParam
     * @return
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO requestParam) {
        shortLinkActualRemoteService.removeRecycleBin(requestParam);
        return Results.success();
    }
}