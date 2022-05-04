package com.nttdata.product.controller;

import com.nttdata.product.dto.mapper.CategoryMapper;
import com.nttdata.product.dto.request.CategoryRequest;
import com.nttdata.product.dto.response.CategoryResponse;
import com.nttdata.product.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This controller class defines the endpoints to category
 *
 * @author Alcibar Vasquesz
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryRestController {

    private final ICategoryService categoryService;

    private final CategoryMapper categoryMapper;

    /**
     * @return list of category
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CategoryResponse> getAll() {
        return categoryMapper.toFluxResponse(categoryService.findAll());
    }

    /**
     * @return bank account charge
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CategoryResponse> getById(@PathVariable(name = "id") String id) {
        return categoryMapper.toMonoResponse(categoryService.findById(id));
    }

    /**
     * @param request request to create bank account charge
     * @return bank account charge created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CategoryResponse> create(@RequestBody CategoryRequest request) {
        return categoryMapper.toMonoResponse(categoryService.create(request));
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
    public Mono<CategoryResponse> update(@PathVariable(name = "id") String id,
                                         @RequestBody CategoryRequest request) {
        return categoryMapper.toMonoResponse(categoryService.update(id, request));
    }

    /**
     * @param id bank account charge id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable(name = "id") String id) {
        return categoryService.deleteById(id);
    }

}
