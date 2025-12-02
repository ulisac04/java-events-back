package com.isaacjava.demoapi.mapper;

import com.isaacjava.demoapi.domain.Event;
import com.isaacjava.demoapi.dto.EventRequestDto;
import com.isaacjava.demoapi.dto.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEventMapper {

    Event toEntity(EventRequestDto eventRequestDto);
    EventResponseDto toEventResponseDto(Event event);

    List<EventResponseDto> toEventResponseDtoList(List<Event> events);

    void updateEventFromDto(EventRequestDto dto, @MappingTarget Event event);
}
