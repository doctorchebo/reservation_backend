package com.marcelo.reservation.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Duration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private java.time.Duration duration;
    @ManyToOne
    private Business business;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy="durations")
    public List<Service> services;

    private Instant created;

    private Instant modified;

}
