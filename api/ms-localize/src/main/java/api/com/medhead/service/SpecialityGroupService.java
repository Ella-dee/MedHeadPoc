package api.com.medhead.service;

import api.com.medhead.model.Speciality;
import api.com.medhead.model.SpecialityGroup;
import api.com.medhead.repository.SpecialityGroupRepository;
import api.com.medhead.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityGroupService {

    @Autowired
    private SpecialityGroupRepository specialityGroupRepository;

    public SpecialityGroup findByName(String name){
       return  specialityGroupRepository.findByName(name);
    }
    public List<SpecialityGroup> getAllSpecialityGroups() {
        return specialityGroupRepository.findAll();
    }
}

