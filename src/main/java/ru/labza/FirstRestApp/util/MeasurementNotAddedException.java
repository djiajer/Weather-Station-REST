package ru.labza.FirstRestApp.util;

public class MeasurementNotAddedException extends RuntimeException{
    public MeasurementNotAddedException(String message) {
        super(message);
    }
}
