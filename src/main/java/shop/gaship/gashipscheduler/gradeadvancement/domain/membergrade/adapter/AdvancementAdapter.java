package shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.adapter;

import java.time.LocalDate;
import java.util.List;
import shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.dto.response.AdvancementTargetResponseDto;


/**
 * 회원 승급과 관련된 데이터 요청 adapter.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface AdvancementAdapter {
    /**
     * 등급갱신 대상회원 조회 요청 adapter 메서드.
     *
     * @param renewalGradeDate 대상회원 조회 기준이 되는 등급갱신 날짜 (LocalDate)
     * @return List - AdvancementTargetResponseDto 대상회원 조회 응답 dto 목록.
     */
    List<AdvancementTargetResponseDto> findTargetsByRenewalDate(LocalDate renewalGradeDate);

    /**
     * 회원 정보의 등급 수정 요청 및 등급이력 등록 요청 adapter 메서드.
     *
     * @param requestDto 등급 수정될 대상회원에 대한 정보 및 등급이력 등록정보를 담은 요청 dto
     */
    void renewalMemberGrade(RenewalMemberGradeRequestDto requestDto);

    /**
     * 승급 대상 회원의 특정 기간안의 누적 구매확정 금액 조회 요청 adapter 메서드.
     *
     * @param memberNo             조회하려는 대상 회원의 식별번호 (Integer)
     * @param nextRenewalGradeDate 조회하려는 누적구매금액의 기간정보 (LocalDate)
     * @return 회원의 특정 기간안의 누적구매금액 반환 (Long)
     */
    Long getAccumulatePurchaseAmount(Integer memberNo, LocalDate nextRenewalGradeDate);
}
