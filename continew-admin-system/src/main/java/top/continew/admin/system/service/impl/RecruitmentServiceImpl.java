package top.continew.admin.system.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.impl.BaseServiceImpl;
import top.continew.admin.system.mapper.RecruitmentMapper;
import top.continew.admin.system.model.entity.RecruitmentDO;
import top.continew.admin.system.model.query.RecruitmentQuery;
import top.continew.admin.system.model.req.RecruitmentReq;
import top.continew.admin.system.model.resp.RecruitmentDetailResp;
import top.continew.admin.system.model.resp.RecruitmentResp;
import top.continew.admin.system.service.RecruitmentService;

/**
 * 招聘计划业务实现
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Service
@RequiredArgsConstructor
public class RecruitmentServiceImpl extends BaseServiceImpl<RecruitmentMapper, RecruitmentDO, RecruitmentResp, RecruitmentDetailResp, RecruitmentQuery, RecruitmentReq> implements RecruitmentService {}