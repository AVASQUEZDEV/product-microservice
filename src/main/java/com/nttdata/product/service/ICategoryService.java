package com.nttdata.product.service;

import com.nttdata.product.dto.request.CategoryRequest;
import com.nttdata.product.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICategoryService {

    Flux<Category> findAll();

    Mono<Category> findById(String id);

    Mono<Category> create(CategoryRequest request);

    Mono<Category> update(String id, CategoryRequest request);

    Mono<Void> deleteById(String id);

}
