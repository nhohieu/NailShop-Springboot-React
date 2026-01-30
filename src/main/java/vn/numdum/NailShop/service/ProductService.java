package vn.numdum.NailShop.service;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.numdum.NailShop.domain.Product;
import vn.numdum.NailShop.domain.User;
import vn.numdum.NailShop.domain.DTO.Meta;
import vn.numdum.NailShop.domain.DTO.ResultPaginationDTO;
import vn.numdum.NailShop.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product handleCreateProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Product fetchProductByid(long id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional != null) {
            return productOptional.get();
        }
        return null;
    }

    public ResultPaginationDTO fetchAllProduct(Specification<Product> spec, Pageable pageable) {
        Page<Product> pageProduct = this.productRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();
        mt.setPage(pageProduct.getNumber());
        mt.setPageSize(pageProduct.getSize());
        mt.setPages(pageProduct.getTotalPages());
        mt.setTotal(pageProduct.getTotalElements());
        rs.setMeta(mt);
        rs.setResult(pageProduct.getContent());
        return rs;
    }

    public void handleDeleteUser(long id) {
        this.productRepository.deleteById(id);
    }

    public Product handleUpdateUser(Product reqProduct) {
        Product currentProduct = this.fetchProductByid(reqProduct.getId());

        if (currentProduct != null) {
            currentProduct.setName(reqProduct.getName());
            currentProduct.setPrice(reqProduct.getPrice());
            currentProduct.setDescription(reqProduct.getDescription());
            // update
            currentProduct = this.productRepository.save(currentProduct);
        }
        return currentProduct;
    }

}
