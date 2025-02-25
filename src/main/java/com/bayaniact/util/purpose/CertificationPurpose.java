package com.bayaniact.util.purpose;

import jakarta.persistence.*;

@Entity
@Table(name = "certification_purposes")
public class CertificationPurpose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purpose_id")
    private Integer id;

    @Column(name = "purpose_name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
