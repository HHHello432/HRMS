package top.continew.admin.system.model.query;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

/**
 * 应聘者查询条件
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@Schema(description = "应聘者查询条件")
public class CandidateQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联招聘计划
     */
    @Schema(description = "关联招聘计划")
    @Query(type = QueryType.EQ)
    private Long recruitmentId;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @Query(type = QueryType.EQ)
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @Query(type = QueryType.EQ)
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Query(type = QueryType.EQ)
    private String email;

    /**
     * 学历
     */
    @Schema(description = "学历")
    @Query(type = QueryType.EQ)
    private String education;

    /**
     * 状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)
     */
    @Schema(description = "状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)")
    @Query(type = QueryType.EQ)
    private Integer status;
}