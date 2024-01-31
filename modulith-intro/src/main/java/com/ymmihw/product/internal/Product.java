package com.ymmihw.product.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {

    private String name;
    private String description;
    private int price;
}