package com.bayaniact.common.controller;

import com.bayaniact.common.address.*;
import com.bayaniact.util.enums.ApprovalStatus;
import com.bayaniact.util.medical.MedicalCondition;
import com.bayaniact.util.medical.MedicalConditionService;
import com.bayaniact.util.purpose.CertificationPurpose;
import com.bayaniact.util.purpose.CertificationPurposeService;
import com.bayaniact.common.service.EventService;
import com.bayaniact.common.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired private BrgyRepository brgyRepository;
    @Autowired private StreetRepository streetRepository;
    @Autowired private CityRepository cityRepository;
    @Autowired private ProvinceRepository provinceRepository;
    @Autowired private RegionRepository regionRepository;
    @Autowired private EventService eventService;
    @Autowired private MedicalConditionService medicalConditionService;
    @Autowired private CertificationPurposeService certificationPurposeService;
    @Autowired private ResidentService residentService;

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        // These attributes will be available to all controllers/views
        model.addAttribute("brgys", brgyRepository.findAll());
        model.addAttribute("streets", streetRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("provinces", provinceRepository.findAll());
        model.addAttribute("regions", regionRepository.findAll());
    }

    @ModelAttribute
    public void addRequestToModel(HttpServletRequest request, Model model) {
        model.addAttribute("request", request);
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @ModelAttribute
    public void approvalStatus(Model model) {
        model.addAttribute("approvalStatus", ApprovalStatus.values());
    }

    @ModelAttribute
    public void getAllEvents(Model model) {
        model.addAttribute("activeEvents", eventService.findAllActiveEvents());
    }

    @ModelAttribute
    public void getAllMedicalConditions(Model model) {
        List<MedicalCondition> conditions = medicalConditionService.findAll();

        // Split the list into chunks of 5
        int chunkSize = 5;
        List<List<MedicalCondition>> groupedConditions = new ArrayList<>();
        for (int i = 0; i < conditions.size(); i += chunkSize) {
            groupedConditions.add(conditions.subList(i, Math.min(i + chunkSize, conditions.size())));
        }

        model.addAttribute("groupedConditions", groupedConditions);
    }

    @ModelAttribute
    public void getAllCertificationPurpose(Model model) {
        List<CertificationPurpose> conditions = certificationPurposeService.findAll();

        // Split the list into chunks of 5
        int chunkSize = 5;
        List<List<CertificationPurpose>> groupedConditions = new ArrayList<>();
        for (int i = 0; i < conditions.size(); i += chunkSize) {
            groupedConditions.add(conditions.subList(i, Math.min(i + chunkSize, conditions.size())));
        }

        model.addAttribute("certificationsPurposes", groupedConditions);
    }

    @ModelAttribute
    public void countAllRegisteredResident(Model model) {


        model.addAttribute("residentsCountAll", residentService.countAll());
    }
}
