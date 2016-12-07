package com.hospital.management.repository;

import com.hospital.management.domain.Appointment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Appointment entity.
 */
@SuppressWarnings("unused")
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

}
