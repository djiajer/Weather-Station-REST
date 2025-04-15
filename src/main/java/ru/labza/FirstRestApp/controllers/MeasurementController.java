package ru.labza.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.labza.FirstRestApp.dto.MeasurementDTO;
import ru.labza.FirstRestApp.models.Measurement;
import ru.labza.FirstRestApp.models.Sensor;
import ru.labza.FirstRestApp.services.MeasurementService;
import ru.labza.FirstRestApp.services.SensorService;
import ru.labza.FirstRestApp.util.MeasurementErrorResponse;
import ru.labza.FirstRestApp.util.MeasurementNotAddedException;
import ru.labza.FirstRestApp.util.SensorErrorResponse;
import ru.labza.FirstRestApp.util.SensorNotFoundException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, SensorService sensorService) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            throw new MeasurementNotAddedException(errorMsg.toString());
        }
        measurementService.add(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler(MeasurementNotAddedException.class)
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse("Measurement wasn't added",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int rainyDaysCount() {
        return measurementService.rainyDaysCount();
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        enrichMeasurement(measurement);
        return measurement;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private void enrichMeasurement(Measurement measurement) {
        bindSensorId(measurement.getSensor());
        measurement.setCreatedAt(LocalDateTime.now());
    }

    private void bindSensorId(Sensor sensor) throws SensorNotFoundException {
        Optional<Sensor> opt = sensorService.findOne(sensor.getName());
        if (opt.isPresent())
            sensor.setId(opt.get().getId());
        else throw new SensorNotFoundException("sensor not found");
    }
    @ExceptionHandler(SensorNotFoundException.class)
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
