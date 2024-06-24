package com.marcelo.reservation.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = {"businesses", "durations", "reservations", "categories", "addresses"})
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    public Business business;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "service_duration",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "duration_id")
    )
    public List<Duration> durations;

    @OneToMany(mappedBy = "service", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Reservation> reservations;

    @ManyToMany(mappedBy = "services", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Category> categories;

    @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(
            name = "service_address",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    public List<Address> addresses;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "service", orphanRemoval = true, cascade = CascadeType.ALL)
    public List<Price> prices;

    private Instant created;

    private Instant modified;

}
