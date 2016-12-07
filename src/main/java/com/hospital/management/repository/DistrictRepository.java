package com.hospital.management.repository;

import com.hospital.management.domain.District;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the District entity.
 */
@SuppressWarnings("unused")
public interface DistrictRepository extends JpaRepository<District,Long> {

}
