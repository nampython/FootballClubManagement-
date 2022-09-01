package com.example.Excercise1.exceptions;

public class FileMaintEPWorkgroupLogicException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileMaintEPWorkgroupLogicException() {
        super();
    }

    public FileMaintEPWorkgroupLogicException(String message) {
        super(message);
    }

    public FileMaintEPWorkgroupLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileMaintEPWorkgroupLogicException(Throwable cause) {
        super(cause);
    }
}
