package shop.gaship.gashipscheduler.domain.member.adapter;

import java.time.LocalDate;
import java.util.List;
import shop.gaship.gashipscheduler.domain.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipscheduler.domain.member.dto.response.MemberSchedulerResponseDto;

/**
 * shopping mall rest-api 에 회원과 관련한 요청을 보내는 adapter.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface MemberAdapter {
    List<MemberSchedulerResponseDto> findMembersByRenewalDate(LocalDate renewalGradeDate);

    void modifyMemberGrade(MemberModifyRequestDto requestDto);
}
