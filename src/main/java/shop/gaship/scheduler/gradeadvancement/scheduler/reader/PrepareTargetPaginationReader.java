package shop.gaship.scheduler.gradeadvancement.scheduler.reader;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.stereotype.Component;
import shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.adapter.AdvancementAdapter;
import shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.dto.response.AdvancementTargetResponseDto;

/**
 * 설명작성
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
public class PrepareTargetPaginationReader extends AbstractPaginatedDataItemReader<AdvancementTargetResponseDto> {
    private final AdvancementAdapter advancementAdapter;
    private static int count = 0;

    public PrepareTargetPaginationReader(AdvancementAdapter advancementAdapter) {
        this.advancementAdapter = advancementAdapter;
        setExecutionContextName("페이징");
        setName("hi");
        setSaveState(false);
    }

    @Override
    protected Iterator<AdvancementTargetResponseDto> doPageRead() {
        List<AdvancementTargetResponseDto> responseDtoList =
                advancementAdapter.findTargetsByRenewalDate(LocalDate.of(2022, 7, 28));
        count ++;
        if(count>1) {
            return null;
        }
        return responseDtoList.iterator();
    }
}
