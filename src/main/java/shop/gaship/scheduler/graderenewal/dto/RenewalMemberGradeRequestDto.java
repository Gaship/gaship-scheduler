package shop.gaship.scheduler.graderenewal.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;


/**
 * 회원의 등급 갱신 요청 data transfer object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Builder
public class RenewalMemberGradeRequestDto {
    private final Integer memberNo;
    private final Long totalAmount;
    private final String gradeName;
    private final LocalDate at;
    private final Integer memberGradeNo;
    private LocalDate nextRenewalGradeDate;
}
