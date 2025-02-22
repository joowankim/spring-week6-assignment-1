package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.AuthenticationService;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.errors.MissingAuthorizationHeaderException;
import com.codesoom.assignment.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 상품에 대한 요청을 처리합니다.
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;
    private final AuthenticationService authenticationService;

    public ProductController(ProductService productService, AuthenticationService authenticationService) {
        this.productService = productService;
        this.authenticationService = authenticationService;
    }

    /**
     * 상품 전체 목록을 리턴합니다.
     *
     * @return 상품 목록
     */
    @GetMapping
    public List<Product> list() {
        return productService.getProducts();
    }

    /**
     * 상품 하나를 조회해 리턴합니다.
     *
     * @param id 상품 식별자
     * @return 상품
     */
    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 새로운 상품을 생성하고, 생성된 상품 정보를 리턴합니다.
     *
     * @param authorization 권한을 확인할 수 있는 토큰
     * @param productData 상품 정보
     * @return 생성된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(
            @RequestHeader(value = "Authorization", required = false) Optional<String> authorization,
            @RequestBody @Valid ProductData productData
    ) {
        this.authenticationService.verify(
                authorization.orElseThrow(MissingAuthorizationHeaderException::new));
        return productService.createProduct(productData);
    }

    /**
     * 상품 정보를 수정하고 상품을 리턴합니다.
     *
     * @param authorization 권한을 확인할 수 있는 토큰
     * @param id 상품 식별자
     * @param productData 상품 정보
     * @return 수정된 상품
     */
    @PatchMapping("{id}")
    public Product update(
            @RequestHeader(value = "Authorization", required = false) Optional<String> authorization,
            @PathVariable Long id,
            @RequestBody @Valid ProductData productData
    ) {
        this.authenticationService.verify(
                authorization.orElseThrow(MissingAuthorizationHeaderException::new));
        return productService.updateProduct(id, productData);
    }

    /**
     * 상품을 삭제합니다.
     *
     * @param authorization 권한을 확인할 수 있는 토큰
     * @param id 상품 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(
            @RequestHeader(value = "Authorization", required = false) Optional<String> authorization,
            @PathVariable Long id
    ) {
        this.authenticationService.verify(
                authorization.orElseThrow(MissingAuthorizationHeaderException::new));
        productService.deleteProduct(id);
    }
}
