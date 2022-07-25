package shop.gaship.gashipscheduler.domain.gradehistory.adapter;

import shop.gaship.gashipscheduler.domain.gradehistory.dto.request.GradeHistoryAddRequestDto;

/**
 * 등급이력 관련 데이터 요청 adapter.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface GradeHistoryAdapter {
    /**
     * 등급이력 등록 요청을 위한 메서드.
     *
     * @param requestDto 등급이력 등록 요청 dto.
     * @author 김세미
     */
    void addGradeHistory(GradeHistoryAddRequestDto requestDto);
}
