package top.continew.admin.system.service;

import top.continew.starter.extension.crud.service.BaseService;
import top.continew.admin.system.model.query.CandidateQuery;
import top.continew.admin.system.model.req.CandidateReq;
import top.continew.admin.system.model.resp.CandidateDetailResp;
import top.continew.admin.system.model.resp.CandidateResp;

/**
 * 应聘者业务接口
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
public interface CandidateService extends BaseService<CandidateResp, CandidateDetailResp, CandidateQuery, CandidateReq> {}