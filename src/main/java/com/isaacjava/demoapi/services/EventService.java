package com.isaacjava.demoapi.services;

import com.isaacjava.demoapi.domain.Event;
import com.isaacjava.demoapi.exception.ResourceNotFoundException;
import com.isaacjava.demoapi.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final EventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Event not found with id: " + id)
        );
    }

    @Override
    public void deleteById(Long id) {
        Event event = this.findById(id);
        eventRepository.delete(event);
    }
}
