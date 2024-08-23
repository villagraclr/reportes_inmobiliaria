package cl.desafiolatam.rest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.desafiolatam.rest.models.Seller;

public interface ISellerRepository extends JpaRepository<Seller, Long>{
	List<Seller> findByName(String name);

}
