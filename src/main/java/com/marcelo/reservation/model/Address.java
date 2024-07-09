package com.marcelo.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Business business;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "geolocation_id", referencedColumnName = "id")
    private Geolocation geolocation;

    @Size(min = 10, max = 2000)
    @NotBlank(message = "Address cannot be blank")
    private String name;

    private boolean isMainAddress;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "address")
    private List<Member> members;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "addresses")
    private List<Service> services;

    private Instant created;

    private Instant modified;
}
