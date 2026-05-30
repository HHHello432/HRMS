package top.continew.admin.system.model.resp;

import java.io.Serial;
import java.time.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.starter.extension.crud.model.resp.BaseDetailResp;

/**
 * 应聘者详情信息
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "应聘者详情信息")
public class CandidateDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联招聘计划
     */
    @Schema(description = "关联招聘计划")
    @ExcelProperty(value = "关联招聘计划")
    private Long recruitmentId;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 性别(0:未知 1:男 2:女)
     */
    @Schema(description = "性别(0:未知 1:男 2:女)")
    @ExcelProperty(value = "性别(0:未知 1:男 2:女)")
    private Integer gender;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 学历
     */
    @Schema(description = "学历")
    @ExcelProperty(value = "学历")
    private String education;

    /**
     * 毕业院校
     */
    @Schema(description = "毕业院校")
    @ExcelProperty(value = "毕业院校")
    private String school;

    /**
     * 专业
     */
    @Schema(description = "专业")
    @ExcelProperty(value = "专业")
    private String major;

    /**
     * 工作经历
     */
    @Schema(description = "工作经历")
    @ExcelProperty(value = "工作经历")
    private String experience;

    /**
     * 简历附件
     */
    @Schema(description = "简历附件")
    @ExcelProperty(value = "简历附件")
    private String resumeUrl;

    /**
     * 状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)
     */
    @Schema(description = "状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)")
    @ExcelProperty(value = "状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)")
    private Integer status;
}