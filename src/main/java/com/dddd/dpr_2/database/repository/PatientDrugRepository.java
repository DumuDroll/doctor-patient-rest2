package com.dddd.dpr_2.database.repository;

import com.dddd.dpr_2.database.models.PatientDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDrugRepository extends JpaRepository<PatientDrug, Long> {

}