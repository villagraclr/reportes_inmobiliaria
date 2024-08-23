package cl.desafiolatam.rest.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.desafiolatam.rest.services.IReportService;
import cl.desafiolatam.rest.models.Report;
import cl.desafiolatam.rest.repositories.IReportRepository;

@Service
public class ReportService implements IReportService{
	@Autowired
    private IReportRepository reportRepository;

    @Override
    public List<Report> findAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Report findReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public List<Report> findReportsByTitle(String title) {
        return reportRepository.findByTitle(title);
    }

    @Override
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}
