package com.shark.shortlink.admin.controller;

import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.common.convention.result.Results;
import com.shark.shortlink.admin.dto.req.GroupSaveReqDTO;
import com.shark.shortlink.admin.dto.req.GroupSortReqDTO;
import com.shark.shortlink.admin.dto.req.GroupUpdateReqDTO;
import com.shark.shortlink.admin.dto.resp.GroupRespDTO;
import com.shark.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/short-link/admin/v1/group")
public class GroupController {
    private final GroupService groupService;

    /**
     * 新增短连接分组
     * @param saveReqDTO
     * @return
     */
    @PostMapping
    public Result<Void> saveGroup(@RequestBody GroupSaveReqDTO saveReqDTO){
        groupService.saveGroup(saveReqDTO);
        return Results.success();
    }

    /**
     * 查询短连接分组列表
     * @return
     */
    @GetMapping
    public Result<List<GroupRespDTO>> listGroup(){
        return Results.success(groupService.listGroup());
    }

    /**
     * 修改群组
     * @param groupUpdateReqDTO
     * @return
     */
    @PutMapping
    public Result<Void> update(@RequestBody GroupUpdateReqDTO groupUpdateReqDTO){
        groupService.update(groupUpdateReqDTO);
        return Results.success();
    }

    @DeleteMapping
    public Result<Void> delete(@RequestParam String gid){
        groupService.detele(gid);
        return Results.success();
    }

    /**
     * 排序短连接群组
     * @param groupSortReqDTO
     * @return
     */
    @PostMapping("/sort")
    public Result<Void> sortGroup(@RequestBody List<GroupSortReqDTO> groupSortReqDTO){
        groupService.sortGroup(groupSortReqDTO);
        return Results.success();
    }

}
