package shop.gaship.gashipscheduler.gradeadvancement.scheduler.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * 승급대상 dto.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class AdvancementTargetDto {
    private Integer no;
    private Integer memberNo;
    private LocalDate nextRenewalGradeDate;
    private Boolean isComplete;
}
