package com.playo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playo.exceptions.BadRequestException;
import com.playo.exceptions.ResourceNotFoundException;
import com.playo.exceptions.UserNotFoundException;
import com.playo.models.Event;
import com.playo.models.Request;
import com.playo.payload.request.EventRequest;
import com.playo.services.EventService;

/*
 * This is Event Controller to handle all the requests related to the Event Service. 
 * like 
 * -- creating an event (http://localhost:8088/playo/events/create) Authentication Is required.
 * 
 * -- getEventById (http://localhost:8088/playo/events/{eventId}) Authentication Not required.
 * 
 * -- get upComming events (http://localhost:8088/playo/events/upcoming) Authentication Not required.
 * 
 * -- Request to join a event (http://localhost:8088/playo/events/{eventId}/join) Authentication is required.
 */

@RestController
@RequestMapping("/playo/events")
public class EventController {

	private EventService eventService;

	// Constructor Dependency Injection.
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping("/upcoming")
	public ResponseEntity<List<Event>> getUpcomingEvents() {
		List<Event> events = eventService.getUpcomingEvents();
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<Event> getEventById(@PathVariable Long eventId) throws ResourceNotFoundException {
		Event event = eventService.getEventById(eventId);
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}

	@PostMapping("/{eventId}/join")
	public ResponseEntity<Request> joinEvent(@PathVariable Long eventId, HttpServletRequest httpServletRequest)
			throws BadRequestException, ResourceNotFoundException, UserNotFoundException {

		Request request = eventService.joinEvent(eventId, httpServletRequest);
		return new ResponseEntity<Request>(request, HttpStatus.OK);
	}


	@PostMapping("/create")
	public ResponseEntity<Event> createNewEvent(@RequestBody EventRequest eventRequest,
			HttpServletRequest httpServletRequest) throws UserNotFoundException {
		Event event = eventService.createEvent(eventRequest, httpServletRequest);
		return new ResponseEntity<Event>(event, HttpStatus.CREATED);
	}



}
