package com.hospital.management.repository;

import com.hospital.management.domain.State;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the State entity.
 */
@SuppressWarnings("unused")
public interface StateRepository extends JpaRepository<State,Long> {

}
