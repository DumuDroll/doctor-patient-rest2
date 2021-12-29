package com.dddd.dpr_2.database.repository;

import com.dddd.dpr_2.database.models.FullInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FullInfoRepository extends JpaRepository<FullInfo, Long> {

}
