package com.kiva.application.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.kiva.application.entity.Order;
import com.kiva.application.model.dto.OrderDetailDTO;
import com.kiva.application.model.dto.OrderInfoDTO;
import com.kiva.application.model.request.CreateOrderRequest;
import com.kiva.application.model.request.UpdateDetailOrder;
import com.kiva.application.model.request.UpdateStatusOrderRequest;

import java.util.List;

@Service
public interface OrderService {
    Page<Order> adminGetListOrders(String id, String name, String phone, String status, String product, int page);

    Order createOrder(CreateOrderRequest createOrderRequest, long userId);

    void updateDetailOrder(UpdateDetailOrder updateDetailOrder, long id, long userId);

    Order findOrderById(long id);

    void updateStatusOrder(UpdateStatusOrderRequest updateStatusOrderRequest, long orderId, long userId);

    List<OrderInfoDTO> getListOrderOfPersonByStatus(int status, long userId);

    OrderDetailDTO userGetDetailById(long id, long userId);

    void userCancelOrder(long id, long userId);

    //Đếm số lượng đơn hàng
    long getCountOrder();

}
