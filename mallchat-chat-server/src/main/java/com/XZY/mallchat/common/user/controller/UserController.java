package com.XZY.mallchat.common.user.controller;


import com.XZY.mallchat.common.common.domain.dto.RequestInfo;
import com.XZY.mallchat.common.common.utils.AssertUtil;
import com.XZY.mallchat.common.common.utils.RequestHolderUtils;
import com.XZY.mallchat.common.user.domain.enums.RoleEnum;
import com.XZY.mallchat.common.user.domain.vo.req.*;
import com.XZY.mallchat.common.user.domain.vo.resp.ApiResult;
import com.XZY.mallchat.common.user.domain.vo.resp.BadgeRespResp;
import com.XZY.mallchat.common.user.domain.vo.resp.UserInfoResp;
import com.XZY.mallchat.common.user.service.IRoleService;
import com.XZY.mallchat.common.user.service.UserService;
import com.XZY.mallchat.common.websocket.domain.dto.ItemInfoDTO;
import com.XZY.mallchat.common.websocket.domain.dto.SummeryInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 *
 * @since 2024-03-28
 */
@RestController
@RequestMapping("/capi/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private IRoleService iRoleService;
    @GetMapping("/userInfo")
    @ApiOperation(value = "获取用户个人信息(通过token)")
    public ApiResult<UserInfoResp> getUserInfo(){
      RequestInfo requestInfo = RequestHolderUtils.get();
        return ApiResult.success(userService.getUserinfo(requestInfo.getUid()));
    }


    @PutMapping("/name")
    @ApiOperation(value = "修改用户名字")
    public ApiResult<Void> modifyName(@Valid @RequestBody ModifyNameReq req) {
        userService.modifyname(RequestHolderUtils.get().getUid(), req);
        return ApiResult.success();
    }

    @GetMapping("/badges")
    @ApiOperation(value = "可选徽章预览")
    public ApiResult<List<BadgeRespResp>> badges(){
        //查询所有徽章 通过缓存
        return ApiResult.success(userService.badges(RequestHolderUtils.get().getUid()));
    }

    @PutMapping("/badge")
    @ApiOperation(value = "佩戴徽章")
    public ApiResult<Void> badges(@Valid @RequestBody WearingBadgeReq req){
        //查询所有徽章 通过缓存
        userService.wearingBadge(RequestHolderUtils.get().getUid(), req.getItemId());
        return ApiResult.success();
    }

    @PutMapping("/black")
    @ApiOperation("拉黑用户")
    public ApiResult<Void> black(@Valid @RequestBody BlackReq req) {
        Long uid = RequestHolderUtils.get().getUid();
        boolean hasPower = iRoleService.hasPower(uid, RoleEnum.ADMIN);
        AssertUtil.isTrue(hasPower, "没有权限");
        userService.black(req);
        return ApiResult.success();
    }


    @PostMapping("/public/summary/userInfo/batch")
    @ApiOperation("用户聚合信息-返回的代表需要刷新的")
    public ApiResult<List<SummeryInfoDTO>> getSummeryUserInfo(@Valid @RequestBody SummeryInfoReq req) {
        return ApiResult.success(userService.getSummeryUserInfo(req));
    }

    @PostMapping("/public/badges/batch")
    @ApiOperation("徽章聚合信息-返回的代表需要刷新的")
    public ApiResult<List<ItemInfoDTO>> getItemInfo(@Valid @RequestBody ItemInfoReq req) {
        return ApiResult.success(userService.getItemInfo(req));
    }





}

