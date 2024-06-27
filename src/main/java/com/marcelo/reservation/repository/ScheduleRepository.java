package com.marcelo.reservation.repository;

import com.marcelo.reservation.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByScheduleCalendarMemberBusinessId(Long businessId);

    List<Schedule> findAllByScheduleCalendarMemberId(Long memberId);

    List<Schedule> findAllByScheduleCalendarId(Long calendarId);

    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.id = :scheduleId")
    void deleteById(@Param("scheduleId") Long scheduleId);

}
