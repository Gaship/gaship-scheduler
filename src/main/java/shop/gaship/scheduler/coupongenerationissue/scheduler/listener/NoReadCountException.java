package shop.gaship.scheduler.coupongenerationissue.scheduler.listener;

/**
 * @author : 최겸준
 * @since 1.0
 */
public class NoReadCountException extends RuntimeException {

    public static final String MESSAGE = "쿠폰타입이 없습니다.";

    public NoReadCountException() {
        super(MESSAGE);
    }
}
