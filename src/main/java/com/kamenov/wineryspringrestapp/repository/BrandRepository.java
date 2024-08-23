package com.kamenov.wineryspringrestapp.repository;

import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity,Long> {
   @EntityGraph(
           value = "brandWithCategories",
           attributePaths = "categories"
   )
    @Query("select b from  BrandEntity b")
    Optional<BrandEntity> getAllBrands();
}
