package com.example.Ecommerce_product.mapper;

import com.example.Ecommerce_product.dto.ProductDTO;
import com.example.Ecommerce_product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO mapToDTO(Product product){
        return ProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productQuantity(product.getProductQuantity())
                .build();
    }

    public Product mapToEntity(ProductDTO productDTO){
        return Product.builder()
                .productName(productDTO.productName())
                .productPrice(productDTO.productPrice())
                .productQuantity(productDTO.productQuantity())
                .build();
    }
}
