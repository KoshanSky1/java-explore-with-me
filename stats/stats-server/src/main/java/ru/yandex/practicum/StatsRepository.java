package ru.yandex.practicum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    /*@Query("select h " +
            "from hits as h " +
            "where h.created beetwen ?1 and ?2 " +
            "and h. uri in ?3 " +
            "group by h.app, h.uri")

    List<VievStats> getStat(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select h " +
            "from hits as h " +
            "where h.created beetwen ?1 and ?2 " +
            "and h. uri in ?3 " +
            "group by h.app, h.uri")
    List<VievStats> getUniqueStat(LocalDateTime start, LocalDateTime end, List<String> uris);
    */
}