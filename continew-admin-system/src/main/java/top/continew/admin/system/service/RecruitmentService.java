package top.continew.admin.system.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.system.model.query.RecruitmentQuery;
import top.continew.admin.system.model.req.RecruitmentReq;
import top.continew.admin.system.model.resp.RecruitmentDetailResp;
import top.continew.admin.system.model.resp.RecruitmentResp;

/**
 * 招聘计划业务接口
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
public interface RecruitmentService extends BaseService<RecruitmentResp, RecruitmentDetailResp, RecruitmentQuery, RecruitmentReq> {}