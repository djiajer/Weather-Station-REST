package ru.labza.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.labza.FirstRestApp.models.Sensor;
import ru.labza.FirstRestApp.repositories.SensorRepository;
import ru.labza.FirstRestApp.util.SensorNotCreatedException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findOne(int id) {
        return sensorRepository.findById(id);
    }

    public Optional<Sensor> findOne(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

}
