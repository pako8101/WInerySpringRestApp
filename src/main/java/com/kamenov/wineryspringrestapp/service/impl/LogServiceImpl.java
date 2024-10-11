package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.LogEntity;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.service.LogServiceModel;
import com.kamenov.wineryspringrestapp.repository.LogRepository;
import com.kamenov.wineryspringrestapp.service.LogService;
import com.kamenov.wineryspringrestapp.service.UserService;
import com.kamenov.wineryspringrestapp.service.WineService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Instant.now;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);
    private final WineService wineService;
    private final Period deleteTime;

    public LogServiceImpl(LogRepository logRepository,
                          UserService userService,
                          ModelMapper modelMapper,
                          WineService wineService,
                          @Value("P30D") Period deleteTime) {
        this.logRepository = logRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.wineService = wineService;
        this.deleteTime = deleteTime;
    }


    @Override
    public void createLog(String action, Long wineId) {
        WineEntity wine = wineService.findWineById(wineId);

        Authentication authentication =
                SecurityContextHolder
                        .getContext().getAuthentication();

        String username = authentication.getName();
        UserEntity user = userService.findByName(username);
        LogEntity logEntity = new LogEntity()
                .setWine(wine)
                .setUser(user)
                .setAction(action)
                .setTime(LocalDateTime.now());

        logRepository.save(logEntity);
    }

    @Override
    public List<LogServiceModel> findAllLogs() {
        return logRepository
                .findAll()
                .stream()
                .map(logEntity -> {
                    LogServiceModel logServiceModel =
                            modelMapper.map(logEntity, LogServiceModel.class);
              logServiceModel.setWine(logServiceModel.getWine())
                      .setUser(logEntity.getUser().getUsername());
              return logServiceModel;
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteOldLog() {
        Instant now = now();
        Instant deleteBefore = now.minus(deleteTime);

        LOGGER.info("Deleting old logs older than " + deleteBefore);
        logRepository.deleteOldLogs(deleteBefore);
        LOGGER.info("Old logs will be determined by " + now);
    }
}
