package com.nttdata.product.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryRequest {

    private String name;

    private String description;

    private Date createdAt;

    private Date updatedAt;

}
