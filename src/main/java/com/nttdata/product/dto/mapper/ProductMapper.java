package com.nttdata.product.dto.mapper;

import com.nttdata.product.dto.request.ProductRequest;
import com.nttdata.product.dto.response.CategoryResponse;
import com.nttdata.product.dto.response.ProductResponse;
import com.nttdata.product.model.Product;
import com.nttdata.product.util.AppUtil;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Service
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    /**
     * This method convert request to model
     *
     * @param request request of bankAccountCharge
     * @return bankAccountCharge model
     */
    public Mono<Product> toPostModel(ProductRequest request) {
        return Mono.just(
                new Product(
                        request.getName(),
                        request.getCommission(),
                        request.getMovementsQuantity(),
                        AppUtil.dateFormat(new Date()),
                        AppUtil.dateFormat(new Date())
                )
        );
    }

    /**
     * This method convert request to model
     *
     * @param product entity
     * @param request           product request
     * @return product model
     */
    public Mono<Product> toPutModel(Product product, ProductRequest request) {
        product.setCommission(request.getCommission());
        product.setMovementsQuantity(request.getMovementsQuantity());
        product.setUpdatedAt(AppUtil.dateFormat(new Date()));
        return Mono.just(product);
    }

    /**
     * This method convert bankAccountCharge to response
     *
     * @param product entity
     * @return converted response
     */
    public Mono<ProductResponse> toMonoResponse(Mono<Product> product) {
        return product.flatMap(p -> {
            Mono<CategoryResponse> category = categoryMapper.toMonoResponse(Mono.just(p.getCategory()));
            return category.flatMap(cat -> Mono.just(
                    new ProductResponse(
                            p.getId(),
                            p.getName(),
                            p.getCommission(),
                            p.getMovementsQuantity(),
                            p.getFrequency(),
                            p.getCreatedAt(),
                            p.getCreatedAt(), cat))
            );
        });
    }

    /**
     * This method convert a list the bankAccountCharges to response
     *
     * @param bankAccountCharges bankAccountCharges list
     * @return converted response
     */
    public Flux<ProductResponse> toFluxResponse(Flux<Product> bankAccountCharges) {
        return bankAccountCharges.flatMap(bac -> toMonoResponse(Mono.just(bac)));
    }

}
