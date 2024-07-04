package com.marcelo.reservation.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SchedulePatchIsWholeDayRequest {
    private Long scheduleId;
    @JsonProperty("isWholeDay")
    private boolean isWholeDay;
}
