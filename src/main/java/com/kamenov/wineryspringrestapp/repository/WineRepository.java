package com.kamenov.wineryspringrestapp.repository;

import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import com.kamenov.wineryspringrestapp.models.view.WIneViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineRepository extends JpaRepository<WineEntity,Long> {

    List<WineEntity> findAllByCategory(CategoryEnum category_name);

    WIneViewModel findWineById(Long id);
}
