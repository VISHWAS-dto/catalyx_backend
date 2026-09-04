package com.catalyx.backend.repository;

import com.catalyx.backend.entity.Catalogue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {

    List<Catalogue> findByBusinessId(Long businessId);

    List<Catalogue> findByAiJobId(Long aiJobId);
}
