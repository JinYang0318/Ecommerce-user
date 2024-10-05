package com.example.Ecommerce_product.controller;

import com.example.Ecommerce_product.dto.ProductDTO;
import com.example.Ecommerce_product.exception.NotFoundException;
import com.example.Ecommerce_product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Integer productId) {
        ProductDTO productDTO = productService.getProductById(productId)
                .orElseThrow(() -> new NotFoundException("Product Id With " + productId + " not found"));
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> getProductByIds(
            @RequestParam(value = "id", required = false) List<Integer> productIds) {
        List<ProductDTO> productDTOList = productService.getProductByIds(productIds);
        List<Integer> returnedIds = productDTOList
                .stream()
                .map(ProductDTO::productId)
                .toList();

        List<Integer> missingIds = productIds.stream()
                .filter(productId -> !returnedIds.contains(productId))
                .toList();

        HttpHeaders headers = new HttpHeaders();
        if (!missingIds.isEmpty()) {
            headers.add("X-MISSING-SET", missingIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
        log.info("X-MISSING-SET {}", missingIds);
        return ResponseEntity.ok().headers(headers).body(productDTOList);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOList = productService.getAllPassengers();
        return ResponseEntity.ok().body(productDTOList);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO)
                .map(product -> ResponseEntity.status(HttpStatus.CREATED).body(product))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable("id") Integer productId,
            @Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(productId, productDTO)
                .map(product -> ResponseEntity.status(HttpStatus.OK).body(product))
                .orElseThrow(() -> new NotFoundException("Product Id With " + productId + " not found"));
    }

    @DeleteMapping(value = "/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer productId) {
        ProductDTO productDTO = productService.getProductById(productId)
                .orElseThrow(() -> new NotFoundException("Product Id With " + productId + " not found"));
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body("Product Id With " + productId + " successfully deleted.");
    }
}