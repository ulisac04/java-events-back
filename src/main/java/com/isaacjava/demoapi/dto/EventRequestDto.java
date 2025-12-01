// src/main/java/com/isaacjava/demoapi/dto/EventRequestDto.java
package com.isaacjava.demoapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data //
public class EventRequestDto {
    @NotBlank
    private String name;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank
    private String location;

    @Override
    public String toString() {
        return "EventRequestDto{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                '}';
    }
}
