package com.example.Excercise1.logic.epworkgroup;

import com.example.Excercise1.dao.EpWorkgroupDAO;
import com.example.Excercise1.models.Label;
import com.example.Excercise1.models.Workgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FileMaintEPWorkgroupDaoImpl implements FileMaintEPWorkgroupDao {


    @Autowired
    private EpWorkgroupDAO epWorkgroupDAO;



    @Override
    public int getNextSequenceFromOracle(String seqName) {
        return epWorkgroupDAO.getNextSequenceFromOracle(seqName);
    }

    @Override
    public List<Workgroup> getAllEPWorkgroups() {
        List<Workgroup> allEPWorkgroups = epWorkgroupDAO.getAllWorkgroups();
        List<Workgroup> epWorkgroups = new ArrayList<>();
        for (Workgroup epWorkgroup : allEPWorkgroups) {
            if (epWorkgroup.getSeqId() > 0) {
                epWorkgroups.add(epWorkgroup);
            }
        }
        return epWorkgroups;
    }

    @Override
    public void setEPWorkgroup(Workgroup workgroup) {
        epWorkgroupDAO.setWorkgroup(workgroup);
    }

    @Override
    public void setLabel(Label label) {
        epWorkgroupDAO.setLabel(label);
    }
}
