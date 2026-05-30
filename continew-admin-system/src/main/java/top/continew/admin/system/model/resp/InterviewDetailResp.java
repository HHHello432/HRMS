package top.continew.admin.system.model.resp;

import java.io.Serial;
import java.time.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import top.continew.starter.extension.crud.model.resp.BaseDetailResp;

/**
 * 面试记录详情信息
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@ExcelIgnoreUnannotated
@Schema(description = "面试记录详情信息")
public class InterviewDetailResp extends BaseDetailResp {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联应聘者
     */
    @Schema(description = "关联应聘者")
    @ExcelProperty(value = "关联应聘者")
    private Long candidateId;

    /**
     * 面试轮次
     */
    @Schema(description = "面试轮次")
    @ExcelProperty(value = "面试轮次")
    private Integer round;

    /**
     * 面试官
     */
    @Schema(description = "面试官")
    @ExcelProperty(value = "面试官")
    private String interviewer;

    /**
     * 面试时间
     */
    @Schema(description = "面试时间")
    @ExcelProperty(value = "面试时间")
    private LocalDateTime interviewTime;

    /**
     * 面试地点
     */
    @Schema(description = "面试地点")
    @ExcelProperty(value = "面试地点")
    private String address;

    /**
     * 面试内容
     */
    @Schema(description = "面试内容")
    @ExcelProperty(value = "面试内容")
    private String content;

    /**
     * 面试评价
     */
    @Schema(description = "面试评价")
    @ExcelProperty(value = "面试评价")
    private String evaluation;

    /**
     * 面试结果(1:通过 2:待定 3:不通过)
     */
    @Schema(description = "面试结果(1:通过 2:待定 3:不通过)")
    @ExcelProperty(value = "面试结果(1:通过 2:待定 3:不通过)")
    private Integer result;

    /**
     * 下一轮面试时间
     */
    @Schema(description = "下一轮面试时间")
    @ExcelProperty(value = "下一轮面试时间")
    private LocalDateTime nextRoundTime;
}