package top.continew.admin.system.model.req;

import java.io.Serial;
import java.time.*;
import java.math.BigDecimal;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import top.continew.starter.extension.crud.model.req.BaseReq;

/**
 * 创建或修改招聘计划参数
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@Schema(description = "创建或修改招聘计划参数")
public class RecruitmentReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 职位名称
     */
    @Schema(description = "职位名称")
    @NotBlank(message = "职位名称不能为空")
    @Length(max = 200, message = "职位名称长度不能超过 {max} 个字符")
    private String title;

    /**
     * 招聘部门
     */
    @Schema(description = "招聘部门")
    @Length(max = 100, message = "招聘部门长度不能超过 {max} 个字符")
    private String department;

    /**
     * 职位类别(全职/兼职/实习)
     */
    @Schema(description = "职位类别(全职/兼职/实习)")
    @Length(max = 50, message = "职位类别(全职/兼职/实习)长度不能超过 {max} 个字符")
    private String positionType;

    /**
     * 招聘人数
     */
    @Schema(description = "招聘人数")
    private Integer headcount;

    /**
     * 职位描述
     */
    @Schema(description = "职位描述")
    @Length(max = 65535, message = "职位描述长度不能超过 {max} 个字符")
    private String description;

    /**
     * 任职要求
     */
    @Schema(description = "任职要求")
    @Length(max = 65535, message = "任职要求长度不能超过 {max} 个字符")
    private String requirement;

    /**
     * 最低薪资
     */
    @Schema(description = "最低薪资")
    private BigDecimal salaryMin;

    /**
     * 最高薪资
     */
    @Schema(description = "最高薪资")
    private BigDecimal salaryMax;

    /**
     * 工作地点
     */
    @Schema(description = "工作地点")
    @Length(max = 500, message = "工作地点长度不能超过 {max} 个字符")
    private String address;

    /**
     * 状态(0:关闭 1:发布中)
     */
    @Schema(description = "状态(0:关闭 1:发布中)")
    private Integer status;

    /**
     * 截止日期
     */
    @Schema(description = "截止日期")
    private LocalDate deadline;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;
}