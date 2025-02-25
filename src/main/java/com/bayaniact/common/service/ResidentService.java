package com.bayaniact.common.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.bayaniact.common.email.EmailService;
import com.bayaniact.common.entity.Resident;
import com.bayaniact.common.entity.Role;
import com.bayaniact.common.entity.User;
import com.bayaniact.util.enums.ApprovalStatus;
import com.bayaniact.common.repository.ResidentRepository;
import com.bayaniact.common.security.RoleDao;
import com.bayaniact.common.security.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResidentService {

    @Autowired private ResidentRepository residentRepository;
    @Autowired private UserService userService;
    @Autowired private RoleDao roleDao;
    @Autowired private EmailService emailService;

    /**
     * Save a new resident or update an existing resident.
     *
     * @param resident the resident entity to save
     * @return the saved resident
     */
    public Resident save(Resident resident) {
        return residentRepository.save(resident); // Save the resident to the database
    }

    /**
     * Update an existing resident's details.
     *
     * @param resident the resident entity with updated details
     */
    public void update(Resident resident) {
        residentRepository.save(resident); // Save the updated resident to the database
    }

    /**
     * Find all residents with pagination support.
     *
     * @param pageable pagination information
     * @return a page of residents
     */
    public Page<Resident> findAll(Pageable pageable) {
        return residentRepository.findAll(pageable); // Retrieve all residents with pagination
    }

    /**
     * Find a resident by their ID.
     *
     * @param residentId the ID of the resident to find
     * @return an Optional containing the resident, if found
     */
    public Optional<Resident> findById(Long residentId) {
        return residentRepository.findById(residentId); // Find a resident by their ID
    }

    /**
     * Find residents by their last name with pagination support.
     *
     * @param lastName the last name to search for
     * @param pageRequest pagination information
     * @return a page of residents with matching last names
     */
    public Page<Resident> findByLastName(String lastName, PageRequest pageRequest) {
        return residentRepository.findByLastName(lastName, pageRequest); // Find residents by last name
    }

    /**
     * Check if a resident exists by their ID.
     *
     * @param residentId the ID of the resident
     * @return true if the resident exists, false otherwise
     */
    public boolean existsResidentById(Long residentId) {
        return residentRepository.existsByResidentId(residentId); // Check if a resident exists by ID
    }

    /**
     * Delete a resident by their ID.
     *
     * @param residentId the ID of the resident to delete
     */
    public void deleteResidentById(Long residentId) {
        residentRepository.deleteById(residentId); // Delete the resident by their ID
    }

    /**
     * Delete multiple residents by their IDs.
     *
     * @param residentIds a list of resident IDs to delete
     */
    public void deleteResidents(List<Long> residentIds) {
        residentRepository.deleteAllById(residentIds); // Delete residents by their IDs
    }

    /**
     * Find residents by multiple filters with pagination support.
     *
     * @param firstName the first name to filter by
     * @param lastName the last name to filter by
     * @param status the status to filter by
     * @param middleName the middle name to filter by
     * @param birthDate the birthdate to filter by
     * @param pageable pagination information
     * @return a page of residents matching the filters
     */
    public Page<Resident> findByFilters(String firstName, String lastName, String status, String middleName, LocalDate birthDate, Pageable pageable) {
        return residentRepository.findByFilters(firstName, lastName, status, middleName, birthDate, pageable); // Find residents by filters
    }

    /**
     * Find a resident by their UserUUID.
     *
     * @param userUUID the UUID of the user to search for
     * @return the resident associated with the provided UserUUID
     */
    public Resident findByUserUUID(String userUUID) {
        return residentRepository.findByUser_UserUUID(userUUID); // Find a resident by their UserUUID
    }

    /**
     * Update the status of a resident.
     *
     * @param residentId the ID of the resident to update
     * @param newStatus the new status to set for the resident
     */
    public void updateStatus(Long residentId, ApprovalStatus newStatus, String reason) throws MessagingException {
        // Find the resident by ID
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new IllegalArgumentException("Resident not found with ID: " + residentId));

        // Update the resident's status to the new status
        resident.setStatus(newStatus);

        // Save the updated resident entity to the database
        residentRepository.save(resident);

        // If the status is 'APPROVED', update the user's role
        if (newStatus == ApprovalStatus.APPROVED) {
            // Assuming Resident has a relationship with User
            User user = resident.getUser();

            // Find the role for 'RESIDENT'
            Role residentRole = roleDao.findRoleByName("ROLE_RESIDENT");
            if (residentRole == null) {
                throw new IllegalArgumentException("Role RESIDENT not found");
            }

            // Find the existing role 'FOR_APPROVAL_RESIDENT' in the users_roles table
            Role approvalRole = roleDao.findRoleByName("ROLE_FOR_APPROVAL_RESIDENT");
            if (approvalRole == null) {
                throw new IllegalArgumentException("Role FOR_APPROVAL_RESIDENT not found");
            }

            // Update the user's role in the users_roles table
            user.getRoles().remove(approvalRole);  // Remove the 'FOR_APPROVAL_RESIDENT' role
            user.getRoles().add(residentRole);    // Add the 'RESIDENT' role

            // Save the user with updated roles
            userService.save(user);

            // Send the notification to resident
            emailService.sendApprovedResident(user);
        }
        // If the status is 'DECLINED', only update the resident's status, but do not change roles
        else if (newStatus == ApprovalStatus.DECLINED) {

            User user = resident.getUser();

            emailService.sendDeclinedResident(user, reason);
        }
    }

    /**
     * Counts the total number of residents in the database.
     *
     * This method calls the count() method from the residentRepository,
     * which is assumed to extend JpaRepository, to return the total number
     * of resident records in the database.
     *
     * @return the total count of residents
     */
    public long countAll() {
        // Call the count() method from the residentRepository to get the total count of residents
        return residentRepository.count();  // Assuming you have a residentRepository that extends JpaRepository
    }


}
