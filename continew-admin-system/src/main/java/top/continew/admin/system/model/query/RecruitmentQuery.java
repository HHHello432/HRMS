package top.continew.admin.system.model.query;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;
import java.math.BigDecimal;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

/**
 * 招聘计划查询条件
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@Schema(description = "招聘计划查询条件")
public class RecruitmentQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 职位名称
     */
    @Schema(description = "职位名称")
    @Query(type = QueryType.EQ)
    private String title;

    /**
     * 招聘部门
     */
    @Schema(description = "招聘部门")
    @Query(type = QueryType.EQ)
    private String department;

    /**
     * 职位类别(全职/兼职/实习)
     */
    @Schema(description = "职位类别(全职/兼职/实习)")
    @Query(type = QueryType.EQ)
    private String positionType;

    /**
     * 状态(0:关闭 1:发布中)
     */
    @Schema(description = "状态(0:关闭 1:发布中)")
    @Query(type = QueryType.EQ)
    private Integer status;
}