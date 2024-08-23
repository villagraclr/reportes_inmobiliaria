package cl.desafiolatam.rest.services;

import java.util.List;
import cl.desafiolatam.rest.models.Report;

public interface IReportService {
    List<Report> findAllReports();
    Report findReportById(Long id);
    List<Report> findReportsByTitle(String title);
    Report saveReport(Report report);
    void deleteReport(Long id);
}