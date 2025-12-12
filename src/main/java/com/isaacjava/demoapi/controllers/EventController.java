package com.isaacjava.demoapi.controllers;


import com.isaacjava.demoapi.domain.Event;
import com.isaacjava.demoapi.dto.EventRequestDto;
import com.isaacjava.demoapi.dto.EventResponseDto;
import com.isaacjava.demoapi.mapper.IEventMapper;
import com.isaacjava.demoapi.services.IEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor

public class EventController {
    private final IEventService eventService;
    private final IEventMapper eventMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<EventResponseDto> getAllEvents() {
        List<Event> events = eventService.findAll();
        return eventMapper.toEventResponseDtoList(events);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        Event event = eventMapper.toEntity(eventRequestDto);
        Event savedEvent = eventService.save(event);
        EventResponseDto responseDto = eventMapper.toEventResponseDto(savedEvent);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        EventResponseDto responseDto = eventMapper.toEventResponseDto(event);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Long id,
                                                        @Valid @RequestBody EventRequestDto eventRequestDto) {
        Event existingEvent = eventService.findById(id);
        eventMapper.updateEventFromDto(eventRequestDto, existingEvent);
        Event updatedEvent = eventService.save(existingEvent);
        EventResponseDto responseDto = eventMapper.toEventResponseDto(updatedEvent);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
