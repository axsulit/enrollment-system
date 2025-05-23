package com.enrollment.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Integer id;
    private String username;
    private String email;
    private List<String> roles;
} 