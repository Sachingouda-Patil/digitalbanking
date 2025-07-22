package com.xyzbank.digitalbanking.service.impl;

import com.xyzbank.digitalbanking.dto.*;
import com.xyzbank.digitalbanking.entity.Customer;
import com.xyzbank.digitalbanking.repository.CustomerRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    public CustomerServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomer_Success() {
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "John", "Doe", "johndoe", LocalDate.of(2000,1,1), "Netherlands", "SAVINGS",
                new MockMultipartFile("document", "test.png", "image/png", "test".getBytes())
        );

        Customer savedCustomer = Customer.builder()
                .id(1L)
                .username("johndoe")
                .password("Pass1234")
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerRegistrationResponse response = customerService.registerCustomer(request);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("johndoe", response.getUsername());
        assertNotNull(response.getPassword());
    }

    @Test
    void testLogin_Success() {
        Customer customer = Customer.builder()
                .username("johndoe")
                .password("Pass1234")
                .build();

        when(customerRepository.findByUsername("johndoe")).thenReturn(Optional.of(customer));

        LoginRequest loginRequest = new LoginRequest("johndoe", "Pass1234");
        LoginResponse response = customerService.login(loginRequest);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("Login successful", response.getMessage());
    }

    @Test
    void testGetAccountOverview_Success() {
        Customer customer = Customer.builder()
                .username("johndoe")
                .accountNumber("ABNA123456789012")
                .accountType("SAVINGS")
                .balance(BigDecimal.valueOf(500))
                .currency("EUR")
                .build();

        when(customerRepository.findByUsername("johndoe")).thenReturn(Optional.of(customer));

        AccountOverviewResponse response = customerService.getAccountOverview("johndoe");

        assertEquals("ABNA123456789012", response.getAccountNumber());
        assertEquals("SAVINGS", response.getAccountType());
        assertEquals(BigDecimal.valueOf(500), response.getBalance());
        assertEquals("EUR", response.getCurrency());
    }
}
