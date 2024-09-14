package ru.yandex.practicum.event.publicAPI;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.client.StatsClient;
import ru.yandex.practicum.error.NotFoundException;
import ru.yandex.practicum.event.model.enums.EventState;
import ru.yandex.practicum.event.model.search.SearchEventsArgs;
import ru.yandex.practicum.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicEventsServiceImpl implements PublicEventsService {

    private final EventRepository eventsRepository;
    private final StatsClient client;

    @Value("${ewm.service.name}")
    private String serviceName;

    @Override
    public List<Event> getEvents(SearchEventsArgs args) {

        Specification<Event> spec = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("state"), EventState.PUBLISHED));

        if (args.getText() != null) {
            String searchText = String.format("%%%s%%", args.getText());
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(root.get("annotation"), searchText),
                    criteriaBuilder.like(root.get("description"), searchText)
            ));
        }
        if (args.getCategories() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.get("category").get("id").in(args.getCategories()));
        }
        if (args.getPaid() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("paid"), args.getPaid()));
        }
        if (args.getRangeStart() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get("date"), args.getRangeStart()));
        }
        if (args.getRangeEnd() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get("date"), args.getRangeEnd()));
        }
        if (args.getOnlyAvailable() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("participantLimit"), 0),
                            criteriaBuilder.lessThanOrEqualTo(
                                    root.get("confirmedRequests"),
                                    root.get("participantLimit").as(Long.class)
                            )
                    )
            );
        }

        List<Event> events = eventsRepository.findAll(spec);

        if (args.getSort() != null) {
            switch (args.getSort()) {
                case "EVENT_DATE":
                    events.sort(Comparator.comparing(Event::getDate));
                    break;
                case "VIEWS":
                    events.sort(Comparator.comparing(Event::getViews));
                    break;
                default:
                    events.sort(Comparator.comparing(Event::getId));
            }
        }

        log.info("Сформирован список запросов согласно спецификации");

        saveEndpointHit(args.getRequest());

        return events;
    }

    @Override
    public Event getEventById(int eventId, HttpServletRequest request) {
        Event event = eventsRepository.findById((long) eventId).orElseThrow(()
                -> new NotFoundException("Event not found with id = " + eventId));

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("Event with id=" + eventId + " is not published");
        }

        log.info("Найдено событие с id=" + eventId);

        saveEndpointHit(request);

        event.setViews(event.getViews() + 1);

        return event;
    }

    @Override
    public Event getEventByIdWithoutRequest(int eventId) {
        Event event = eventsRepository.findById((long) eventId).orElseThrow(()
                -> new NotFoundException("Event not found with id = " + eventId));

        log.info("Найдено событие с id=" + eventId);

        return event;
    }

    private void saveEndpointHit(HttpServletRequest request) {
        EndpointHitDto eh = new EndpointHitDto(
                null,
                serviceName,
                (request.getRequestURI()),
                request.getRemoteAddr(),
                LocalDateTime.now()
        );

        log.info("Клиент отправил эндпойнт на веб-сервис");

        client.postStats(eh);
    }

}