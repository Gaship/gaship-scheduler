package shop.gaship.scheduler.gradeadvancement.scheduler.exception;

/**
 * 회원승급 작업 실행시 발생할 수 있는 exception.
 *
 * @author : 김세미
 * @since 1.0
 */
public class MemberGradeAdvanceRunException extends RuntimeException {
    private static final String MESSAGE = "회원승급 작업 실행중 issue 가 발생하였습니다.";

    public MemberGradeAdvanceRunException() {
        super(MESSAGE);
    }
}
