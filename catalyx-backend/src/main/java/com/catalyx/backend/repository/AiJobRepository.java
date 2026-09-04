package com.catalyx.backend.repository;

import com.catalyx.backend.entity.AiJob;
import com.catalyx.backend.entity.AiJobStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiJobRepository extends JpaRepository<AiJob, Long> {

    List<AiJob> findByProductId(Long productId);

    List<AiJob> findByStatus(AiJobStatus status);
}
