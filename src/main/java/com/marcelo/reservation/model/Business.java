package com.marcelo.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(exclude = {"members", "services", "owner", "categories", "addresses", "images"})
public class Business {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @OneToMany(mappedBy = "business", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Member> members;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "business_service",
            joinColumns = @JoinColumn(name = "business_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(
            name = "business_categories",
            joinColumns = @JoinColumn(name = "business_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "business", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "business", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Image> images;

    private Instant created;

    private Instant modified;

}
