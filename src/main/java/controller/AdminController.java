package com.canteen.canteen.controller;

import com.canteen.canteen.entity.AdminCredentials;
import com.canteen.canteen.repository.AdminCredentialsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminCredentialsRepository repository;

    public AdminController(AdminCredentialsRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void seedDefaultAdmin() {
        if (!repository.existsById(1L)) {
            AdminCredentials admin = new AdminCredentials();
            admin.setId(1L);
            admin.setUsername("admin");
            admin.setPassword("admin123");
            repository.save(admin);
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        AdminCredentials admin = repository.findById(1L).orElse(null);
        Map<String, Object> response = new HashMap<>();

        if (admin != null && admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            response.put("success", true);
            response.put("message", "Login successful");
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
        }

        return response;
    }

    @PutMapping("/change-credentials")
    public Map<String, Object> changeCredentials(@RequestBody Map<String, String> body) {
        String currentPassword = body.get("currentPassword");
        String newUsername = body.get("newUsername");
        String newPassword = body.get("newPassword");

        AdminCredentials admin = repository.findById(1L).orElse(null);
        Map<String, Object> response = new HashMap<>();

        if (admin == null || !admin.getPassword().equals(currentPassword)) {
            response.put("success", false);
            response.put("message", "Current password is incorrect");
            return response;
        }

        if (newUsername != null && !newUsername.isBlank()) {
            admin.setUsername(newUsername);
        }
        if (newPassword != null && !newPassword.isBlank()) {
            admin.setPassword(newPassword);
        }
        repository.save(admin);

        response.put("success", true);
        response.put("message", "Credentials updated successfully");
        return response;
    }
}