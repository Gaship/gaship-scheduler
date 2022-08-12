package shop.gaship.scheduler.gradeadvancement.scheduler.writer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.adapter.AdvancementAdapter;
import shop.gaship.gashipscheduler.gradeadvancement.domain.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipscheduler.gradeadvancement.scheduler.entity.AdvancementTarget;
import shop.gaship.gashipscheduler.gradeadvancement.scheduler.repository.AdvancementTargetRepository;
import shop.gaship.gashipscheduler.gradeadvancement.scheduler.exception.TargetNotFoundException;

/**
 * 변환된 승급 데이터를 저장하기 위한 Item Writer 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see org.springframework.batch.item.ItemWriter
 */
@Component
@RequiredArgsConstructor
public class ProgressGradeAdvancementWriter implements ItemWriter<RenewalMemberGradeRequestDto> {
    private final AdvancementAdapter advancementAdapter;
    private final AdvancementTargetRepository advancementTargetRepository;

    @Override
    public void write(List<? extends RenewalMemberGradeRequestDto> requestDtoList) {
        for (RenewalMemberGradeRequestDto requestDto : requestDtoList) {
            advancementAdapter.renewalMemberGrade(requestDto);

            AdvancementTarget target = advancementTargetRepository
                    .findByMemberAndDate(requestDto.getAdvancementMemberRequestDto().getMemberNo(),
                        requestDto.getGradeHistoryAddRequestDto().getAt())
                    .orElseThrow(TargetNotFoundException::new);

            target.complete();
            advancementTargetRepository.save(target);
        }
    }
}
