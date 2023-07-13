package com.kiva.application.service.impl;
//package com.kiva.application.service.impl;
//
//import com.kiva.application.entity.Product;
//import com.kiva.application.entity.Rate;
//import com.kiva.application.entity.User;
//import com.kiva.application.exception.InternalServerException;
//import com.kiva.application.model.request.CreateRateProductRequest;
//import com.kiva.application.repository.ProductRepository;
//import com.kiva.application.repository.RateRepository;
//import com.kiva.application.service.RateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class RateServiceImpl implements RateService {
//
//    @Autowired
//    private RateRepository rateRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Override
//    public Rate createRate(CreateRateProductRequest createRateProductRequest, long userId) {
//        Rate rate = new Rate();
//        rate.setRating(createRateProductRequest.getRate());
//        rate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//        Product product = new Product();
//        product.setId(createRateProductRequest.getProductId());
//        rate.setProduct(product);
//        User user = new User();
//        user.setId(userId);
//        rate.setUser(user);
//        try {
//            rateRepository.save(rate);
//        }catch (Exception e){
//            throw new InternalServerException("Có lỗi khi đánh giá");
//        }
//        return rate;
//    }
//
//}
