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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponService couponService;

    @GetMapping("/showCreateProduct")
    public String showCreateProduct(){
        return "createProduct";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(Product product){

        Coupon coupon = couponService.getCoupon(product.getCouponCode());
        if (coupon != null) {
            product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        }
        productRepository.save(product);
        return "createResponse";
    }

    @GetMapping("/showGetProduct")
    public String showGetCoupon(){
        return "getProduct";
    }

    @PostMapping("/getProduct")
    public ModelAndView getProduct(String name){
        ModelAndView mav = new ModelAndView("productDetails");
        mav.addObject(productRepository.findByName(name));
        return mav;
    }
}
