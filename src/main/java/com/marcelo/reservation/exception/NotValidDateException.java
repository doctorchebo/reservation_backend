package com.marcelo.reservation.exception;

public class NotValidDateException extends IllegalArgumentException{
    public NotValidDateException(String message, Exception ex) {
        super(message, ex);
    }
    public NotValidDateException(String message){
        super(message);
    }
}
