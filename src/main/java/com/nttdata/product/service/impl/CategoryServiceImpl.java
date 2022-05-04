package com.nttdata.product.service.impl;

import com.nttdata.product.dto.mapper.CategoryMapper;
import com.nttdata.product.dto.request.CategoryRequest;
import com.nttdata.product.exceptions.CustomException;
import com.nttdata.product.model.Category;
import com.nttdata.product.repository.ICategoryRepository;
import com.nttdata.product.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ICategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    /**
     * @return categories list
     */
    @Override
    public Flux<Category> findAll() {
        return categoryRepository.findAll().onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][findAll]" + e);
            return Mono.error(CustomException.internalServerError("Internal Server Error:" + e));
        });
    }

    /**
     * @param id request
     * @return category
     */
    @Override
    public Mono<Category> findById(String id) {
        return categoryRepository.findById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not found")));
    }

    /**
     * @param request request
     * @return created category
     */
    @Override
    public Mono<Category> create(CategoryRequest request) {
        return categoryMapper.toPostModel(request)
                .flatMap(categoryRepository::save)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][create]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Category not created")));
    }

    /**
     * @param id      request id
     * @param request request body
     * @return updated category
     */
    @Override
    public Mono<Category> update(String id, CategoryRequest request) {
        return findById(id)
                .flatMap(bac -> categoryMapper.toPutModel(bac, request)
                        .flatMap(categoryRepository::save))
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][update]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Category not found")));
    }

    /**
     * @param id request
     * @return void
     */
    @Override
    public Mono<Void> deleteById(String id) {
        return categoryRepository.deleteById(id).onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][delete]" + e);
            return Mono.error(CustomException.badRequest("The request is invalid:" + e));
        });
    }

}
