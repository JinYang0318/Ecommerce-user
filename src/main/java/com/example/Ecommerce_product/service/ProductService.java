package com.example.Ecommerce_product.service;

import com.example.Ecommerce_product.dto.ProductDTO;
import com.example.Ecommerce_product.mapper.ProductMapper;
import com.example.Ecommerce_product.model.Product;
import com.example.Ecommerce_product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Optional<ProductDTO> getProductById(Integer productId){
        return productRepository.findByProductId(productId)
                .map(productMapper::mapToDTO);
    }

    public List<ProductDTO> getProductByIds(List<Integer> productIds) {
        return productRepository.findAllById(productIds)
                .stream()
                .map(productMapper::mapToDTO)
                .toList();
    }

    public List<ProductDTO> getAllPassengers() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapToDTO)
                .toList();
    }

    public Optional<ProductDTO> createProduct(ProductDTO productDTO) {
        return Optional.of(productRepository.save(productMapper.mapToEntity(productDTO)))
                .map(productMapper::mapToDTO);
    }

    public Optional<ProductDTO> updateProduct(Integer productId, ProductDTO productDTO) {
        return productRepository.findById(productId)
                .map(existingProduct -> {
                    existingProduct.setProductName(productDTO.productName());
                    existingProduct.setProductPrice(productDTO.productPrice());
                    existingProduct.setProductQuantity(productDTO.productQuantity());

                    Product updatedProduct = productRepository.save(existingProduct);

                    return productMapper.mapToDTO(updatedProduct);
                });
    }


    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
}