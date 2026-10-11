package com.canteen.canteen.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_credentials")
public class AdminCredentials {

    @Id
    private Long id = 1L;

    private String username;
    private String password;

    public AdminCredentials() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}