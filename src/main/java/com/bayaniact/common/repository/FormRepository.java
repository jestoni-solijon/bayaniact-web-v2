package com.bayaniact.common.repository;

import com.bayaniact.common.entity.Form;
import com.bayaniact.common.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {

}
