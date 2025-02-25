package com.bayaniact.common.address;

import jakarta.persistence.*;

@Entity
@Table(name = "address_streets")
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "street_id")
    private int streetId;

    @Column(name = "street_name", nullable = false, length = 100)
    private String streetName;

    @ManyToOne
    @JoinColumn(name = "brgy_id", nullable = false)
    private Brgy brgy;

    // Getters and setters
    public int getStreetId() {
        return streetId;
    }

    public void setStreetId(int streetId) {
        this.streetId = streetId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Brgy getBrgy() {
        return brgy;
    }

    public void setBrgy(Brgy brgy) {
        this.brgy = brgy;
    }
}

