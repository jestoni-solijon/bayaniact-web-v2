package com.bayaniact.util.medical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalConditionService {
    @Autowired
    private MedicalConditionRepository repository;

    public List<MedicalCondition> findAll() {
        return repository.findAll();
    }

    public List<MedicalCondition> findByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }
}
