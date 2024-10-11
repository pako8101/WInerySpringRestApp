package com.kamenov.wineryspringrestapp.repository;

import com.kamenov.wineryspringrestapp.models.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {
    @Transactional
    @Modifying
    @Query("delete from LogEntity as l where l.appearanceTime < :deleteBefore")
    void deleteOldLogs(Instant deleteBefore);
}
