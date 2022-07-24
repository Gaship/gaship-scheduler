package shop.gaship.gashipscheduler.member.adapter;

import java.time.LocalDate;
import reactor.core.publisher.Flux;
import shop.gaship.gashipscheduler.member.dto.response.MemberResponseDto;

/**
 * shopping mall rest-api 에 회원과 관련한 요청을 보내는 adapter.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface MemberAdapter {
    Flux<MemberResponseDto> findMembersByRenewalDate(LocalDate renewalGradeDate);
}
