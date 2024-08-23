package cl.desafiolatam.rest.services;

import java.util.List;
import cl.desafiolatam.rest.models.Seller;

public interface ISellerService {
    List<Seller> findAllSellers();
    Seller findSellerById(Long id);
    List<Seller> findSellersByName(String name);
    Seller saveSeller(Seller seller);
    void deleteSeller(Long id);
}
