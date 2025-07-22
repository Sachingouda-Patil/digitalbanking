package com.xyzbank.digitalbanking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationResponse {
    private String status;
    private String username;
    private String password;
    private String message;
}
