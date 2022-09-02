package com.example.Excercise1.dao;

import com.example.Excercise1.domain.EpWorkGroupUserAssignmentInfo;
import com.example.Excercise1.exceptions.XifinDataNotFoundException;
import com.example.Excercise1.models.*;

import java.util.List;

public interface EpWorkgroupDAO {
    /**
     * Return label object by abbrev
     * @param abbrev abbrev
     * @return Label object
     * @throws XifinDataNotFoundException data not found
     */
    Label getLabelByAbbrev(String abbrev) throws XifinDataNotFoundException;

    /**
     * Return workgroup by Seq Id
     * @param seqId seq Id
     * @return Workgroup
     * @throws XifinDataNotFoundException data not found
     */
    Workgroup getWorkGroupBySeqId(int seqId) throws XifinDataNotFoundException;

    /**
     * Set Accn Label
     * @param accnLabel accnLabel
     */
    void setAccnLabel(AccnLabel accnLabel);

    /**
     * Get all EP Workgroups
     *
     * @return List that contains entries from WORKGROUP table
     */
    List<Workgroup> getAllWorkgroups();

    /**
     *  Set Workgroup
     * @param workgroup
     */
    void setWorkgroup(Workgroup workgroup);

    /**
     *  Set Label
     * @param label
     */
    void setLabel(Label label);

    /**
     * Get all Workgroup Saved Search Assignments
     *
     * @return List of WORKGROUP_SAVED_SRCH_ASSIGNMENT
     */
    List<WorkgroupSavedSrchAssignment> getAllWorkgroupSavedSrchAssignments();

    /**
     * Set Workgroup Saved Search Assignment
     *
     * @param workgroupSavedSrchAssignment Workgroup Saved Search Assignment
     */
    void setWorkgroupSavedSrchAssignment(WorkgroupSavedSrchAssignment workgroupSavedSrchAssignment);

    /**
     * Get Workgroup User Assignments by workgroup id
     *
     * @param workgroupId workgroup id
     * @return List of WORKGROUP_USER_ASSIGNMENT
     */
    List<WorkgroupUserAssignment> getWorkgroupUserAssignments(int workgroupId);

    /**
     * Set Workgroup User Assignment
     *
     * @param workgroupUserAssignment Workgroup User Assignment
     */
    void setWorkgroupUserAssignment(WorkgroupUserAssignment workgroupUserAssignment);

    List<EpWorkGroupUserAssignmentInfo> getEpWorkGroupUserAssignmentInfoList(String userId);

    int getNextSequenceFromOracle(String seqName);
}
