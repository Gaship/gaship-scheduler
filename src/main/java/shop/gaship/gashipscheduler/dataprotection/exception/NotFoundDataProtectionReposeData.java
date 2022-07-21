package shop.gaship.gashipscheduler.dataprotection.exception;

/**
 * Secure Key Manager 에서 응답을 제대로 받지 못하거나 요청이 잘못 되었을 경우 발생하는 exception.
 *
 * @author : 김세미
 * @see java.lang.RuntimeException
 * @since 1.0
 */
public class NotFoundDataProtectionReposeData extends RuntimeException {
    public NotFoundDataProtectionReposeData(String s) {
        super(s);
    }
}
