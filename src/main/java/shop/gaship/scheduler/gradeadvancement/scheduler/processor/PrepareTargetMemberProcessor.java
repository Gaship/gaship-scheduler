package shop.gaship.scheduler.gradeadvancement.scheduler.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import shop.gaship.scheduler.gradeadvancement.scheduler.dto.ConvertedTargetDto;
import shop.gaship.scheduler.gradeadvancement.scheduler.dto.GradeRenewalTargetDto;

/**
 * 등급 갱신 대상회원 데이터를 변환하기 위한 Item Processor 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see org.springframework.batch.item.ItemProcessor
 */
@Component
public class PrepareTargetMemberProcessor
        implements ItemProcessor<GradeRenewalTargetDto, ConvertedTargetDto> {

    @Override
    public ConvertedTargetDto
            process(GradeRenewalTargetDto gradeRenewalTarget) {

        ConvertedTargetDto convertedTargetDto = new ConvertedTargetDto();

        convertedTargetDto
                .setMemberNo(gradeRenewalTarget.getMemberNo());
        convertedTargetDto
                .setRenewalGradeDate(gradeRenewalTarget.getRenewalGradeDate());
        convertedTargetDto
                .setIsComplete(false);

        return convertedTargetDto;
    }
}
