package ru.yandex.practicum.event.publicAPI;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.error.ConflictException;
import ru.yandex.practicum.error.IncorrectParameterException;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.client.StatsClient;
import ru.yandex.practicum.error.NotFoundException;
import ru.yandex.practicum.event.model.enums.EventState;
import ru.yandex.practicum.event.model.search.SearchEventsArgs;
import ru.yandex.practicum.event.model.search.SelectionCriteria;
import ru.yandex.practicum.event.repository.EventRepository;
import ru.yandex.practicum.event.repository.PublicSelectionRepositoryImpl;
import ru.yandex.practicum.util.CustomPageRequest;
import ru.yandex.practicum.event.repository.PublicSelectionRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.yandex.practicum.event.dto.EventMapper.toListEventShortDtoFromSetEvents;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicEventsServiceImpl implements PublicEventsService {
    //private final PublicSelectionRepositoryImpl selectionRepository;
    private final EventRepository eventsRepository;
    private final StatsClient client;

    @Value("${ewm.service.name}")
    private String serviceName;

    @Override
    public List<EventShortDto> getEvents(SearchEventsArgs args) {

        CustomPageRequest pageable = createPagination(args.getSort(), args.getFrom(), args.getSize());
        SelectionCriteria selectionCriteria = createCriteria(args);

        saveEndpointHit(args.getRequest());

        //System.out.println(args);
       // if (args.getRangeEnd().isBefore(LocalDateTime.now())) {
       //     throw new IncorrectParameterException("Field: category. Error: must not be blank. Value: null");
       // }

        return toListEventShortDtoFromSetEvents(eventsRepository.findAllWithArgs(pageable,
                selectionCriteria).toSet());
    }

    @Override
    public Event getEventById(int eventId, HttpServletRequest request) {
        Event event = eventsRepository.findById((long) eventId).orElseThrow(()
                -> new NotFoundException(String.format("Event not found with id = " + eventId)));

        if (!event.getState().equals(EventState.PUBLISHED)) {
            System.out.println("hi");
            throw new NotFoundException("Event with id=" + eventId + " is not published");
        }
        saveEndpointHit(request);
        event.setViews(event.getViews() + 1);
        return event;
    }

    private CustomPageRequest createPagination(String sort, int from, int size) {
        CustomPageRequest pageable = null;
        if (sort == null || sort.equalsIgnoreCase("EVENT_DATE")) {
            pageable = new CustomPageRequest(from, size,
                    Sort.by(Sort.Direction.ASC, "event_date"));
        } else if (sort.equalsIgnoreCase("VIEWS")) {
            pageable = new CustomPageRequest(from, size,
                    Sort.by(Sort.Direction.ASC, "views"));
        }
        return pageable;
    }

    private SelectionCriteria createCriteria(SearchEventsArgs args) {
        return SelectionCriteria.builder()
                .text(args.getText())
                .categories(args.getCategories())
                .rangeEnd(args.getRangeEnd())
                .rangeStart(args.getRangeStart())
                .paid(args.getPaid())
                .build();
    }

    private void saveEndpointHit(HttpServletRequest request) {
        EndpointHitDto eh = new EndpointHitDto(
                null,
                serviceName,
                (request.getRequestURI()),
                request.getRemoteAddr(),
                LocalDateTime.now()
        );

        client.postStats(eh);
    }

}