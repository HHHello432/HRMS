package top.continew.admin.system.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.controller.BaseController;
import top.continew.admin.system.model.query.RecruitmentQuery;
import top.continew.admin.system.model.req.RecruitmentReq;
import top.continew.admin.system.model.resp.RecruitmentDetailResp;
import top.continew.admin.system.model.resp.RecruitmentResp;
import top.continew.admin.system.service.RecruitmentService;

/**
 * 招聘计划管理 API
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Tag(name = "招聘计划管理 API")
@RestController
@CrudRequestMapping(value = "/system/recruitment", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class RecruitmentController extends BaseController<RecruitmentService, RecruitmentResp, RecruitmentDetailResp, RecruitmentQuery, RecruitmentReq> {}