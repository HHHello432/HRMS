package top.continew.admin.system.model.req;

import java.io.Serial;
import java.time.*;

import jakarta.validation.constraints.*;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

import org.hibernate.validator.constraints.Length;

import top.continew.starter.extension.crud.model.req.BaseReq;

/**
 * 创建或修改面试记录参数
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@Schema(description = "创建或修改面试记录参数")
public class InterviewReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联应聘者
     */
    @Schema(description = "关联应聘者")
    @NotNull(message = "关联应聘者不能为空")
    private Long candidateId;

    /**
     * 面试轮次
     */
    @Schema(description = "面试轮次")
    private Integer round;

    /**
     * 面试官
     */
    @Schema(description = "面试官")
    @Length(max = 100, message = "面试官长度不能超过 {max} 个字符")
    private String interviewer;

    /**
     * 面试时间
     */
    @Schema(description = "面试时间")
    private LocalDateTime interviewTime;

    /**
     * 面试地点
     */
    @Schema(description = "面试地点")
    @Length(max = 500, message = "面试地点长度不能超过 {max} 个字符")
    private String address;

    /**
     * 面试内容
     */
    @Schema(description = "面试内容")
    @Length(max = 65535, message = "面试内容长度不能超过 {max} 个字符")
    private String content;

    /**
     * 面试评价
     */
    @Schema(description = "面试评价")
    @Length(max = 65535, message = "面试评价长度不能超过 {max} 个字符")
    private String evaluation;

    /**
     * 面试结果(1:通过 2:待定 3:不通过)
     */
    @Schema(description = "面试结果(1:通过 2:待定 3:不通过)")
    private Integer result;
}