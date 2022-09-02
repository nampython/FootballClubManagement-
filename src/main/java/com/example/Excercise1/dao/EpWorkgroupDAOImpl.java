package com.example.Excercise1.dao;


import com.example.Excercise1.MarDao.MarsDaoManager;
import com.example.Excercise1.domain.EpWorkGroupUserAssignmentInfo;
import com.example.Excercise1.exceptions.XifinDataAccessFailureException;
import com.example.Excercise1.exceptions.XifinDataNotFoundException;
import com.example.Excercise1.models.*;
import com.example.Excercise1.persistence.DatabaseMap;
import com.example.Excercise1.utility.Money;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EpWorkgroupDAOImpl implements EpWorkgroupDAO {


    @Autowired
    private MarsDaoManager marsDaoManager;

    private static final Logger log = Logger.getLogger(EpWorkgroupDAOImpl.class);


    private static final String GET_LABEL_BY_ABBREV = "select * from " + DatabaseMap.TBL_LABEL + " where " + DatabaseMap.FLD_LABEL_ABBREV + " = ?";

    private static final String GET_WORKGROUP_BY_SEQ_ID = "select * from " + DatabaseMap.TBL_WORKGROUP + " where " + DatabaseMap.FLD_WORKGROUP_SEQ_ID + " = ?";

    /*
     * SELECT * FROM WORKGROUP ORDER BY WORKGROUP_ABBREV
     */
    private static final String GET_ALL_WORKGROUPS =
            "select * from " + DatabaseMap.TBL_WORKGROUP + " ORDER BY " + DatabaseMap.FLD_WORKGROUP_WORKGROUP_ABBREV;

    /*
     * SELECT * FROM WORKGROUP_SAVED_SRCH_ASSIGNMENT ORDER BY PRIORITY
     */
    private static final String GET_ALL_WORKGROUP_SAVED_SRCH_ASSIGNMENTS =
            "select * from " + DatabaseMap.TBL_WORKGROUP_SAVED_SRCH_ASSIGNMENT + " order by " + DatabaseMap.FLD_WORKGROUP_SAVED_SRCH_ASSIGNMENT_PRIORITY;/*

     /*
     * SELECT * FROM WORKGROUP_USER_ASSIGNMENT WHERE FK_WORKGROUP_SEQ_ID = ? ORDER BY VK_USER_ID
     */
    private static final String GET_WORKGROUP_USER_ASSIGNMENTS_BY_WORKGROUP_ID =
            "select * from " + DatabaseMap.TBL_WORKGROUP_USER_ASSIGNMENT
                    + " where " + DatabaseMap.FLD_WORKGROUP_USER_ASSIGNMENT_WORKGROUP_SEQ_ID + " = ? order by " + DatabaseMap.FLD_WORKGROUP_USER_ASSIGNMENT_USER_ID;


    @Override
    public Label getLabelByAbbrev(String abbrev) throws XifinDataNotFoundException {
        try {
            List<String> params = new ArrayList<>();
            params.add(abbrev);
            return marsDaoManager.getValueObject(GET_LABEL_BY_ABBREV, params, Label.class);
        } catch (XifinDataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to retrieve label by Description, abbrev=" + abbrev, e);
        }
    }

    @Override
    public Workgroup getWorkGroupBySeqId(int seqId) throws XifinDataNotFoundException {
        List<Integer> params = new ArrayList<>();
        params.add(seqId);
        try {
            return marsDaoManager.getValueObject(GET_WORKGROUP_BY_SEQ_ID, params, Workgroup.class);
        } catch (XifinDataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to retrieve Workgroup by seqId, seqId=" + seqId, e);
        }
    }

    @Override
    public void setAccnLabel(AccnLabel accnLabel) {
        marsDaoManager.setValueObject(accnLabel);
    }

    @Override
    public List<Workgroup> getAllWorkgroups() {
        try {
            return marsDaoManager.getValueObjects(GET_ALL_WORKGROUPS, Workgroup.class);
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to retrieve all Workgroups", e);
        }
    }

    @Override
    public void setWorkgroup(Workgroup workgroup) {
        try {
            marsDaoManager.setValueObject(workgroup);
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to save workgroup, object = " + workgroup, e);
        }
    }

    @Override
    public void setLabel(Label label) {
        try {
            marsDaoManager.setValueObject(label);
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to save label, object = " + label, e);
        }
    }

    @Override
    public List<WorkgroupSavedSrchAssignment> getAllWorkgroupSavedSrchAssignments() {
        try {
            return marsDaoManager.getValueObjects(GET_ALL_WORKGROUP_SAVED_SRCH_ASSIGNMENTS, WorkgroupSavedSrchAssignment.class);
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to retrieve all Workgroup Saved Srch Assignments", e);
        }
    }

    @Override
    public void setWorkgroupSavedSrchAssignment(WorkgroupSavedSrchAssignment workgroupSavedSrchAssignment) {
        try {
            marsDaoManager.setValueObject(workgroupSavedSrchAssignment);
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to save Workgroup Saved Srch Assignment, object = " + workgroupSavedSrchAssignment, e);
        }
    }

    @Override
    public List<WorkgroupUserAssignment> getWorkgroupUserAssignments(int workgroupId) {
        try {
            List<Integer> params = new ArrayList<>();
            params.add(workgroupId);
            return marsDaoManager.getValueObjects(GET_WORKGROUP_USER_ASSIGNMENTS_BY_WORKGROUP_ID, params, WorkgroupUserAssignment.class);
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to retrieve all Workgroup User Assignments", e);
        }
    }

    @Override
    public void setWorkgroupUserAssignment(WorkgroupUserAssignment workgroupUserAssignment) {
        try {
            marsDaoManager.setValueObject(workgroupUserAssignment);
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to save Workgroup User Assignment, object = " + workgroupUserAssignment, e);
        }
    }

    @Override
    public List<EpWorkGroupUserAssignmentInfo> getEpWorkGroupUserAssignmentInfoList(String userId) {
        //Order by WorkGroup Id and Priority Id
        List<EpWorkGroupUserAssignmentInfo> epWorkGroupUserAssignmentInfoList = new ArrayList<>();
        EpWorkGroupUserAssignmentInfo epWorkGroupUserAssignmentInfo = new EpWorkGroupUserAssignmentInfo();
        epWorkGroupUserAssignmentInfo.setAccnCnt(10);
        epWorkGroupUserAssignmentInfo.setDueAmt(new BigDecimal(100.00));
        epWorkGroupUserAssignmentInfo.setDueAmtAsMoney(new Money(100.00));
        epWorkGroupUserAssignmentInfo.setErrorCnt(10);
        epWorkGroupUserAssignmentInfo.setNewAccnCnt(1);
        epWorkGroupUserAssignmentInfo.setNewErrCnt(5);
        epWorkGroupUserAssignmentInfo.setWorkgroupId(2);
        epWorkGroupUserAssignmentInfo.setWorkGroupAbbrev("WorkGroupAbbrev1");
        epWorkGroupUserAssignmentInfo.setPriority(1);
        epWorkGroupUserAssignmentInfo.setPriorityAbbrev("HIGH");
        epWorkGroupUserAssignmentInfo.setSuperVisorId("Supervisor");
        epWorkGroupUserAssignmentInfo.setUserId("xlodapally");
        epWorkGroupUserAssignmentInfoList.add(epWorkGroupUserAssignmentInfo);

        EpWorkGroupUserAssignmentInfo epWorkGroupUserAssignmentInfo1 = new EpWorkGroupUserAssignmentInfo();
        epWorkGroupUserAssignmentInfo1.setAccnCnt(10);
        epWorkGroupUserAssignmentInfo1.setDueAmt(new BigDecimal(101.00));
        epWorkGroupUserAssignmentInfo1.setDueAmtAsMoney(new Money(102.00));
        epWorkGroupUserAssignmentInfo1.setErrorCnt(12);
        epWorkGroupUserAssignmentInfo1.setNewAccnCnt(13);
        epWorkGroupUserAssignmentInfo1.setNewErrCnt(5);
        epWorkGroupUserAssignmentInfo1.setWorkgroupId(2);
        epWorkGroupUserAssignmentInfo1.setWorkGroupAbbrev("WorkGroupAbbrev2");
        epWorkGroupUserAssignmentInfo1.setPriority(2);
        epWorkGroupUserAssignmentInfo1.setPriorityAbbrev("LOW");
        epWorkGroupUserAssignmentInfo1.setSuperVisorId("Supervisor");
        epWorkGroupUserAssignmentInfo1.setUserId("xlodapally");
        epWorkGroupUserAssignmentInfoList.add(epWorkGroupUserAssignmentInfo1);

        return epWorkGroupUserAssignmentInfoList;
    }

    @Override
    public int getNextSequenceFromOracle(String seqName) {
        try {
            marsDaoManager.getNextSequenceFromOracle(seqName);
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to save Workgroup User Assignment, object = ", e);
        }
    }
}
