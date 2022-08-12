package shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 등급 갱신 대상 회원 조회 응답 data response object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class AdvancementTargetResponseDto {
    private Integer memberNo;
    private String nextRenewalGradeDate;
}