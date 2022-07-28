package shop.gaship.gashipscheduler.scheduler.gradeadvancement.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * batch db 에 저장하기 위해 변환된 승급 대상회원 data transfer object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class ConvertedTargetDataDto {
    private Integer memberNo;
    private String nextRenewalGradeDate;
    private Boolean isComplete;
}
