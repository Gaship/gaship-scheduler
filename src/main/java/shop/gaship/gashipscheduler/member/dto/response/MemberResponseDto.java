package shop.gaship.gashipscheduler.member.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원관련 응답 Data Transfer Object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private final Integer memberNo;
    private final Long accumulatePurchaseAmount;
    private final LocalDate nextRenewalGradeDate;
}
