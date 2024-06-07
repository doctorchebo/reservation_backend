package com.marcelo.reservation.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

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
    @ManyToOne()
    private Business business;

    @ManyToOne()
    public Service service;

    private Instant created;

    private Instant modified;

}
