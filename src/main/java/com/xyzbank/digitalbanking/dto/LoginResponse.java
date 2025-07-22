package com.xyzbank.digitalbanking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String status;
    private String message;
}
