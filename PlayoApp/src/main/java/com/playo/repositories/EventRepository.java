package com.playo.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playo.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByDateTimeAfterOrderByDateTimeAsc(LocalDateTime dateTime);
}
