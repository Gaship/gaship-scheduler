package shop.gaship.gashipscheduler.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원의 회원등급 수정 요청 Data Transfer Object.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class MemberModifyRequestDto {
    private final Integer memberNo;
    private final Integer memberGradeNo;
}
