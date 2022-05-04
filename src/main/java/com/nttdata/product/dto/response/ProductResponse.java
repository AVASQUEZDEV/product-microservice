package com.nttdata.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * This class defines the response of bank account charge
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@AllArgsConstructor
@Data
public class ProductResponse {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "commission")
    private Float commission;

    @JsonProperty(value = "movementsQuantity")
    private String movementsQuantity;

    @JsonProperty(value = "frequency")
    private String frequency;

    @JsonProperty(value = "createdAt")
    private Date createdAt;

    @JsonProperty(value = "updatedAt")
    private Date updatedAt;

    @JsonProperty(value = "category")
    private CategoryResponse category;

}
