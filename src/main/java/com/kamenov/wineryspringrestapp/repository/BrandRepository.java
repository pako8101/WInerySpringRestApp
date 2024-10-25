package com.kamenov.wineryspringrestapp.repository;

import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface BrandRepository extends JpaRepository<BrandEntity,Long> {
   @EntityGraph(
           value = "brandWithCategories",
           attributePaths = "categories"
   )
    @Query("select b from  BrandEntity b")
    List<BrandEntity> getAllBrands();

   Optional<BrandEntity> getBrandByCategories(CategoryEntity categories);


    boolean existsByName(String defaultBrand);

    Optional<BrandEntity> findByName(String name);


}
