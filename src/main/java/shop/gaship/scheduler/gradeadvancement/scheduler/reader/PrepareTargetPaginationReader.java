package shop.gaship.scheduler.gradeadvancement.scheduler.reader;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.stereotype.Component;
import shop.gaship.scheduler.gradeadvancement.domain.membergrade.adapter.AdvancementAdapter;
import shop.gaship.scheduler.gradeadvancement.domain.membergrade.dto.response.AdvancementTargetResponseDto;

/**
 * pagination 을 적용한 targetPagination Item reader 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
public class PrepareTargetPaginationReader
        extends AbstractPaginatedDataItemReader<AdvancementTargetResponseDto> {
    private final AdvancementAdapter advancementAdapter;
    private int count = 0;

    /**
     * AbstractPaginatedDataItemReader 구현을 위해 기본값을 설정하는 PrepareTargetPaginationReader 생성자입니다.
     *
     * @param advancementAdapter the advancement adapter
     */
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

        count++;

        if (count > 1) {
            return null;
        }
        return responseDtoList.iterator();
    }
}
