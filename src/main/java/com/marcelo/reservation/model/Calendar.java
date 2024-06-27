package com.marcelo.reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="scheduleCalendar", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Schedule> schedules;

    @OneToMany(mappedBy="unavailableDateCalendar", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Schedule> unavailableSchedules;

    @OneToOne(mappedBy = "calendar")
    private Member member;

    private Instant created;

    private Instant modified;
}
