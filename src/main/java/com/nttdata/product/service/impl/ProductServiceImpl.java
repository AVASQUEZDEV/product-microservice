package com.nttdata.product.service.impl;

import com.nttdata.product.dto.mapper.ProductMapper;
import com.nttdata.product.dto.request.ProductRequest;
import com.nttdata.product.exceptions.CustomException;
import com.nttdata.product.model.Product;
import com.nttdata.product.repository.ICategoryRepository;
import com.nttdata.product.repository.IProductRepository;
import com.nttdata.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class defines the service of bank accounts charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final IProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ICategoryRepository categoryRepository;

    /**
     * This method returns a list of bank accounts charges
     *
     * @return ank account charges list
     */
    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll().onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][findAll]" + e);
            return Mono.error(CustomException.internalServerError("Internal Server Error:" + e));
        });
    }

    /**
     * This method return one bank account charge
     *
     * @param id request
     * @return bank account charge
     */
    @Override
    public Mono<Product> findById(String id) {
        return productRepository.findById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not found")));
    }

    /**
     * This method creates a bank account charges
     *
     * @param request request to create new bank account charges
     * @return bank account charges created
     */
    @Override
    public Mono<Product> create(ProductRequest request) {
        return categoryRepository.findByName(request.getCategory().getName())
                .flatMap(c -> productMapper.toPostModel(request)
                        .flatMap(p -> {
                            p.setCategory(c);
                            return productRepository.save(p)
                                    .onErrorResume(e -> {
                                        LOGGER.error("[" + getClass().getName() + "][create]" + e);
                                        return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                                    }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not created")));
                        })
                );

    }

    /**
     * This method updates a bank account charges
     *
     * @param id      bank account charge id to update
     * @param request request to update bank account charge
     * @return bank account charge updated
     */
    @Override
    public Mono<Product> update(String id, ProductRequest request) {
        return findById(id)
                .flatMap(bac -> productMapper.toPutModel(bac, request)
                        .flatMap(productRepository::save))
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][update]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not found")));
    }

    /**
     * This method delete a bank account charge
     *
     * @param id bank account charge id to delete
     * @return void
     */
    @Override
    public Mono<Void> deleteById(String id) {
        return productRepository.deleteById(id).onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][delete]" + e);
            return Mono.error(CustomException.badRequest("The request is invalid:" + e));
        });
    }

}
