package com.example.demo.error;

public enum TicketBookingError {

    USER_NOT_FOUND(1, "USER_NOT_FOUND", 404);

    protected final Integer errorCode;
    protected final String message;
    protected final Integer httpStatus;

    TicketBookingError(Integer errorCode, String message, Integer httpStatus){
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }


}
