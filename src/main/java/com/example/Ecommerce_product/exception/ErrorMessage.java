package com.example.Ecommerce_product.exception;

import lombok.Builder;

@Builder
public record ErrorMessage(String message) {
}