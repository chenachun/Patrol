package tech.piis.modules.core.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import tech.piis.modules.core.domain.vo.UserBriefVO;

import java.util.List;

/**
 * 临时支部 对象 inspection_temp_branch
 *
 * @author Tuzki
 * @date 2020-11-23
 */

@TableName("inspection_temp_branch")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class InspectionTempBranchPO extends PIBaseEntity {
    /**
     * 临时支部编号
     */
    @TableId(value = "TEMP_BRANCH_ID", type = IdType.AUTO)
    private Long tempBranchId;

    /**
     * 临时支部名称
     */
    private String tempBranchName;

    /**
     * 计划ID
     */
    private String planId;
    /**
     * 临时党支部书记编号
     */
    private String secretaryId;
    /**
     * 临时党支部书记姓名
     */
    private String secretaryName;
    /**
     * 临时党支部支委编号
     */
    private String branchPersonId;
    /**
     * 临时党支部支委姓名
     */
    private String branchPersonName;

    /**
     * 临时党支部成员编号
     */
    private String branchMemberIds;

    /**
     * 临时党支部成员名称
     */
    private String branchMemberNames;

    @TableField(exist = false)
    private List<UserBriefVO> memberList;

    @TableField(exist = false)
    private List<PiisDocumentPO> documents;
}
