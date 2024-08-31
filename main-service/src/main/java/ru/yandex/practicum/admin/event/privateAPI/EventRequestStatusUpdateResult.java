package ru.yandex.practicum.admin.event.privateAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateResult {
    ParticipationRequestDto confirmedRequests;
    ParticipationRequestDto rejectedRequests;
}