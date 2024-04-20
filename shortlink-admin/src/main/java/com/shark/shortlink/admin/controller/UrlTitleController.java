package com.shark.shortlink.admin.controller;

import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.remote.service.ShortLinkActualRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * URL 标题控制层
 */
@RestController

public class UrlTitleController {

    /**
     * 重构为 SpringCloud Feign 调用
     */
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;
    @Autowired
    public UrlTitleController(ShortLinkActualRemoteService shortLinkActualRemoteService) {
        this.shortLinkActualRemoteService = shortLinkActualRemoteService;
    }

    /**
     * 根据URL获取对应网站的标题
     */
    @GetMapping("/api/short-link/admin/v1/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url) {
        return shortLinkActualRemoteService.getTitleByUrl(url);
    }
}