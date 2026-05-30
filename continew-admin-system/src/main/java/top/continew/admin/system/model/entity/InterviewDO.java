package top.continew.admin.system.model.entity;

import java.io.Serial;
import java.time.*;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableName;

import top.continew.starter.extension.crud.model.entity.BaseDO;

/**
 * 面试记录实体
 *
 * @author HRMS
 * @since 2026/05/30 08:25
 */
@Data
@TableName("rec_interview")
public class InterviewDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关联应聘者
     */
    private Long candidateId;

    /**
     * 面试轮次
     */
    private Integer round;

    /**
     * 面试官
     */
    private String interviewer;

    /**
     * 面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 面试地点
     */
    private String address;

    /**
     * 面试内容
     */
    private String content;

    /**
     * 面试评价
     */
    private String evaluation;

    /**
     * 面试结果(1:通过 2:待定 3:不通过)
     */
    private Integer result;

    /**
     * 下一轮面试时间
     */
    private LocalDateTime nextRoundTime;
}