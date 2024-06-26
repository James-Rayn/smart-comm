package com.fuji.order_service.services;

import com.fuji.order_service.dto.OrderLineRequest;
import com.fuji.order_service.dto.OrderRequest;
import com.fuji.order_service.entities.Order;
import com.fuji.order_service.mapper.OrderMapper;
import com.fuji.order_service.model.Customer;
import com.fuji.order_service.model.ProductPurchaseRequest;
import com.fuji.order_service.model.ProductPurchaseResponse;
import com.fuji.order_service.repositories.OrderRepository;
import com.fuji.order_service.utils.Response;
import com.fuji.order_service.webClient.WebClientCustomer;
import com.fuji.order_service.webClient.WebClientProduct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WebClientCustomer webClientCustomer;
    private final WebClientProduct webClientProduct;
    private final OrderLineService orderLineService;

    @Override
    public Response create(OrderRequest request) {
        var customer= webClientCustomer.getCustomer(request.customerID());
        List<ProductPurchaseResponse> productPurchaseResponses = webClientProduct.purchaseProduct(request.products());

        Order order = orderRepository.save(orderMapper.mapToOrder(request));

        for (ProductPurchaseRequest purchaseRequest: request.products()) {
            orderLineService.create(
                    new OrderLineRequest(
                            order.getId(),
                            purchaseRequest.productID(),
                            purchaseRequest.quantity()
                    )
            );
        }

        return null;
    }

    @Override
    public Response update(OrderRequest request) {
        return null;
    }

    @Override
    public Response get(String idOrder) {
        return null;
    }

    @Override
    public Response getAll() {
        return null;
    }

    @Override
    public Response delete(String idOrder) {
        return null;
    }

    private Response generateResponse(HttpStatus status, Map<?, ?> data, String message) {
        return Response.builder()
                .timeStamp(LocalDateTime.now())
                .status(status)
                .statusCode(status.value())
                .data(data)
                .message(message)
                .build();
    }
}
