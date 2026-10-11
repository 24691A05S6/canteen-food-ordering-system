package com.canteen.canteen.repository;

import com.canteen.canteen.entity.Order;
import com.canteen.canteen.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStudentIdOrderByOrderDateDesc(String studentId);

    List<Order> findAllByOrderByOrderDateDesc();

    long countByStatus(OrderStatus status);
}