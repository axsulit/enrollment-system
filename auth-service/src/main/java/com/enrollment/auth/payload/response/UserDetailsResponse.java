package com.enrollment.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailsResponse {
    private Integer id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
} 