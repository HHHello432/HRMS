package top.continew.admin.system.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.system.model.query.InterviewQuery;
import top.continew.admin.system.model.req.InterviewReq;
import top.continew.admin.system.model.resp.InterviewDetailResp;
import top.continew.admin.system.model.resp.InterviewResp;

/**
 * 面试记录业务接口
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
public interface InterviewService extends BaseService<InterviewResp, InterviewDetailResp, InterviewQuery, InterviewReq> {}