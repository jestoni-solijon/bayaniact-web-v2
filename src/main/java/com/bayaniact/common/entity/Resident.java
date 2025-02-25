package com.bayaniact.common.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.bayaniact.common.address.*;
import com.bayaniact.util.enums.ApprovalStatus;
import com.bayaniact.common.file.File;
import com.bayaniact.util.medical.MedicalCondition;
import com.bayaniact.util.purpose.CertificationPurpose;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "residents")
public class Resident {

    // --- Fields ---

    /**
     * Unique identifier for the resident.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resident_id")
    private Long residentId;

    /**
     * The user associated with this resident.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * List of files related to the resident.
     */
    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    private List<File> files;

    /**
     * First name of the resident.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Last name of the resident.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Middle name of the resident.
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Date of birth of the resident.
     */
    @NotNull(message = "Birth date is required")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * Gender of the resident.
     */
    @NotBlank(message = "Gender is required")
    @Column(name = "gender")
    private String gender;

    /**
     * Address of the resident.
     */
    @Column(name = "address")
    private String address;

    /**
     * Contact number of the resident.
     */
    @Column(name = "contact_number")
    private String contactNumber;

    /**
     * Email address of the resident.
     */
    @NotBlank(message = "Email address is required")
    @Column(name = "email")
    private String email;

    /**
     * Valid ID of the resident.
     */
    @Column(name = "valid_id")
    private String validId;

    /**
     * Valid ID number of the resident.
     */
    @Column(name = "valid_id_number")
    private String validIdNumber;

    /**
     * Birthplace of the resident.
     */
    @NotBlank(message = "Birth place is required")
    @Column(name = "birth_place")
    private String birthPlace;

    /**
     * Affiliation of the resident.
     */
    @Column(name = "affiliation")
    private String affiliation;

    /**
     * Nationality of the resident.
     */
    @NotBlank(message = "Nationality is required")
    @Column(name = "nationality")
    private String nationality;

    /**
     * Occupation of the resident.
     */
    @Column(name = "occupation")
    private String occupation;

    /**
     * Approval status of the resident.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApprovalStatus status;

    /**
     * Indicates if the resident owns a car.
     */
    @Column(name = "car_owner")
    private String carOwner;

    /**
     * Indicates if the resident owns a pet.
     */
    @Column(name = "pet_owner")
    private String petOwner;

    /**
     * Indicates if the resident owns a garage.
     */
    @Column(name = "garage_owner")
    private String garageOwner;

    /**
     * Barangay associated with the resident.
     */
    @ManyToOne
    @JoinColumn(name = "brgy_id", referencedColumnName = "brgy_id")
    private Brgy brgy;

    /**
     * Street associated with the resident.
     */
    @ManyToOne
    @JoinColumn(name = "street_id", referencedColumnName = "street_id")
    private Street street;

    /**
     * City associated with the resident.
     */
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private City city;

    /**
     * Province associated with the resident.
     */
    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "province_id")
    private Province province;

    /**
     * Region associated with the resident.
     */
    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    private Region region;

    /**
     * Medical history of the resident.
     */
    @ManyToMany
    @JoinTable(
            name = "resident_medical_history",
            joinColumns = @JoinColumn(name = "resident_id"),
            inverseJoinColumns = @JoinColumn(name = "condition_id")
    )
    private List<MedicalCondition> medicalHistory = new ArrayList<>();

    /**
     * Certification purposes associated with the resident.
     */
    @ManyToMany
    @JoinTable(
            name = "resident_certification_purposes",
            joinColumns = @JoinColumn(name = "resident_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id")
    )
    private List<CertificationPurpose> certificationPurpose = new ArrayList<>();

    /*
    * Age of the resident
    * */
    @Column(name = "age")
    private String age;

    // --- Constructors ---


    // --- Getters and Setters ---

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValidId() {
        return validId;
    }

    public void setValidId(String validId) {
        this.validId = validId;
    }

    public String getValidIdNumber() {
        return validIdNumber;
    }

    public void setValidIdNumber(String validIdNumber) {
        this.validIdNumber = validIdNumber;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(String petOwner) {
        this.petOwner = petOwner;
    }

    public String getGarageOwner() {
        return garageOwner;
    }

    public void setGarageOwner(String garageOwner) {
        this.garageOwner = garageOwner;
    }

    public Brgy getBrgy() {
        return brgy;
    }

    public void setBrgy(Brgy brgy) {
        this.brgy = brgy;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<MedicalCondition> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<MedicalCondition> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<CertificationPurpose> getCertificationPurpose() {
        return certificationPurpose;
    }

    public void setCertificationPurpose(List<CertificationPurpose> certificationPurpose) {
        this.certificationPurpose = certificationPurpose;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBase64Image() {
        if (files != null && !files.isEmpty()) {
            byte[] imageData = files.get(0).getData(); // Assuming one image per official
            return "data:" + files.get(0).getFileType() + ";base64," + Base64.getEncoder().encodeToString(imageData);
        }
        return null;
    }
}
