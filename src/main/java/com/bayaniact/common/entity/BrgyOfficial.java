package com.bayaniact.common.entity;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import com.bayaniact.common.address.*;
import com.bayaniact.common.file.BrgyOfficialFile;
import com.bayaniact.common.file.File;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "brgy_officials")
public class BrgyOfficial {

    @Id
    @Column(name = "brgy_official_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brgyOfficialId;

    @NotBlank(message = "First Name is required")
    @Size(max = 20, min = 1, message = "Firstname must be less than 20 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(max = 20, min = 1, message = "Lastname must be less than 20 characters")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "suffix")
    private String suffix;

    @NotBlank(message = "Position is required")
    @Column(name = "position")
    private String position;

    @NotBlank(message = "Contact Number is required")
    @Column(name = "contact_number")
    private String contactNumber;

    @NotBlank(message = "Email Address is required")
    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "term_start")
    private LocalDate termStart;

    @Column(name = "term_end")
    private LocalDate termEnd;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "brgyOfficial", cascade = CascadeType.ALL)
    private List<BrgyOfficialFile> brgyOfficialFiles;

    @Column(name = "sex")
    private String sex;

    @Column(name = "age")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "brgy_id", referencedColumnName = "brgy_id")
    private Brgy brgy;

    @ManyToOne
    @JoinColumn(name = "street_id", referencedColumnName = "street_id")
    private Street street;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    private Region region;

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

    public String getBase64Image() {
        if (brgyOfficialFiles != null && !brgyOfficialFiles.isEmpty()) {
            byte[] imageData = brgyOfficialFiles.get(0).getData(); // Assuming one image per official
            return "data:" + brgyOfficialFiles.get(0).getFileType() + ";base64," + Base64.getEncoder().encodeToString(imageData);
        }
        return null;
    }

    public List<BrgyOfficialFile> getBrgyOfficialFiles() {
        return brgyOfficialFiles;
    }

    public void setBrgyOfficialFiles(List<BrgyOfficialFile> brgyOfficialFiles) {
        this.brgyOfficialFiles = brgyOfficialFiles;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the brgyOfficialId
     */
    public Long getBrgyOfficialId() {
        return brgyOfficialId;
    }

    /**
     * @param brgyOfficialId the brgyOfficialId to set
     */
    public void setBrgyOfficialId(Long brgyOfficialId) {
        this.brgyOfficialId = brgyOfficialId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return the email
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param email the email to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the termStart
     */
    public LocalDate getTermStart() {
        return termStart;
    }

    /**
     * @param termStart the termStart to set
     */
    public void setTermStart(LocalDate termStart) {
        this.termStart = termStart;
    }

    /**
     * @return the termEnd
     */
    public LocalDate getTermEnd() {
        return termEnd;
    }

    /**
     * @param termEnd the termEnd to set
     */
    public void setTermEnd(LocalDate termEnd) {
        this.termEnd = termEnd;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
