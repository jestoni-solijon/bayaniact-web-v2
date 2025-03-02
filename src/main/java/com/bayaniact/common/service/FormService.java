package com.bayaniact.common.service;

import com.bayaniact.common.entity.Form;
import com.bayaniact.common.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    @Autowired private FormRepository formRepository;

    public void save(Form form) {
        formRepository.save(form);
    }

    public Page<Form> findAllIncident(Pageable pageable) {
        return formRepository.findAll(pageable);
    }

    public Optional<Form> findById(Long formId) {
        return formRepository.findById(formId);
    }

    public void deleteById(List<Long> formId) {
        formRepository.deleteAllById(formId);
    }

    public List<Form> findAll() {
        return formRepository.findAll();
    }
}