package tech.piis.modules.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.piis.modules.core.domain.po.PiisDocumentPO;
import tech.piis.modules.core.service.IPiisDocumentService;
import org.springframework.util.CollectionUtils;
import tech.piis.framework.utils.BizUtils;
import java.util.List;
import java.util.Arrays;
import tech.piis.common.exception.BaseException;
import tech.piis.framework.utils.file.FileUploadUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import tech.piis.modules.core.domain.vo.UnitsBizCountVO;
import static tech.piis.common.constant.OperationConstants.DELETE;
import static tech.piis.common.constant.OperationConstants.INSERT;
import tech.piis.modules.core.mapper.InspectionAttendanceMapper;
import tech.piis.modules.core.domain.po.InspectionAttendancePO;
import tech.piis.modules.core.service.IInspectionAttendanceService;

/**
 * 参会情况Service业务层处理
 *
 * @author Kevin
 * @date 2020-10-19
 */
@Transactional
@Service
public class InspectionAttendanceServiceImpl implements IInspectionAttendanceService {
    @Autowired
    private InspectionAttendanceMapper inspectionAttendanceMapper;

    @Autowired
    private IPiisDocumentService documentService;

    @Value("${piis.profile}")
    private String baseFileUrl;

    /**
     * 统计巡视方案下被巡视单位InspectionAttendance次数
     * @param planId 巡视计划ID
     *
     */
    public List<UnitsBizCountVO> selectInspectionAttendanceCount(String planId) throws BaseException {
        return inspectionAttendanceMapper.selectInspectionAttendanceCount(planId);
    }

    /**
     * 查询参会情况列表
     * @param inspectionAttendance
     * @return
     * @throws BaseException
     */
    public List<InspectionAttendancePO> selectInspectionAttendanceList(InspectionAttendancePO inspectionAttendance) throws BaseException {
        QueryWrapper<InspectionAttendancePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("units_id", inspectionAttendance.getUnitsId());
        queryWrapper.eq("plan_id", inspectionAttendance.getPlanId());
        return inspectionAttendanceMapper.selectList(queryWrapper);
    }

    /**
     * 新增参会情况
     * @param inspectionAttendance
     * @return
     * @throws BaseException
     */
    public int save(InspectionAttendancePO inspectionAttendance) throws BaseException {
        int result = inspectionAttendanceMapper.insert(inspectionAttendance);
        List<PiisDocumentPO> documents = inspectionAttendance.getDocuments();
        if (!CollectionUtils.isEmpty(documents)) {
            for (PiisDocumentPO document : documents) {
                BizUtils.setUpdatedOperation(PiisDocumentPO.class, document);
                documentService.updateDocumentById(document.setObjectId(null).setFileDictId(null));
            }
        }
        return result;
    }

    /**
     * 根据ID修改参会情况
     * @param inspectionAttendance
     * @return
     * @throws BaseException
     */
    public int update(InspectionAttendancePO inspectionAttendance) throws BaseException {
        List<PiisDocumentPO> documents = inspectionAttendance.getDocuments();
        if (!CollectionUtils.isEmpty(documents)) {
            for (PiisDocumentPO document : documents) {
                Integer operationType = document.getOperationType();
                if (null != operationType) {
                    switch (operationType) {
                        case INSERT: {
                            //将业务字段赋予文件表
                            document.setFileDictId(null)
                                    .setObjectId(String.valueOf(null));
                            documentService.updateDocumentById(document);
                            break;
                        }
                        case DELETE: {
                            //删除服务器上文件以及文件表数据
                            documentService.deleteDocumentById(document.getPiisDocId());
                            String filePath = document.getFilePath();
                            if (!StringUtils.isEmpty(filePath)) {
                                FileUploadUtils.deleteServerFile(filePath.replace(filePath, baseFileUrl));
                            }
                            break;
                        }
                    }
                }
            }
        }
        return inspectionAttendanceMapper.updateById(inspectionAttendance);
    }

    /**
     * 根据ID批量删除参会情况
     * @param attendanceIds 参会情况编号
     *
     * @return
     */
    public int deleteByInspectionAttendanceIds(Long[] attendanceIds) {
        List<Long> list = Arrays.asList(attendanceIds);
        return inspectionAttendanceMapper.deleteBatchIds(list);
    }
}
