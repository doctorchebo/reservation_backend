package com.marcelo.reservation.service;

import com.marcelo.reservation.dto.calendar.CalendarDto;
import com.marcelo.reservation.dto.calendar.CalendarRequest;
import com.marcelo.reservation.exception.NotFoundException;
import com.marcelo.reservation.mapper.CalendarMapper;
import com.marcelo.reservation.model.Calendar;
import com.marcelo.reservation.model.Member;
import com.marcelo.reservation.repository.CalendarRepository;
import com.marcelo.reservation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private static Logger logger = LoggerFactory.getLogger(CalendarService.class);

    private final CalendarRepository calendarRepository;

    private final MemberRepository memberRepository;

    private final CalendarMapper calendarMapper;
    public List<CalendarDto> getAllCalendars() {
        return calendarMapper.mapToDtoList(calendarRepository.findAll());
    }

    public CalendarDto createCalendar(CalendarRequest calendarRequest) {
        Member member = memberRepository.findById(calendarRequest.getMemberId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Member with id %s not found", calendarRequest)));
        Calendar calendar = Calendar.builder()
                .member(member)
                .created(Instant.now())
                .build();

        return calendarMapper.mapToDto(calendarRepository.save(calendar));
    }
}
