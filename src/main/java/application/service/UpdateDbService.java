package application.service;

import application.model.Report;

public interface UpdateDbService {
    void addNewOrUpdatedReportsToDb(Report report);
}
