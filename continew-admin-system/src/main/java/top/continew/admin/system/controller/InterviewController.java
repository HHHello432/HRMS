package top.continew.admin.system.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.controller.BaseController;
import top.continew.admin.system.model.query.InterviewQuery;
import top.continew.admin.system.model.req.InterviewReq;
import top.continew.admin.system.model.resp.InterviewDetailResp;
import top.continew.admin.system.model.resp.InterviewResp;
import top.continew.admin.system.service.InterviewService;

/**
 * 面试记录管理 API
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Tag(name = "面试记录管理 API")
@RestController
@CrudRequestMapping(value = "/system/interview", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class InterviewController extends BaseController<InterviewService, InterviewResp, InterviewDetailResp, InterviewQuery, InterviewReq> {}