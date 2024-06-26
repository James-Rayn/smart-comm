package com.fuji.customer_service.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Document(value = "customer")
public class Customer {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Address address;
    private Date createdDate;
    private Date lastUpdateDate;
}
