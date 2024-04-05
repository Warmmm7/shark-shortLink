package com.shark.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.common.convention.result.Results;
import com.shark.shortlink.admin.remote.dto.req.RecycleBinPageReqDTO;
import com.shark.shortlink.admin.remote.dto.req.RecycleBinRecoverReqDTO;
import com.shark.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import com.shark.shortlink.admin.remote.ShortLinkRemoteService;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.shark.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回收站管理控制层
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {
    private final RecycleBinService recycleBinService;

    /**
     * 后续重构为 SpringCloud Feign 调用
     */
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 保存回收站
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        shortLinkRemoteService.saveRecycleBin(recycleBinSaveReqDTO);
        return Results.success();
    }

    /**
     * 分页查询回收站短链接
     */
    @GetMapping("/api/short-link/admin/v1/recycle-bin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(RecycleBinPageReqDTO recycleBinPageReqDTO) {
        return recycleBinService.pageRecycleBinShortLink(recycleBinPageReqDTO);
    }

    /**
     * 恢复短链接
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        shortLinkRemoteService.recoverRecycleBin(recycleBinRecoverReqDTO);
        return Results.success();
    }
}