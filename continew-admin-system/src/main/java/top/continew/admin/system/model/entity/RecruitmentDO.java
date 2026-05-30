package top.continew.admin.system.model.entity;

import java.io.Serial;
import java.time.*;
import java.math.BigDecimal;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.starter.extension.crud.model.entity.BaseDO;

/**
 * 招聘计划实体
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@TableName("rec_recruitment")
public class RecruitmentDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 职位名称
     */
    private String title;

    /**
     * 招聘部门
     */
    private String department;

    /**
     * 职位类别(全职/兼职/实习)
     */
    private String positionType;

    /**
     * 招聘人数
     */
    private Integer headcount;

    /**
     * 职位描述
     */
    private String description;

    /**
     * 任职要求
     */
    private String requirement;

    /**
     * 最低薪资
     */
    private BigDecimal salaryMin;

    /**
     * 最高薪资
     */
    private BigDecimal salaryMax;

    /**
     * 工作地点
     */
    private String address;

    /**
     * 状态(0:关闭 1:发布中)
     */
    private Integer status;

    /**
     * 截止日期
     */
    private LocalDate deadline;

    /**
     * 排序
     */
    private Integer sort;
}