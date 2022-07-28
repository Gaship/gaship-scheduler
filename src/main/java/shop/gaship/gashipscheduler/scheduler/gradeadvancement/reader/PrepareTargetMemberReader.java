package shop.gaship.gashipscheduler.scheduler.gradeadvancement.reader;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import shop.gaship.gashipscheduler.domain.membergrade.adapter.AdvancementAdapter;
import shop.gaship.gashipscheduler.domain.membergrade.dto.response.AdvancementTargetResponseDto;


/**
 * 회원등급 대상이 되는 데이터를 읽어오기 위한 Item Reader 구현체.
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class PrepareTargetMemberReader implements ItemReader<List<AdvancementTargetResponseDto>> {
    private final AdvancementAdapter advancementAdapter;

    @Override
    public List<AdvancementTargetResponseDto> read() {
        int count;

        LocalDateTime renewalDate = LocalDateTime.now();

        if (renewalDate.getHour() == 23) {
            renewalDate = renewalDate.plusHours(1L);
        }

        List<AdvancementTargetResponseDto> targetList = advancementAdapter
                .findTargetsByRenewalDate(renewalDate.toLocalDate());

        count = targetList.size();

        return count == 0 ? null : targetList;
    }
}
