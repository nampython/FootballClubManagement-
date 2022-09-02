package com.example.Excercise1.logic.epworkgroup;

import com.example.Excercise1.models.Label;
import com.example.Excercise1.models.Workgroup;

import java.util.List;

public interface FileMaintEPWorkgroupDao {
    /**
     * Get the next sequence
     * @param seqName Sequence name
     * @return the next sequence
     */
    int getNextSequenceFromOracle(String seqName);

    /**
     * Get all EP Workgroups from database
     *
     * @return List that contains entries from WORKGROUP table
     */
    List<Workgroup> getAllEPWorkgroups();

    /**
     * Set EP Workgroup object
     *
     * @param workgroup
     */
    void setEPWorkgroup(Workgroup workgroup);

    /**
     * Set Label object
     *
     * @param label
     */
    void setLabel(Label label);
}
