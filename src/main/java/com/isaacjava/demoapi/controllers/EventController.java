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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor

public class EventController {
    private final IEventService eventService;
    private final IEventMapper eventMapper;

    @GetMapping
    public List<EventResponseDto> getAllEvents() {
        List<Event> events = eventService.findAll();
        return eventMapper.toEventResponseDtoList(events);
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        System.out.println("========================");
        System.out.println(eventRequestDto);
        System.out.println("========================");

        Event event = eventMapper.toEntity(eventRequestDto);
        Event savedEvent = eventService.save(event);
        EventResponseDto responseDto = eventMapper.toEventResponseDto(savedEvent);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        EventResponseDto responseDto = eventMapper.toEventResponseDto(event);
        return ResponseEntity.ok(responseDto);
    }
}
