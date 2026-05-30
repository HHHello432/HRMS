package top.continew.admin.system.model.resp;

import java.io.Serial;
import java.time.*;
import java.math.BigDecimal;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.starter.extension.crud.model.resp.BaseDetailResp;

/**
 * 招聘计划详情信息
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "招聘计划详情信息")
public class RecruitmentDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 职位名称
     */
    @Schema(description = "职位名称")
    @ExcelProperty(value = "职位名称")
    private String title;

    /**
     * 招聘部门
     */
    @Schema(description = "招聘部门")
    @ExcelProperty(value = "招聘部门")
    private String department;

    /**
     * 职位类别(全职/兼职/实习)
     */
    @Schema(description = "职位类别(全职/兼职/实习)")
    @ExcelProperty(value = "职位类别(全职/兼职/实习)")
    private String positionType;

    /**
     * 招聘人数
     */
    @Schema(description = "招聘人数")
    @ExcelProperty(value = "招聘人数")
    private Integer headcount;

    /**
     * 职位描述
     */
    @Schema(description = "职位描述")
    @ExcelProperty(value = "职位描述")
    private String description;

    /**
     * 任职要求
     */
    @Schema(description = "任职要求")
    @ExcelProperty(value = "任职要求")
    private String requirement;

    /**
     * 最低薪资
     */
    @Schema(description = "最低薪资")
    @ExcelProperty(value = "最低薪资")
    private BigDecimal salaryMin;

    /**
     * 最高薪资
     */
    @Schema(description = "最高薪资")
    @ExcelProperty(value = "最高薪资")
    private BigDecimal salaryMax;

    /**
     * 工作地点
     */
    @Schema(description = "工作地点")
    @ExcelProperty(value = "工作地点")
    private String address;

    /**
     * 状态(0:关闭 1:发布中)
     */
    @Schema(description = "状态(0:关闭 1:发布中)")
    @ExcelProperty(value = "状态(0:关闭 1:发布中)")
    private Integer status;

    /**
     * 截止日期
     */
    @Schema(description = "截止日期")
    @ExcelProperty(value = "截止日期")
    private LocalDate deadline;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @ExcelProperty(value = "排序")
    private Integer sort;
}