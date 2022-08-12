package shop.gaship.scheduler.gradeadvancement.domain.membergrade.dto.request;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

/**
 * 회원의 등급 정보 수정 요청 data transfer object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Builder
public class AdvancementMemberRequestDto {
    private final Integer memberNo;
    private final Integer memberGradeNo;
    private LocalDate nextRenewalGradeDate;
}
