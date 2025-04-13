package ru.labza.FirstRestApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotBlank(message = "name should not be empty")
    @Size(min = 3, max = 30, message = "name should be from 3 to 30 chars long")
    private String name;

    public SensorDTO() {
    }

    public SensorDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
