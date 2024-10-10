package com.alvise1.taskManagementApi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_blacklist")
public class TokenBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime expiryDate;

    public TokenBlacklist() {}

    public TokenBlacklist(String token, LocalDateTime expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}

    public LocalDateTime getExpiryDate() {return expiryDate;}
    public void setExpiryDate(LocalDateTime expiryDate) {this.expiryDate = expiryDate;}
}
