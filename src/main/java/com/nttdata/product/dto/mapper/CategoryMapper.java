package com.nttdata.product.dto.mapper;

import com.nttdata.product.dto.request.CategoryRequest;
import com.nttdata.product.dto.response.CategoryResponse;
import com.nttdata.product.model.Category;
import com.nttdata.product.util.AppUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * This class convert request and response
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Service
public class CategoryMapper {

    /**
     * This method convert request to model
     *
     * @param request request of category
     * @return category model
     */
    public Mono<Category> toPostModel(CategoryRequest request) {
        return Mono.just(
                new Category(
                        request.getName(),
                        request.getDescription(),
                        AppUtil.dateFormat(new Date()),
                        AppUtil.dateFormat(new Date())
                )
        );
    }

    /**
     * This method convert request to model
     *
     * @param product entity
     * @param request product request
     * @return product model
     */
    public Mono<Category> toPutModel(Category product, CategoryRequest request) {
        product.setName(request.getName());
        product.setUpdatedAt(AppUtil.dateFormat(new Date()));
        return Mono.just(product);
    }

    /**
     * This method convert category to response
     *
     * @param category entity
     * @return converted response
     */
    public Mono<CategoryResponse> toMonoResponse(Mono<Category> category) {
        return category.flatMap(bac -> Mono.just(
                new CategoryResponse(
                        bac.getId(),
                        bac.getName(),
                        bac.getDescription(),
                        bac.getCreatedAt(),
                        bac.getCreatedAt()))
        );
    }

    /**
     * This method convert a list the categorys to response
     *
     * @param category categorys list
     * @return converted response
     */
    public Flux<CategoryResponse> toFluxResponse(Flux<Category> category) {
        return category.flatMap(bac -> toMonoResponse(Mono.just(bac)));
    }

}
