package com.isaacjava.demoapi.services;

import com.isaacjava.demoapi.domain.Event;

import java.util.List;

public interface IEventService {
    List<Event> findAll();
    Event save(Event event);
    Event findById(Long id);
}
