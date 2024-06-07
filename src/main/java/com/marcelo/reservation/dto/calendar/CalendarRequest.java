package com.marcelo.reservation.dto.calendar;

import lombok.Data;

import java.time.Instant;

@Data
public class CalendarRequest {
    private Long memberId;
    private Instant created;
    private Instant modified;
}
