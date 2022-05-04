package com.nttdata.product.controller;

import com.nttdata.product.dto.mapper.ProductMapper;
import com.nttdata.product.dto.request.ProductRequest;
import com.nttdata.product.dto.response.ProductResponse;
import com.nttdata.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This controller class defines the endpoints to bank account charges
 *
 * @author Alcibar Vasquesz
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductService productService;

    private final ProductMapper productMapper;

    /**
     * @return list of bank account charges
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductResponse> getAll() {
        return productMapper.toFluxResponse(productService.findAll());
    }

    /**
     * @return bank account charge
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductResponse> getById(@PathVariable(name = "id") String id) {
        return productMapper.toMonoResponse(productService.findById(id));
    }

    /**
     * @param request request to create bank account charge
     * @return bank account charge created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductResponse> create(@RequestBody ProductRequest request) {
        return productMapper.toMonoResponse(productService.create(request));
    }

    /**
     * @param id      bank account charge id to update
     * @param request request for update bank account charge
     * @return bank account charge updated
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductResponse> update(@PathVariable(name = "id") String id,
                                        @RequestBody ProductRequest request) {
        return productMapper.toMonoResponse(productService.update(id, request));
    }

    /**
     * @param id bank account charge id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable(name = "id") String id) {
        return productService.deleteById(id);
    }

}
