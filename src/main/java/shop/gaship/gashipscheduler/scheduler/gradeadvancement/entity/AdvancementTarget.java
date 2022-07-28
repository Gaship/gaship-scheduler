package shop.gaship.gashipscheduler.scheduler.gradeadvancement.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.dto.ConvertedTargetDataDto;

/**
 * 승급 대상 Entity class.
 *
 * @author : 김세미
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@Entity
@Table(name = "advancement_targets")
public class AdvancementTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advancement_target_no")
    private Integer no;

    @Positive
    @NotNull(message = "대상 회원 번호는 필수값입니다.")
    @Column(name = "member_no")
    private Integer memberNo;

    @NotNull(message = "승급 일자는 필수값입니다.")
    @Column(name = "renewal_grade_date")
    private LocalDate nextRenewalGradeDate;

    @NotNull(message = "성공여부는 필수값입니다.")
    @Column(name = "is_complete")
    private Boolean isComplete;

    /**
     * Instantiates a new Advancement target.
     *
     * @param convertedTargetDataDto the converted target data dto
     */
    public AdvancementTarget(ConvertedTargetDataDto convertedTargetDataDto) {
        this.memberNo = convertedTargetDataDto.getMemberNo();
        this.nextRenewalGradeDate = LocalDate
                .parse(convertedTargetDataDto.getNextRenewalGradeDate());
        this.isComplete = false;
    }

    /**
     * 해당 대상회원에 대한 작업 처리가 성공적으로 완료된 경우
     * 해당 대상회원의 isComplete 값을 true 로 수정하는 메서드.
     */
    public void complete() {
        this.isComplete = true;
    }
}
