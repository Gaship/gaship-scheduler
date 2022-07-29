package shop.gaship.gashipscheduler.scheduler.gradeadvancement.processor;

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
        implements ItemProcessor<List<AdvancementTargetResponseDto>, ConvertedTargetDataDto> {

    @Override
    public ConvertedTargetDataDto
            process(List<AdvancementTargetResponseDto> targetResponseDtoList) {
        AdvancementTargetResponseDto responseDto = targetResponseDtoList.get(0);

        ConvertedTargetDataDto convertedTargetDataDto = new ConvertedTargetDataDto();

        convertedTargetDataDto.setMemberNo(responseDto.getMemberNo());
        convertedTargetDataDto.setNextRenewalGradeDate(responseDto.getNextRenewalGradeDate());
        convertedTargetDataDto.setIsComplete(false);

        return convertedTargetDataDto;
    }
}
