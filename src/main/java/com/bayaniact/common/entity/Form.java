package com.bayaniact.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "forms")
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Long formId;

    @NotBlank(message = "Form name is required")
    @Column(name = "form_name")
    private String formName;

    @NotBlank(message = "Form type is required")
    @Column(name = "form_type")
    private String formType;

    @NotBlank(message = "Description is required")
    @Column(name = "form_desc")
    private String formDesc;

    @NotNull(message = "Price is required")
    @Column(name = "form_price")
    private Double formPrice;

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getFormDesc() {
        return formDesc;
    }

    public void setFormDesc(String formDesc) {
        this.formDesc = formDesc;
    }

    public Double getFormPrice() {
        return formPrice;
    }

    public void setFormPrice(Double formPrice) {
        this.formPrice = formPrice;
    }
}
