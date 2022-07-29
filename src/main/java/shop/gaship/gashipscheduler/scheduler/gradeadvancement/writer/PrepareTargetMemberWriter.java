package shop.gaship.gashipscheduler.scheduler.gradeadvancement.writer;

import java.lang.annotation.Repeatable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.dto.ConvertedTargetDataDto;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.entity.AdvancementTarget;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.repository.AdvancementTargetRepository;

/**
 * 변환된 승급 대상회원 정보를 batch db 에 저장하기 위한 Item Writer 구현체.
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class PrepareTargetMemberWriter implements ItemWriter<ConvertedTargetDataDto> {
    private final AdvancementTargetRepository advancementTargetRepository;

    @Override
    public void write(List<? extends ConvertedTargetDataDto> targetList) {
        for (ConvertedTargetDataDto target : targetList) {
            AdvancementTarget advancementTarget = new AdvancementTarget(target);
            advancementTargetRepository.save(advancementTarget);
        }
    }
}
