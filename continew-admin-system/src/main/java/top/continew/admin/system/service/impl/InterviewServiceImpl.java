package top.continew.admin.system.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.continew.starter.extension.crud.service.impl.BaseServiceImpl;
import top.continew.admin.system.mapper.InterviewMapper;
import top.continew.admin.system.model.entity.InterviewDO;
import top.continew.admin.system.model.query.InterviewQuery;
import top.continew.admin.system.model.req.InterviewReq;
import top.continew.admin.system.model.resp.InterviewDetailResp;
import top.continew.admin.system.model.resp.InterviewResp;
import top.continew.admin.system.service.InterviewService;

/**
 * 面试记录业务实现
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Service
@RequiredArgsConstructor
public class InterviewServiceImpl extends BaseServiceImpl<InterviewMapper, InterviewDO, InterviewResp, InterviewDetailResp, InterviewQuery, InterviewReq> implements InterviewService {}