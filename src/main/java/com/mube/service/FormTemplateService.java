package com.mube.service;

import java.util.List;

import com.mube.model.FormTemplate;

public interface FormTemplateService {
    Iterable<FormTemplate> findAll();

    List<FormTemplate> findByName(String name);
}
