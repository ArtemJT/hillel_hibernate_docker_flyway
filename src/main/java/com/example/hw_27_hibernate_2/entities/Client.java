package com.example.hw_27_hibernate_2.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @OneToOne(mappedBy = "client")
    private Address address;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Order> ordersHistory;
}
