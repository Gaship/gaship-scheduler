package shop.gaship.gashipscheduler.scheduler.gradeadvancement.item;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import shop.gaship.gashipscheduler.domain.member.adapter.MemberAdapter;
import shop.gaship.gashipscheduler.domain.membergrade.adapter.MemberGradeAdapter;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.GradeAdvancementItems;

/**
 * 회원승급에 필요한 데이터를 read 하는 ItemReader.
 *
 * @author : 김세미
 * @since 1.0
 * @see org.springframework.batch.item.ItemReader
 */
@Component
@RequiredArgsConstructor
public class GradeAdvancementReader implements ItemReader<GradeAdvancementItems> {
    private final MemberGradeAdapter memberGradeAdapter;
    private final MemberAdapter memberAdapter;
    private Integer count = 0;

    @Override
    public GradeAdvancementItems read() {
        // 무한 루프 방지
        count++;

        return count == 1
                ? GradeAdvancementItems
                    .builder()
                    .memberGrades(memberGradeAdapter.findMemberGrades())
                    .members(memberAdapter.findMembersByRenewalDate(LocalDate.now()))
                    .build()
                : null;
    }
}
