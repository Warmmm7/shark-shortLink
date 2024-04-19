package com.shark.shortlink.project.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shark.shortlink.project.common.convention.result.Result;
import com.shark.shortlink.project.common.convention.result.Results;
import com.shark.shortlink.project.dto.req.ShortLinkBatchCreateReqDTO;
import com.shark.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.shark.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.shark.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkBatchCreateRespDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.shark.shortlink.project.handler.CustomBlockHandler;
import com.shark.shortlink.project.service.ShortLinkService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短连接控制层
 */
@RestController
@RequiredArgsConstructor

public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    /**
     * 短链接跳转原始链接
     */
    @GetMapping("/{short-uri}")
    public void restoreUrl(@PathVariable("short-uri") String shortUri, ServletRequest request, ServletResponse response) {
        shortLinkService.restoreUrl(shortUri, request, response);
    }

    /**
     * 创建短连接
     */
    @PostMapping("/api/short-link/v1/create")
    @SentinelResource(
            value = "create_short-link",
            blockHandler = "createShortLinkBlockHandlerMethod",
            blockHandlerClass = CustomBlockHandler.class
    )
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO){
        return Results.success(shortLinkService.createShortLink(shortLinkCreateReqDTO));
    }

    /**
     * 分页查询短链接
     * @param shortLinkPageReqDTO
     * @return
     */
    @GetMapping("/api/short-link/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO){
        return Results.success(shortLinkService.pageShortLink(shortLinkPageReqDTO));
    }

    /**
     * 查询短链接分组类的链接数量
     * @param gids
     * @return
     */
    @GetMapping("/api/short-link/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("gids") List<String> gids){
        return Results.success(shortLinkService.listGroupShortLinkCount(gids));
    }

    /**
     * 修改短链接
     */
    @PostMapping("/api/short-link/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO shortLinkUpdateReqDTO) {
        shortLinkService.updateShortLink(shortLinkUpdateReqDTO);
        return Results.success();
    }

    /**
     * 批量创建短链接
     */
    @PostMapping("/api/short-link/v1/create/batch")
    public Result<ShortLinkBatchCreateRespDTO> batchCreateShortLink(@RequestBody ShortLinkBatchCreateReqDTO requestParam) {
        return Results.success(shortLinkService.batchCreateShortLink(requestParam));
    }



}
