package com.ymmihw.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductDTO {

    private String name;
    private String description;
    private int price;
}