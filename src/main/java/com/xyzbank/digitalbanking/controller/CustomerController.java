package com.xyzbank.digitalbanking.controller;

import com.xyzbank.digitalbanking.dto.*;
import com.xyzbank.digitalbanking.service.CustomerService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerRegistrationResponse> registerCustomer(
            @ModelAttribute CustomerRegistrationRequest request) {
        return ResponseEntity.ok(customerService.registerCustomer(request));
    }

    @PostMapping("/logon")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(customerService.login(request));
    }

    @GetMapping("/overview/{username}")
    public ResponseEntity<AccountOverviewResponse> overview(@PathVariable String username) {
        return ResponseEntity.ok(customerService.getAccountOverview(username));
    }

    @GetMapping("/download/{username}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String username) {
        byte[] fileData = customerService.downloadDocument(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document")
                .body(fileData);
    }
}
