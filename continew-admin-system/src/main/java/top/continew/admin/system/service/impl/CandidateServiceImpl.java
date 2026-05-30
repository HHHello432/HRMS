package top.continew.admin.system.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.impl.BaseServiceImpl;
import top.continew.admin.system.mapper.CandidateMapper;
import top.continew.admin.system.model.entity.CandidateDO;
import top.continew.admin.system.model.query.CandidateQuery;
import top.continew.admin.system.model.req.CandidateReq;
import top.continew.admin.system.model.resp.CandidateDetailResp;
import top.continew.admin.system.model.resp.CandidateResp;
import top.continew.admin.system.service.CandidateService;

/**
 * 应聘者业务实现
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl extends BaseServiceImpl<CandidateMapper, CandidateDO, CandidateResp, CandidateDetailResp, CandidateQuery, CandidateReq> implements CandidateService {}