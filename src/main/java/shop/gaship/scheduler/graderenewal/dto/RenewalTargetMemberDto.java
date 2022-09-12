package shop.gaship.scheduler.graderenewal.dto;

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
public class RenewalTargetMemberDto {
    private Integer memberNo;
    private LocalDate renewalGradeDate;
    private Long accumulateAmount;
}
