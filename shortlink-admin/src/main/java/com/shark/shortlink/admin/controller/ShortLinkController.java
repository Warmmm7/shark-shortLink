package com.shark.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.common.convention.result.Results;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkBatchCreateReqDTO;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.shark.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkBaseInfoRespDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkBatchCreateRespDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.shark.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.shark.shortlink.admin.remote.service.ShortLinkActualRemoteService;
import com.shark.shortlink.admin.toolkit.EasyExcelWebUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 短链接后管控制层
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {


    /**
     *  SpringCloud Feign 调用
     */
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;
    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return shortLinkActualRemoteService.createShortLink(requestParam);
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<Page<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return shortLinkActualRemoteService.pageShortLink(requestParam.getGid(), requestParam.getOrderTag(), requestParam.getCurrent(), requestParam.getSize());
    }

    /**
     * 修改短链接
     */
    @PostMapping("/api/short-link/admin/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkActualRemoteService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 批量创建短链接
     */
    @SneakyThrows
    @PostMapping("/api/short-link/admin/v1/create/batch")
    public void batchCreateShortLink(@RequestBody ShortLinkBatchCreateReqDTO requestParam, HttpServletResponse response) {
        Result<ShortLinkBatchCreateRespDTO> shortLinkBatchCreateRespDTOResult = shortLinkActualRemoteService.batchCreateShortLink(requestParam);
        if (shortLinkBatchCreateRespDTOResult.isSuccess()) {
            List<ShortLinkBaseInfoRespDTO> baseLinkInfos = shortLinkBatchCreateRespDTOResult.getData().getBaseLinkInfos();
            EasyExcelWebUtil.write(response, "批量创建短链接:", ShortLinkBaseInfoRespDTO.class, baseLinkInfos);
        }
    }


}