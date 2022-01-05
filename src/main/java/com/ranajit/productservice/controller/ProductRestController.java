package com.ranajit.productservice.controller;

import com.ranajit.productservice.dto.Coupon;
import com.ranajit.productservice.entity.Product;
import com.ranajit.productservice.repository.ProductRepository;
import com.ranajit.productservice.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/productapi")
public class ProductRestController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CouponService couponService;

//    @Value("${couponService.url}")
//    private String  couponServiceURL;

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product){

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setBasicAuth("doug@bailey.com","doug");
//        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
//        ResponseEntity<Coupon> response = new RestTemplate().exchange(couponServiceURL+product.getCouponCode(),
//                HttpMethod.GET,request,Coupon.class);
//
//        Coupon coupon = response.getBody();

        Coupon coupon = couponService.getCoupon(product.getCouponCode());

//        Coupon coupon = restTemplate.getForObject(couponServiceURL+product.getCouponCode(),
//                Coupon.class);
        if (coupon != null) {
            product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        }
        return productRepository.save(product);
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") long id){
        return productRepository.findById(id);
    }
}
