package com.bayaniact.common.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "blotter")
public class Blotter {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long blotterId;

    @Column(name = "blotter_what")
    private String what;

    @Column(name = "blotter_where")
    private String where;

    @Column(name = "blotter_when")
    private LocalDateTime when;

    @Column(name = "accused_first_name")
    private String accusedFirstName;

    @Column(name = "accused_last_name")
    private String accusedLastName;

    @Column(name = "accused_middle_name")
    private String accusedMiddleName;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = true)
    private User user;

    @Column(name = "address")
    private String address;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public Long getBlotterId() {
        return blotterId;
    }

    public void setBlotterId(Long blotterId) {
        this.blotterId = blotterId;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }

    public String getAccusedFirstName() {
        return accusedFirstName;
    }

    public void setAccusedFirstName(String accusedFirstName) {
        this.accusedFirstName = accusedFirstName;
    }

    public String getAccusedLastName() {
        return accusedLastName;
    }

    public void setAccusedLastName(String accusedLastName) {
        this.accusedLastName = accusedLastName;
    }

    public String getAccusedMiddleName() {
        return accusedMiddleName;
    }

    public void setAccusedMiddleName(String accusedMiddleName) {
        this.accusedMiddleName = accusedMiddleName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
