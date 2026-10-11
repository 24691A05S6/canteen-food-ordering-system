package com.canteen.canteen.service;

import com.canteen.canteen.dto.OrderRequest;
import com.canteen.canteen.entity.FoodItem;
import com.canteen.canteen.entity.Order;
import com.canteen.canteen.entity.OrderItem;
import com.canteen.canteen.entity.OrderStatus;
import com.canteen.canteen.repository.FoodItemRepository;
import com.canteen.canteen.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodItemRepository foodItemRepository;

    public OrderService(OrderRepository orderRepository, FoodItemRepository foodItemRepository) {
        this.orderRepository = orderRepository;
        this.foodItemRepository = foodItemRepository;
    }

    @Transactional
    public Order placeOrder(OrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        Order order = new Order();
        order.setStudentName(request.getStudentName());
        order.setStudentId(request.getStudentId());
        order.setStudentPhone(request.getStudentPhone());
        order.setDeliveryOption(request.getDeliveryOption());
        order.setStatus(OrderStatus.PENDING);

        double total = 0;
        for (OrderRequest.Item line : request.getItems()) {
            FoodItem food = foodItemRepository.findById(line.getFoodId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Food not found: " + line.getFoodId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFoodItem(food);
            orderItem.setQuantity(line.getQuantity());
            orderItem.setPrice(food.getPrice());
            order.getItems().add(orderItem);

            total += food.getPrice() * line.getQuantity();
        }
        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);
        saved.setOrderNumber("ORD" + (1000 + saved.getId()));
        return orderRepository.save(saved);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public List<Order> getOrdersByStudentId(String studentId) {
        return orderRepository.findByStudentIdOrderByOrderDateDesc(studentId);
    }

    public Order updateStatus(Long id, String status) {
        Order order = getOrderById(id);
        try {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + status);
        }
        return orderRepository.save(order);
    }
}