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

import cl.desafiolatam.rest.models.Seller;
import cl.desafiolatam.rest.services.ISellerService;

@RestController
@RequestMapping("/api/v1/sellers")
public class SellerController {
	@Autowired
    private ISellerService sellerService;

    // Obtener todos los vendedores
    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers() {
        List<Seller> sellers = sellerService.findAllSellers();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    // Obtener un vendedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) {
        Seller seller = sellerService.findSellerById(id);
        if (seller != null) {
            return new ResponseEntity<>(seller, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Crear un nuevo vendedor
    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        Seller savedSeller = sellerService.saveSeller(seller);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    // Actualizar un vendedor existente
    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody Seller seller) {
        Seller existingSeller = sellerService.findSellerById(id);
        if (existingSeller != null) {
            seller.setId(id);
            Seller updatedSeller = sellerService.saveSeller(seller);
            return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar un vendedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        Seller existingSeller = sellerService.findSellerById(id);
        if (existingSeller != null) {
            sellerService.deleteSeller(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
