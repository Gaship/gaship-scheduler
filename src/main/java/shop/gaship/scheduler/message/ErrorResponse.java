package shop.gaship.scheduler.message;

/**
 * 에러 발생시 응답 객체.
 *
 * @author : 김세미
 * @since 1.0
 */
public class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
