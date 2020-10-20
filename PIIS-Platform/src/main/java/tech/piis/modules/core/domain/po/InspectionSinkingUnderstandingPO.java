package tech.piis.modules.core.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * SINKNG_UNDERSTANDING 对象 inspection_sinkng_understanding
 * 
 * @author Kevin
 * @date 2020-10-12
 */

@TableName("inspection_sinking_understanding")
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class InspectionSinkingUnderstandingPO extends PIBaseEntity
{
    /** 下沉了解编号 */
    @TableId(value = "SINKING_UNDERSTANDING_ID")
    private String sinkingUnderstandingId;
    /** 计划编号 */
    private String planId;
    /**
     * 被巡视单位ID
     */
    private String unitsId;
    /** 了解单位 */
    private String researchCompany;
    /** 下沉时间 */
    private Date sinkingTime;
    /** 目标 */
    private String target;
    /** 创建人 */
    private String createdBy;

    /**
     * 文件信息
     */
    @NotEmpty(message = "文件不能为空！")
    @TableField(exist = false)
    private List<PiisDocumentPO> documents;
}
