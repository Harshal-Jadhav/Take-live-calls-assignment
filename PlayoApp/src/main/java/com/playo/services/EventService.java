package com.playo.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.playo.exceptions.BadRequestException;
import com.playo.exceptions.ResourceNotFoundException;
import com.playo.exceptions.UserNotFoundException;
import com.playo.models.Event;
import com.playo.models.Request;
import com.playo.payload.request.EventRequest;

public interface EventService {


	public List<Event> getUpcomingEvents();

	public Event getEventById(Long eventId) throws ResourceNotFoundException;

	public Request joinEvent(Long eventId, HttpServletRequest httpServletRequest)
			throws BadRequestException, ResourceNotFoundException, UserNotFoundException;


	public Event createEvent(EventRequest eventRequest, HttpServletRequest httpServletRequest)
			throws UserNotFoundException;

	//	public Request cancelJoinRequest(Long eventId, HttpServletRequest httpServletRequest);

}
