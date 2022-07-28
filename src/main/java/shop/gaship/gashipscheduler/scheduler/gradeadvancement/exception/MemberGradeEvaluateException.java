package shop.gaship.gashipscheduler.scheduler.gradeadvancement.exception;

import lombok.Getter;

/**
 * 회원등급 산정시 산정될 회원등급을 찾을 수 없을때 발생하는 exception.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public class MemberGradeEvaluateException extends RuntimeException {
    private static final String MESSAGE = "회원등급 산정시 예외가 발생하였습니다.";

    public MemberGradeEvaluateException() {
        super(MESSAGE);
    }
}
