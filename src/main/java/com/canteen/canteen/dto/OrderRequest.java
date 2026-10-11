package com.canteen.canteen.dto;

import java.util.List;

public class OrderRequest {

    private String studentName;
    private String studentId;
    private String studentPhone;
    private String deliveryOption;
    private List<Item> items;

    public static class Item {
        private Long foodId;
        private Integer quantity;

        public Long getFoodId() { return foodId; }
        public void setFoodId(Long foodId) { this.foodId = foodId; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentPhone() { return studentPhone; }
    public void setStudentPhone(String studentPhone) { this.studentPhone = studentPhone; }

    public String getDeliveryOption() { return deliveryOption; }
    public void setDeliveryOption(String deliveryOption) { this.deliveryOption = deliveryOption; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}