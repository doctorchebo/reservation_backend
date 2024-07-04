package com.marcelo.reservation.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Calendar scheduleCalendar;

    @ManyToOne
    private Calendar unavailableDateCalendar;

    private DayOfWeek dayOfWeek;

    private boolean isWholeDay;

    @Nullable
    private Instant startTime;

    @Nullable
    private Instant endTime;

    private Instant created;

    private Instant modified;
}
