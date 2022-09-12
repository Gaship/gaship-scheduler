package shop.gaship.scheduler.graderenewal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원등급 조회 관련 응답 data transfer object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberGradeResponseDto {
    private Integer no;
    private String name;
    private Long accumulateAmount;
    private String renewalPeriod;
}
