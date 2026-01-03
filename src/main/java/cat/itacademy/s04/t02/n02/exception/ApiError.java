package cat.itacademy.s04.t02.n02.exception;


import java.time.LocalDateTime;


public record ApiError (
        int status,
        String message,
        LocalDateTime timeStamp


) {

    public ApiError(int status, String message){
        this(status, message, LocalDateTime.now());
    }

}
