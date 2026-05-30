package top.continew.admin.system.model.query;

import java.io.Serial;
import java.io.Serializable;
import java.time.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import top.continew.starter.data.core.annotation.Query;
import top.continew.starter.data.core.enums.QueryType;

/**
 * 面试记录查询条件
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@Schema(description = "面试记录查询条件")
public class InterviewQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联应聘者
     */
    @Schema(description = "关联应聘者")
    @Query(type = QueryType.EQ)
    private Long candidateId;

    /**
     * 面试时间
     */
    @Schema(description = "面试时间")
    @Query(type = QueryType.EQ)
    private LocalDateTime interviewTime;

    /**
     * 面试结果(1:通过 2:待定 3:不通过)
     */
    @Schema(description = "面试结果(1:通过 2:待定 3:不通过)")
    @Query(type = QueryType.EQ)
    private Integer result;
}