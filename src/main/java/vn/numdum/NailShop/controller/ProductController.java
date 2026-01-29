package vn.numdum.NailShop.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.numdum.NailShop.domain.Product;
import vn.numdum.NailShop.domain.User;
import vn.numdum.NailShop.domain.DTO.ResultPaginationDTO;
import vn.numdum.NailShop.service.ProductService;
import vn.numdum.NailShop.util.error.IdInvalidException;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product pr = this.productService.handleCreateProduct(product);
        return ResponseEntity.ok(pr);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> fetchProductByID(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productService.fetchProductByid(id));
    }

    @GetMapping("/products")
    public ResponseEntity<ResultPaginationDTO> fetchAllProduct(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        String sCurrent = currentOptional.isPresent() ? currentOptional.get() : " ";
        String sPagesize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : " ";
        int current = Integer.parseInt(sCurrent);
        int pagesize = Integer.parseInt(sPagesize);
        Pageable pageable = PageRequest.of(current - 1, pagesize);
        return ResponseEntity.ok(this.productService.fetchAllProduct(pageable));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable("id") long id) throws IdInvalidException {

        this.productService.handleDeleteUser(id);
        return ResponseEntity.ok("delete success");
    }

    @PutMapping("/products")
    public ResponseEntity<Product> UpdateUserById(@RequestBody Product product) {
        Product hieuproduct = this.productService.handleUpdateUser(product);
        return ResponseEntity.ok(hieuproduct);
    }

}
