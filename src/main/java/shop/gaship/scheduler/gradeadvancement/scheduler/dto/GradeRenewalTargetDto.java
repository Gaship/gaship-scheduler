package shop.gaship.scheduler.gradeadvancement.scheduler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 등급 갱신 대상 회원 조회 결과 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class GradeRenewalTargetDto {
    private Integer memberNo;
    private String renewalGradeDate;
}
