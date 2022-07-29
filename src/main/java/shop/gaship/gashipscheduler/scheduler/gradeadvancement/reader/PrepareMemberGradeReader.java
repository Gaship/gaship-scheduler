package shop.gaship.gashipscheduler.scheduler.gradeadvancement.reader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import shop.gaship.gashipscheduler.domain.membergrade.adapter.MemberGradeAdapter;
import shop.gaship.gashipscheduler.domain.membergrade.dto.response.MemberGradeResponseDto;


/**
 * 회원등급 데이터 준비를 위한 Item Reader 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see org.springframework.batch.item.ItemReader
 */
@Component
@RequiredArgsConstructor
public class PrepareMemberGradeReader implements ItemReader<List<MemberGradeResponseDto>> {
    private final MemberGradeAdapter memberGradeAdapter;

    private int count = 0;

    @Override
    public List<MemberGradeResponseDto> read() {
        List<MemberGradeResponseDto> memberGradeList = memberGradeAdapter
                .findMemberGrades();
        count++;

        return count == 1 ? memberGradeList : null;
    }
}
