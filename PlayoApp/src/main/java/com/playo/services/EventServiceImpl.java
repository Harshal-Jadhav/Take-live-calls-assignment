package com.playo.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.playo.enums.RequestStatus;
import com.playo.exceptions.BadRequestException;
import com.playo.exceptions.ResourceNotFoundException;
import com.playo.exceptions.UserNotFoundException;
import com.playo.models.Event;
import com.playo.models.Request;
import com.playo.models.User;
import com.playo.payload.request.EventRequest;
import com.playo.repositories.EventRepository;
import com.playo.repositories.RequestRepository;
import com.playo.repositories.UserRepository;
import com.playo.security.jwt.JwtHelper;

@Service
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;
	private RequestRepository requestRepository;
	private ModelMapper modelMapper;
	private JwtHelper jwtHelper;
	private UserRepository userRepository;

	// Constructor Dependency Injection.
	public EventServiceImpl(EventRepository eventRepository, RequestRepository requestRepository, JwtHelper jwtHelper,
			UserRepository userRepository,ModelMapper modelMapper) {
		this.eventRepository = eventRepository;
		this.requestRepository = requestRepository;
		this.jwtHelper = jwtHelper;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<Event> getUpcomingEvents() {
		LocalDateTime now = LocalDateTime.now();
		return eventRepository.findByDateTimeAfterOrderByDateTimeAsc(now);
	}

	@Override
	public Event getEventById(Long eventId) throws ResourceNotFoundException {
		return eventRepository.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
	}


	@Override
	public Request joinEvent(Long eventId, HttpServletRequest httpServletRequest)
			throws BadRequestException, ResourceNotFoundException, UserNotFoundException {

		String jwtToken = jwtHelper.getTokenfromRequest(httpServletRequest);
		String username = jwtHelper.getUsernameFromToken(jwtToken);

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found with username " + username));
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

		if (event.getPlayers().size() >= event.getMaxPlayers())
			throw new BadRequestException("Event is already full");
		Request request = new Request();
		request.setEvent(event);
		request.setUser(user);
		request.setStatus(RequestStatus.PENDING);
		return requestRepository.save(request);
	}

	@Override
	public Event createEvent(EventRequest eventRequest, HttpServletRequest httpServletRequest)
			throws UserNotFoundException {

		String jwtToken = jwtHelper.getTokenfromRequest(httpServletRequest);
		String username = jwtHelper.getUsernameFromToken(jwtToken);

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found with username " + username));

		Event event = modelMapper.map(eventRequest, Event.class);
		event.setOrganizer(user);

		return eventRepository.save(event);
	}


}
