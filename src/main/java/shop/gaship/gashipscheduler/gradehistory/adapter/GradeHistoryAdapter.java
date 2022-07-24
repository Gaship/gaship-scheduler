package shop.gaship.gashipscheduler.gradehistory.adapter;

import shop.gaship.gashipscheduler.gradehistory.dto.request.GradeHistoryAddRequestDto;

/**
 * 등급이력 관련 데이터 요청 adapter.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface GradeHistoryAdapter {
    boolean addGradeHistory(GradeHistoryAddRequestDto requestDto);
}
