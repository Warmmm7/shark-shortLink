package com.shark.shortlink.project.controller;

import com.shark.shortlink.project.common.convention.result.Result;
import com.shark.shortlink.project.common.convention.result.Results;
import com.shark.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.shark.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.shark.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短连接控制层
 */
@RestController
@RequiredArgsConstructor

public class ShortLinkController {

    private final ShortLinkService shortLinkService;
    /**
     * 创建短连接
     */
    @PostMapping("/api/short-link/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO){
        return Results.success(shortLinkService.createShortLink(shortLinkCreateReqDTO));
    }
}
