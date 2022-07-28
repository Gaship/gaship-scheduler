package shop.gaship.gashipscheduler.scheduler.gradeadvancement.processor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import shop.gaship.gashipscheduler.domain.membergrade.dto.response.AdvancementTargetResponseDto;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.dto.ConvertedTargetDataDto;

/**
 * 승급 대상회원 데이터를 변환하기 위한 Item Processor 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see org.springframework.batch.item.ItemProcessor
 */
@Component
public class PrepareTargetMemberProcessor
        implements ItemProcessor<List<AdvancementTargetResponseDto>, List<ConvertedTargetDataDto>> {

    @Override
    public List<ConvertedTargetDataDto>
            process(List<AdvancementTargetResponseDto> targetResponseDtoList) {
        List<ConvertedTargetDataDto> convertedTargetDataDtoList = new ArrayList<>();

        targetResponseDtoList.forEach(item -> {
            ConvertedTargetDataDto convertedTargetDataDto = new ConvertedTargetDataDto();
            convertedTargetDataDto.setMemberNo(item.getMemberNo());
            convertedTargetDataDto.setNextRenewalGradeDate(item.getNextRenewalGradeDate());
            convertedTargetDataDto.setIsComplete(false);

            convertedTargetDataDtoList.add(new ConvertedTargetDataDto());
        });

        return convertedTargetDataDtoList;
    }
}
