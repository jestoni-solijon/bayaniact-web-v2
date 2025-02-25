package com.bayaniact.common.address;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "address_brgys")
public class Brgy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brgy_id")
    private int brgyId;

    @Column(name = "brgy_name", nullable = false, length = 100)
    private String brgyName;

    @Column
    private int population;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "brgy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Street> streets;

    // Getters and setters
    public int getBrgyId() {
        return brgyId;
    }

    public void setBrgyId(int brgyId) {
        this.brgyId = brgyId;
    }

    public String getBrgyName() {
        return brgyName;
    }

    public void setBrgyName(String brgyName) {
        this.brgyName = brgyName;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }
}
