package com.mube.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mube.model.FormTemplate;
import com.mube.service.FormTemplateService;

@RestController
@RequestMapping("/api/formTemplate")
public class FormTemplateController {
    
    @Autowired
    private FormTemplateService ftService;

    @GetMapping
    public Iterable<FormTemplate> findAll() {       
        try { 
            return ftService.findAll();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public List<FormTemplate> findByBrand(@PathVariable String name) {
        try {
            return ftService.findByName(name);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
