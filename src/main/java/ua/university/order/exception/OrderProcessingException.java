package ua.university.order.exception;

public class OrderProcessingException extends AppException{
    public OrderProcessingException(String message) {
        super(message);
    }

    public OrderProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

}
