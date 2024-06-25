package com.marcelo.reservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="user", schema = "public")
@ToString(exclude = {"reservations", "businesses"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must contain between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Email(message = "Please type a valid email")
    @NotBlank(message = "Email cannot be blank")
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Business> businesses;

    @OneToOne(fetch = FetchType.EAGER)
    private Calendar calendar;

    private boolean isEnabled;

    private boolean isSuperUser;

    private Instant created;
}
