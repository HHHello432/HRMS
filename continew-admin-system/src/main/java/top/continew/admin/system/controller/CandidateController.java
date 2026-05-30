package top.continew.admin.system.controller;

import top.continew.starter.extension.crud.enums.Api;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.controller.BaseController;
import top.continew.admin.system.model.query.CandidateQuery;
import top.continew.admin.system.model.req.CandidateReq;
import top.continew.admin.system.model.resp.CandidateDetailResp;
import top.continew.admin.system.model.resp.CandidateResp;
import top.continew.admin.system.service.CandidateService;

/**
 * 应聘者管理 API
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Tag(name = "应聘者管理 API")
@RestController
@CrudRequestMapping(value = "/system/candidate", api = {Api.PAGE, Api.DETAIL, Api.ADD, Api.UPDATE, Api.DELETE, Api.EXPORT})
public class CandidateController extends BaseController<CandidateService, CandidateResp, CandidateDetailResp, CandidateQuery, CandidateReq> {}