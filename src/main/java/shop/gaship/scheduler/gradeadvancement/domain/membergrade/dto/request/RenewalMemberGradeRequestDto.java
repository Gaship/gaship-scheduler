package shop.gaship.scheduler.gradeadvancement.domain.membergrade.dto.request;

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
    private GradeHistoryAddRequestDto gradeHistoryAddRequestDto;
    private AdvancementMemberRequestDto advancementMemberRequestDto;
}
