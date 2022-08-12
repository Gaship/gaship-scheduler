package shop.gaship.scheduler.gradeadvancement.scheduler.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import shop.gaship.scheduler.gradeadvancement.domain.membergrade.dto.response.AdvancementTargetResponseDto;
import shop.gaship.scheduler.gradeadvancement.scheduler.dto.ConvertedTargetDataDto;

/**
 * 승급 대상회원 데이터를 변환하기 위한 Item Processor 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see org.springframework.batch.item.ItemProcessor
 */
@Component
public class PrepareTargetMemberProcessor
        implements ItemProcessor<AdvancementTargetResponseDto, ConvertedTargetDataDto> {

    @Override
    public ConvertedTargetDataDto
            process(AdvancementTargetResponseDto targetResponseDtoList) {

        ConvertedTargetDataDto convertedTargetDataDto = new ConvertedTargetDataDto();

        convertedTargetDataDto.setMemberNo(targetResponseDtoList.getMemberNo());
        convertedTargetDataDto
                .setNextRenewalGradeDate(targetResponseDtoList.getNextRenewalGradeDate());
        convertedTargetDataDto.setIsComplete(false);

        return convertedTargetDataDto;
    }
}
