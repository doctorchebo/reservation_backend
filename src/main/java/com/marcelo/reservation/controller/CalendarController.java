package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.calendar.CalendarDto;
import com.marcelo.reservation.dto.calendar.CalendarRequest;
import com.marcelo.reservation.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;
    @GetMapping("getAll")
    public ResponseEntity<List<CalendarDto>> getAllCalendars(){
        return ResponseEntity.ok(calendarService.getAllCalendars());
    }

    @PostMapping("create")
    public ResponseEntity<CalendarDto> createCalendar(@RequestBody CalendarRequest calendarRequest){
        return ResponseEntity.ok(calendarService.createCalendar(calendarRequest));
    }
}
