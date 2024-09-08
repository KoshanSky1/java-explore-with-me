package ru.yandex.practicum.event.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.search.SelectionCriteria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PublicSelectionRepositoryImpl implements PublicSelectionRepository {

    private final EntityManager manager;
    private final CriteriaBuilder builder;

    public PublicSelectionRepositoryImpl(EntityManager entityManager) {
        this.manager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
    }

    public Page<Event> findAllWithArgs(Pageable pageable, SelectionCriteria selectionCriteria) {

        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> eventRoot = query.from(Event.class);
        Predicate predicate = getPredicate(selectionCriteria, eventRoot);
        query.where(predicate);

        if (pageable.getSort().isUnsorted()) {
            query.orderBy(builder.desc(eventRoot.get("createdOn")));
        }

        TypedQuery<Event> typedQuery = manager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Event> events = typedQuery.getResultList();

        return new PageImpl<>(events);
    }

    private Predicate getPredicate(SelectionCriteria criteria, Root<Event> eventRoot) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = null;
        if (Objects.nonNull(criteria.getText())) {
            predicate = builder.like(builder.lower(eventRoot.get("annotation")),
                    "%" + criteria.getText().toLowerCase() + "%");
        }
        if (Objects.nonNull(criteria.getText()) && predicate == null) {
            predicates.add(builder.like(builder.lower(eventRoot.get("description")),
                    "%" + criteria.getText().toLowerCase() + "%"));
        } else if (Objects.nonNull(criteria.getText())) {
            Predicate descriptionPredicate = builder.like(builder.lower(eventRoot.get("description")),
                    "%" + criteria.getText().toLowerCase() + "%");
            predicates.add(builder.or(predicate, descriptionPredicate));
        }

        if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
            Join<Event, Category> categoryJoin = eventRoot.join("category");
            predicates.add(categoryJoin.get("id").in(criteria.getCategories()));
        }
        if (criteria.getPaid() != null && criteria.getPaid().equals(Boolean.TRUE)) {
            predicates.add(builder.equal(eventRoot.get("paid"), criteria.getPaid()));
        }
        if (criteria.getRangeStart() != null || criteria.getRangeEnd() != null) {
            LocalDateTime rangeStart = criteria.getRangeStart() != null
                    ? criteria.getRangeStart()
                    : LocalDateTime.MIN;
            LocalDateTime rangeEnd = criteria.getRangeEnd() != null
                    ? criteria.getRangeEnd()
                    : LocalDateTime.MAX;
            predicates.add(builder.between(eventRoot.get("date"), rangeStart, rangeEnd));
        } else {
            predicates.add(builder.between(eventRoot.get("date"), LocalDateTime.now(),
                    LocalDateTime.now().plusYears(100)));
        }

        if (criteria.getOnlyAvailable() != null && criteria.getOnlyAvailable()) {
            predicates.add(builder.or(
                    builder.isNull(eventRoot.get("participantLimit")),
                    builder.greaterThan(
                            builder.diff(eventRoot.get("participantLimit"), eventRoot.get("confirmedRequests")),
                            0L
                    )
            ));
        }
        return builder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
    }
}
