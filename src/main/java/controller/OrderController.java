package com.canteen.canteen.controller;

import com.canteen.canteen.dto.OrderRequest;
import com.canteen.canteen.entity.Order;
import com.canteen.canteen.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        List<Order> allOrders = orderService.getAllOrders();
        Map<String, Object> stats = new java.util.HashMap<>();

        long pending = allOrders.stream().filter(o -> o.getStatus().name().equals("PENDING")).count();
        long completed = allOrders.stream().filter(o -> o.getStatus().name().equals("COMPLETED")).count();
        double totalSales = allOrders.stream()
                .filter(o -> o.getTotalAmount() != null)
                .mapToDouble(Order::getTotalAmount)
                .sum();

        stats.put("totalOrders", allOrders.size());
        stats.put("pendingOrders", pending);
        stats.put("completedOrders", completed);
        stats.put("totalSales", totalSales);

        return stats;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Order> getStudentOrders(@PathVariable String studentId) {
        return orderService.getOrdersByStudentId(studentId);
    }

    @PostMapping
    public Order placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return orderService.updateStatus(id, body.get("status"));
    }
}