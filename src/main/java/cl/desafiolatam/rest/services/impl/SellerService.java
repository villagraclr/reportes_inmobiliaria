package cl.desafiolatam.rest.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.desafiolatam.rest.models.Seller;
import cl.desafiolatam.rest.repositories.ISellerRepository;
import cl.desafiolatam.rest.services.ISellerService;

@Service
public class SellerService implements ISellerService{
	
	@Autowired
    private ISellerRepository sellerRepository;

    @Override
    public List<Seller> findAllSellers() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller findSellerById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Seller> findSellersByName(String name) {
        return sellerRepository.findByName(name);
    }

    @Override
    public Seller saveSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }
}
