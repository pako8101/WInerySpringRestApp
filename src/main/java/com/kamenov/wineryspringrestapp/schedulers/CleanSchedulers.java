package com.kamenov.wineryspringrestapp.schedulers;

import com.kamenov.wineryspringrestapp.service.LogService;
import com.kamenov.wineryspringrestapp.service.WineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanSchedulers {

    private final Logger LOGGER =
            LoggerFactory.getLogger(CleanSchedulers.class);
    private final WineService wineService;
    private final LogService logService;

    public CleanSchedulers(WineService wineService, LogService logService) {
        this.wineService = wineService;
        this.logService = logService;
    }
    @Scheduled(cron = "0 0 23 * * *")
    public void scheduleCleanOldLogs() {
        LOGGER.info(String.format("Deletion scheduled at: %s", System.currentTimeMillis()));

        logService.deleteOldLog();

        LOGGER.info("Deletion done.");

    }
}
