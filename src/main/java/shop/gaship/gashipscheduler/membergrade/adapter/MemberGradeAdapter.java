package shop.gaship.gashipscheduler.membergrade.adapter;

import reactor.core.publisher.Flux;
import shop.gaship.gashipscheduler.membergrade.dto.response.MemberGradeResponseDto;

/**
 * 회원등급과 관련한 데이터 요청을 보내는 adapter.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface MemberGradeAdapter {
    /**
     * 회원등급 전체 데이터 요청을 위한 메서드.
     *
     * @return Flux - MemberGradeResponseDto
     * @author 김세미
     */
    Flux<MemberGradeResponseDto> findMemberGrades();
}
