package com.catalyx.backend.repository;

import com.catalyx.backend.entity.Business;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> findByUserId(Long userId);
}
