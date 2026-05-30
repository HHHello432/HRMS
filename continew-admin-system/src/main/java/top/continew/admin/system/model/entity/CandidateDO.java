package top.continew.admin.system.model.entity;

import java.io.Serial;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.starter.extension.crud.model.entity.BaseDO;

/**
 * 应聘者实体
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@TableName("rec_candidate")
public class CandidateDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联招聘计划
     */
    private Long recruitmentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别(0:未知 1:男 2:女)
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学历
     */
    private String education;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 专业
     */
    private String major;

    /**
     * 工作经历
     */
    private String experience;

    /**
     * 简历附件
     */
    private String resumeUrl;

    /**
     * 状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)
     */
    private Integer status;
}