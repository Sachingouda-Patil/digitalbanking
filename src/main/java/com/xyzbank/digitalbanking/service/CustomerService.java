package com.xyzbank.digitalbanking.service;

import com.xyzbank.digitalbanking.dto.*;

public interface CustomerService {
    CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest request);
    LoginResponse login(LoginRequest request);
    AccountOverviewResponse getAccountOverview(String username);
    byte[] downloadDocument(String username);
}
