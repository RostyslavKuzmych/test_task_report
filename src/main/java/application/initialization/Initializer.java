package application.initialization;

import application.service.JsonReaderService;
import application.service.UpdateDbService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initializer {
    private final UpdateDbService updateDbService;
    private final JsonReaderService jsonReaderService;

    @PostConstruct
    @Scheduled(cron = "*/15 * * * *")
    private void addAllReportsToDb() {
        updateDbService.addNewOrUpdatedReportsToDb(jsonReaderService.readFile());
    }
}
