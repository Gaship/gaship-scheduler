package shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.dto.request;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

/**
 * 등급이력 등록 요청 Data Transfer Object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Builder
@Getter
public class GradeHistoryAddRequestDto {
    private final Integer memberNo;
    private final Long totalAmount;
    private final String gradeName;
    private final LocalDate at;
}