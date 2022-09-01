package com.example.Excercise1.logic.epworkgroup.service;

import com.example.Excercise1.exceptions.FileMaintEPWorkgroupLogicException;
import com.example.Excercise1.logic.epworkgroup.FileMaintEPWorkgroupDao;
import com.example.Excercise1.models.*;
import com.example.Excercise1.persistence.DatabaseMap;
import com.example.Excercise1.persistence.ErrorCodeMap;
import com.example.Excercise1.persistence.MiscMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FileMaintEPWorkgroupLogicImpl implements FileMaintEPWorkgroupLogic{

    private static final Logger log = Logger.getLogger(FileMaintEPWorkgroupLogicImpl.class);
    private static final String GRID_EP_WORKGROUP = "EP Workgroup";

    @Autowired
    private FileMaintEPWorkgroupDao epWorkgroupDao;


    @Override
    public FileMaintEPWorkgroup getFmEPWorkgroup() {
        FileMaintEPWorkgroup fileMaintEPWorkgroup = new FileMaintEPWorkgroup();
        List<Workgroup> epWorkgroups = epWorkgroupDao.getAllEPWorkgroups();
        fileMaintEPWorkgroup.setEpWorkgroups(convertManActivityTypsToUI(epWorkgroups));
        return fileMaintEPWorkgroup;
    }

    private List<EPWorkgroup> convertManActivityTypsToUI(List<Workgroup> workgroups) {
        List<EPWorkgroup> epWorkgroups = new ArrayList<>();
        for (Workgroup workgroup : workgroups) {
            EPWorkgroup epWorkgroup = new EPWorkgroup();
            epWorkgroup.setSeqId(String.valueOf(workgroup.getSeqId()));
            epWorkgroup.setAbbrev(workgroup.getWorkgroupAbbrev());
            epWorkgroup.setDescription(workgroup.getWorkgroupDescr());
            epWorkgroup.setResultCode(ErrorCodeMap.RECORD_FOUND);
            epWorkgroups.add(epWorkgroup);
        }
        return epWorkgroups;
    }

    @Override
    @Transactional(value = "multiTransactionManager", rollbackFor = Exception.class)
    public FileMaintEPWorkgroup saveFmEPWorkgroup(FileMaintEPWorkgroup fmEPWorkgroup) {
        ErrorCode errorCode = new ErrorCode();
        fmEPWorkgroup.setErrorCode(errorCode);
        List<Label> newLabels = new ArrayList<>();
        List<Workgroup> mergedWorkgroups = new ArrayList<>();
        mergeWorkgroups(fmEPWorkgroup, mergedWorkgroups, newLabels);

        if (errorCode.getNumErrors() == 0) {
            for (Label newLabel : newLabels) {
                epWorkgroupDao.setLabel(newLabel);
            }

            for (Workgroup workgroup : mergedWorkgroups) {
                epWorkgroupDao.setEPWorkgroup(workgroup);
            }
        }
        return fmEPWorkgroup;
    }

    private void mergeWorkgroups(FileMaintEPWorkgroup fmEPWorkgroup, List<Workgroup> workgroups, List<Label> labels) {
        List<EPWorkgroup> epWorkgroups = fmEPWorkgroup.getEpWorkgroups();
        ErrorCode errorCode = fmEPWorkgroup.getErrorCode();
        Map<Integer, Workgroup> currentWorkgroups = getCurrentWorkgroups();

        Set<String> abbrevList = new HashSet<>();
        Set<String> descriptionList = new HashSet<>();
        for (int i = 0; i < epWorkgroups.size(); i++) {
            EPWorkgroup epWorkgroup = epWorkgroups.get(i);
            if (shouldBeSkipped(epWorkgroup, abbrevList, descriptionList)) {
                continue;
            }
            validateEPWorkgroup(i + 1, epWorkgroup, abbrevList, descriptionList, errorCode);

            Workgroup workgroup;
            if (ErrorCodeMap.NEW_RECORD == epWorkgroup.getResultCode()) {
                workgroup = new Workgroup(epWorkgroupDao.getNextSequenceFromOracle(DatabaseMap.SEQ_WORKGROUP_SEQ));
                workgroup.setResultCode(ErrorCodeMap.NEW_RECORD);
                workgroup.setWorkgroupAbbrev(epWorkgroup.getAbbrev());
            } else {
                Integer epWorkgroupSeqId = Integer.valueOf(epWorkgroup.getSeqId());
                if (!currentWorkgroups.containsKey(epWorkgroupSeqId)) {
                    throw new FileMaintEPWorkgroupLogicException("Workgroup " + epWorkgroupSeqId + " not found");
                }
                workgroup = currentWorkgroups.get(epWorkgroupSeqId);
                // Delete existing record by setting result code
                if (epWorkgroup.isDeleted()) {
                    workgroup.setResultCode(ErrorCodeMap.DELETED_RECORD);
                }
            }
            workgroup.setWorkgroupDescr(epWorkgroup.getDescription());
            workgroups.add(workgroup);

            // Insert new label record when creating/updating workgroup
            if (workgroup.getResultCode() != ErrorCodeMap.DELETED_RECORD && workgroup.isModified()) {
                Label newLabel = new Label();
                newLabel.setSeqId(epWorkgroupDao.getNextSequenceFromOracle(DatabaseMap.SEQ_LABEL_SEQ));
                newLabel.setAbbrev(epWorkgroup.getAbbrev());
                newLabel.setDescr(epWorkgroup.getDescription());
                newLabel.setLabelTypSeqId(MiscMap.LABEL_TYP_WORKGROUP);
                labels.add(newLabel);
                workgroup.setLabelSeqId(newLabel.getSeqId());
            }
        }
    }

    private boolean shouldBeSkipped(EPWorkgroup epWorkgroup, Set<String> abbrevList, Set<String> descriptionList) {
        String workgroupAbbrev = epWorkgroup.getAbbrev();
        String workgroupDescription = epWorkgroup.getDescription();
        if (epWorkgroup.isDeleted()) {
            if (StringUtils.isNotBlank(workgroupAbbrev)) {
                abbrevList.add(workgroupAbbrev);
            }

            if (StringUtils.isNotBlank(workgroupDescription)) {
                descriptionList.add(workgroupDescription);
            }

            // Skip new record that has been deleted
            if (ErrorCodeMap.NEW_RECORD == epWorkgroup.getResultCode()) {
                return true;
            }
        }
        return false;
    }

    private void validateEPWorkgroup(int rownum, EPWorkgroup epWorkgroup,
                                     Set<String> abbrevList, Set<String> descriptionList, ErrorCode errorCode) {
        if (!epWorkgroup.isDeleted()) {
            String workgroupAbbrev = epWorkgroup.getAbbrev();
            if (StringUtils.isBlank(workgroupAbbrev)) {
                addGridErrorMsg(errorCode, ErrorCodeMap.REQUIRED_EP_WORKGROUP_ID,
                        "Row " + rownum + ": " + ErrorCodeMap.REQUIRED_EP_WORKGROUP_ID_MSG, GRID_EP_WORKGROUP);
            } else if (!abbrevList.add(StringUtils.upperCase(workgroupAbbrev))) {
                addGridErrorMsg(errorCode, ErrorCodeMap.DUPLICATED_EP_WORKGROUP_ID,
                        "Row " + rownum + ": " + String.format(
                                ErrorCodeMap.DUPLICATED_EP_WORKGROUP_ID_MSG, workgroupAbbrev), GRID_EP_WORKGROUP);
            }

            String workgroupDescription = epWorkgroup.getDescription();
            if (StringUtils.isBlank(workgroupDescription)) {
                addGridErrorMsg(errorCode, ErrorCodeMap.REQUIRED_EP_WORKGROUP_DESCRIPTION,
                        "Row " + rownum + ": " + ErrorCodeMap.REQUIRED_EP_WORKGROUP_DESCRIPTION_MSG, GRID_EP_WORKGROUP);
            } else if (!descriptionList.add(StringUtils.upperCase(workgroupDescription))) {
                addGridErrorMsg(errorCode, ErrorCodeMap.DUPLICATED_EP_WORKGROUP_DESCRIPTION,
                        "Row " + rownum + ": " + String.format(ErrorCodeMap.DUPLICATED_EP_WORKGROUP_DESCRIPTION_MSG, workgroupDescription), GRID_EP_WORKGROUP);
            }
        }
    }

    private Map<Integer, Workgroup> getCurrentWorkgroups() {
        List<Workgroup> workgroups = epWorkgroupDao.getAllEPWorkgroups();
        Map<Integer, Workgroup> workgroupMap = new HashMap<>();
        for (Workgroup workgroup : workgroups) {
            workgroupMap.put(workgroup.getSeqId(), workgroup);
        }
        return workgroupMap;
    }


    /**
     * Add grid error
     * @param errorCode Error object to track errors
     * @param code Error code
     * @param shortDesc Short description of error
     * @param gridName Grid name which errors occur on
     */
    private void addGridErrorMsg(ErrorCode errorCode, int code, String shortDesc, String gridName) {
        if (!StringUtils.isEmpty(gridName)) {
            shortDesc = gridName + " - " + shortDesc;
        }
        errorCode.addErrorMsg(code, shortDesc);
    }
}
