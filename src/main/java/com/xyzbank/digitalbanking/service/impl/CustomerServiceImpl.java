package com.xyzbank.digitalbanking.service.impl;

import com.xyzbank.digitalbanking.dto.*;
import com.xyzbank.digitalbanking.entity.Customer;
import com.xyzbank.digitalbanking.repository.CustomerRepository;
import com.xyzbank.digitalbanking.service.CustomerService;
import com.xyzbank.digitalbanking.util.AccountNumberGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest request) {
        int age = Period.between(request.getDob(), LocalDate.now()).getYears();
        if(age < 18) {
            return new CustomerRegistrationResponse("FAILURE", null, null, "Customer must be 18 or above");
        }

        if(!request.getCountry().equalsIgnoreCase("Netherlands") &&
                !request.getCountry().equalsIgnoreCase("Belgium")) {
            return new CustomerRegistrationResponse("FAILURE", null, null, "Only customers from Netherlands or Belgium allowed");
        }

        String password = "Pass" + new Random().nextInt(9999);
        String accountNumber = AccountNumberGenerator.generateAccountNumber();
        String documentPath = saveDocument(request.getDocument(), request.getUsername());

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(password)
                .dob(request.getDob())
                .country(request.getCountry())
                .accountType(request.getAccountType())
                .balance(BigDecimal.ZERO)
                .currency("EUR")
                .accountNumber(accountNumber)
                .documentPath(documentPath)
                .build();

        customerRepository.save(customer);

        return new CustomerRegistrationResponse("SUCCESS", request.getUsername(), password, "Registration successful");
    }

    private String saveDocument(MultipartFile file, String username) {
        try {
            if (file == null || file.isEmpty()) return null;

            String fileName = username + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads");

            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (Exception e) {
            throw new RuntimeException("File upload failed");
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<Customer> customerOpt = customerRepository.findByUsername(request.getUsername());
        if(customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if(customer.getPassword().equals(request.getPassword())) {
                return new LoginResponse("SUCCESS", "Login successful");
            } else {
                return new LoginResponse("FAILURE", "Invalid password");
            }
        } else {
            return new LoginResponse("FAILURE", "User not found");
        }
    }

    @Override
    public AccountOverviewResponse getAccountOverview(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return new AccountOverviewResponse(
                customer.getAccountNumber(),
                customer.getAccountType(),
                customer.getBalance(),
                customer.getCurrency()
        );
    }

    @Override
    public byte[] downloadDocument(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        try {
            Path path = Paths.get(customer.getDocumentPath());
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new RuntimeException("File not found");
        }
    }
}
