package cl.desafiolatam.rest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.desafiolatam.rest.models.Report;

public interface IReportRepository extends JpaRepository<Report, Long>{
	List<Report> findByTitle(String title);
}
