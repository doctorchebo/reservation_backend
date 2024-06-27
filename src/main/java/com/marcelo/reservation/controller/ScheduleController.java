package com.marcelo.reservation.controller;

import com.marcelo.reservation.dto.schedule.ScheduleDto;
import com.marcelo.reservation.dto.schedule.ScheduleCreateRequest;
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
    @GetMapping("getAllByBusinessId/{businessId}")
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesByBusinessId(@PathVariable Long businessId){
        return ResponseEntity.ok(scheduleService.getAllSchedulesByBusinessId(businessId));
    }
    @GetMapping("getAllByMemberId/{memberId}")
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesByMemberId(@PathVariable Long memberId){
        return ResponseEntity.ok(scheduleService.getAllSchedulesByMemberId(memberId));
    }
    @GetMapping("getAllByCalendarId/{calendarId}")
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesByCalendarId(@PathVariable Long calendarId){
        return ResponseEntity.ok(scheduleService.getAllSchedulesByCalendarId(calendarId));
    }

    @PostMapping("create")
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleCreateRequest scheduleCreateRequest){
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleCreateRequest));
    }
    @DeleteMapping("delete/{scheduleId}")
    public ResponseEntity<ScheduleDto> deleteSchedule(@PathVariable Long scheduleId){
        return ResponseEntity.ok(scheduleService.deleteSchedule(scheduleId));
    }
}
