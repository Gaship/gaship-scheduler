package shop.gaship.scheduler.gradeadvancement.scheduler.exception;

import lombok.Getter;

/**
 * 승급 대상회원을 찾지 못했을때 발생하는 exception.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public class TargetNotFoundException extends RuntimeException {
    private static final String MESSAGE = "대상을 찾을 수 없습니다.";

    public TargetNotFoundException() {
        super(MESSAGE);
    }
}
