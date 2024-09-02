package ru.yandex.practicum.admin.event.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.admin.event.UpdateEventAdminRequest;
import ru.yandex.practicum.admin.event.Event;
import ru.yandex.practicum.event.EventState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.admin.event.EventMapper.toEventFromUpdateEventAdminRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminEventsServiceImpl implements AdminEventsService {
    AdminEventsRepository eventsRepository;

    @Override
    public List<Event> getEvents(List<Long> users, List<String> states, List<Long> categories, LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd) {

       // List<Long> usersIds = new ArrayList<>();
          List<EventState> eventStates = findByState(states);
      //  List<Long> categoryList = new ArrayList<>();

       // for (Integer i : users) {
       //     usersIds.add((long) i);
       // }
       // for (Integer c : categories) {
       //     categoryList.add((long) c);
       // }

        //String datePattern = "yyyy-MM-dd HH:mm:ss";
       // LocalDateTime rs = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern(datePattern));
       // LocalDateTime re = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern(datePattern));

        return eventsRepository.findEvents(users, eventStates, categories, rangeStart, rangeEnd);
    }

    @Override
    public UpdateEventAdminRequest updateEventById(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventsRepository.findById((long) eventId).orElseThrow();
        eventsRepository.save(toEventFromUpdateEventAdminRequest(updateEventAdminRequest, eventId, event.getInitiator(),
                event.getCategory()));

        return updateEventAdminRequest;
    }

    public List<EventState> findByState(List<String> states) {
        List<EventState> eventStates = new ArrayList<>();
        for (String s : states) {
            for (EventState state : EventState.values()) {
                if (s.equalsIgnoreCase(state.name())) {
                    eventStates.add(state);
                }
            }
        }
        return eventStates;
    }

}