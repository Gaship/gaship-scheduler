package shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.dto.response;

import lombok.Getter;

/**
 * 회원등급 조회 관련 응답 data transfer object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public class MemberGradeResponseDto {
    private Integer no;
    private String name;
    private Long accumulateAmount;
    private String renewalPeriodStatusCode;
}
