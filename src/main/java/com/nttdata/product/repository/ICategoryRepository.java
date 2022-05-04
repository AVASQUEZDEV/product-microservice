package com.nttdata.product.repository;

import com.nttdata.product.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ICategoryRepository extends ReactiveMongoRepository<Category, String> {

    Mono<Category> findByName(String name);

}
