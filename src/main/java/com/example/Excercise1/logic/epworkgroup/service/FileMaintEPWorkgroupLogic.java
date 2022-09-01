package com.example.Excercise1.logic.epworkgroup.service;

import com.example.Excercise1.models.FileMaintEPWorkgroup;

public interface FileMaintEPWorkgroupLogic {
    /**
     * Get EP Workgroup data
     *
     * @return {@link FileMaintEPWorkgroup}
     */
    FileMaintEPWorkgroup getFmEPWorkgroup();

    /**
     * Save all EP Workgroups
     *
     * @param fmEPWorkgroup EP Workgroup data
     * @return {@link FileMaintEPWorkgroup}
     */
    FileMaintEPWorkgroup saveFmEPWorkgroup(FileMaintEPWorkgroup fmEPWorkgroup);
}
