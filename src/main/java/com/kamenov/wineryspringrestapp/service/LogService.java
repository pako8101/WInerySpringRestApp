package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.service.LogServiceModel;

import java.util.List;

public interface LogService {

    void createLog(String action,Long wineId);

    List<LogServiceModel> findAllLogs();

    void deleteOldLog();

}
