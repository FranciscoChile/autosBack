package com.mube.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mube.model.FormTemplate;
import com.mube.repository.FormTemplateRepository;

@Service
public class FormTemplateServiceImpl implements FormTemplateService {

    @Autowired
    private FormTemplateRepository ftRepository;

    public Iterable<FormTemplate> findAll() {
        return ftRepository.findAll();
    }

    public List<FormTemplate> findByName(String name) {
        return ftRepository.findByName(name);
    }
}
