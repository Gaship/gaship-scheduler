package shop.gaship.scheduler.gradeadvancement.scheduler.dto;

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
public class ConvertedTargetDto {
    private Integer memberNo;
    private String renewalGradeDate;
    private Boolean isComplete;
}
