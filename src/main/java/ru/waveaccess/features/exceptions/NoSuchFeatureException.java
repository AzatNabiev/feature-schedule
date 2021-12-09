package ru.waveaccess.features.exceptions;

public class NoSuchFeatureException extends RuntimeException {
    public NoSuchFeatureException(String message) {
        super(message);
    }
}
