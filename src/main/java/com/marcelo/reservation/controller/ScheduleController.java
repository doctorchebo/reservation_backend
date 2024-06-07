package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.schedule.ScheduleDto;
import com.marcelo.reservation.dto.schedule.ScheduleRequest;
import com.marcelo.reservation.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    @GetMapping("getAll")
    public ResponseEntity<List<ScheduleDto>> getAllSchedules(){
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @PostMapping("create")
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleRequest scheduleRequest){
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleRequest));
    }
}
