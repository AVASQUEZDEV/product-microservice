package com.nttdata.product.service;

import com.nttdata.product.dto.request.ProductRequest;
import com.nttdata.product.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface defines the service of bank accounts charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
public interface IProductService {

    Flux<Product> findAll();

    Mono<Product> findById(String id);

    Mono<Product> create(ProductRequest request);

    Mono<Product> update(String id, ProductRequest request);

    Mono<Void> deleteById(String id);

}
