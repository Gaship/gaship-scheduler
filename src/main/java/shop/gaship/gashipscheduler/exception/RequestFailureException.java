package shop.gaship.gashipscheduler.exception;

/**
 * 서버로 보낸 요청 실패시 발생하는 Exception.
 *
 * @author : 김세미
 * @since 1.0
 */
public class RequestFailureException extends RuntimeException {
    public RequestFailureException(String message) {
        super(message);
    }
}
