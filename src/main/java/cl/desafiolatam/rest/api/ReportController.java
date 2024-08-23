package cl.desafiolatam.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.desafiolatam.rest.models.Report;
import cl.desafiolatam.rest.repositories.IReportRepository;
import cl.desafiolatam.rest.services.IReportService;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
	@Autowired
    private IReportService reportService;
	
	@Autowired
    private IReportRepository reportRepository;

    // Obtener todos los reportes
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.findAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    // Obtener un reporte por ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Report report = reportService.findReportById(id);
        if (report != null) {
            return new ResponseEntity<>(report, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Crear un nuevo reporte
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report savedReport = reportService.saveReport(report);
        return new ResponseEntity<>(savedReport, HttpStatus.CREATED);
    }

    // Actualizar un reporte existente
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report) {
        Report existingReport = reportService.findReportById(id);
        if (existingReport != null) {
            report.setId(id);
            Report updatedReport = reportService.saveReport(report);
            return new ResponseEntity<>(updatedReport, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar un reporte
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        Report existingReport = reportService.findReportById(id);
        if (existingReport != null) {
            reportService.deleteReport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
