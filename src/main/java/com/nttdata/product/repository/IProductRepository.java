package com.nttdata.product.repository;

import com.nttdata.product.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * This interface defines the repository for bank account charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Repository
public interface IProductRepository extends ReactiveMongoRepository<Product, String> {

    Mono<Product> findByName(String name);

}
