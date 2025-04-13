package ru.labza.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.labza.FirstRestApp.models.Measurement;
import ru.labza.FirstRestApp.repositories.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public Optional<Measurement> findOne(int id) {
        return measurementRepository.findById(id);
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void add(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    public int rainyDaysCount() {
        return measurementRepository.countAllByRainingIsTrue();
    }
}
