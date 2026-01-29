package vn.numdum.NailShop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.numdum.NailShop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
