package top.continew.admin.system.model.req;

import java.io.Serial;
import java.time.*;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import top.continew.starter.extension.crud.model.req.BaseReq;

/**
 * 创建或修改应聘者参数
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@Schema(description = "创建或修改应聘者参数")
public class CandidateReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联招聘计划
     */
    @Schema(description = "关联招聘计划")
    @NotNull(message = "关联招聘计划不能为空")
    private Long recruitmentId;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")
    @Length(max = 100, message = "姓名长度不能超过 {max} 个字符")
    private String name;

    /**
     * 性别(0:未知 1:男 2:女)
     */
    @Schema(description = "性别(0:未知 1:男 2:女)")
    private Integer gender;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @Length(max = 20, message = "手机号长度不能超过 {max} 个字符")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Length(max = 200, message = "邮箱长度不能超过 {max} 个字符")
    private String email;

    /**
     * 学历
     */
    @Schema(description = "学历")
    @Length(max = 50, message = "学历长度不能超过 {max} 个字符")
    private String education;

    /**
     * 毕业院校
     */
    @Schema(description = "毕业院校")
    @Length(max = 200, message = "毕业院校长度不能超过 {max} 个字符")
    private String school;

    /**
     * 专业
     */
    @Schema(description = "专业")
    @Length(max = 200, message = "专业长度不能超过 {max} 个字符")
    private String major;

    /**
     * 工作经历
     */
    @Schema(description = "工作经历")
    @Length(max = 65535, message = "工作经历长度不能超过 {max} 个字符")
    private String experience;

    /**
     * 简历附件
     */
    @Schema(description = "简历附件")
    @Length(max = 500, message = "简历附件长度不能超过 {max} 个字符")
    private String resumeUrl;

    /**
     * 状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)
     */
    @Schema(description = "状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)")
    private Integer status;
}