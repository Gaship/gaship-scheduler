package shop.gaship.scheduler.coupongenerationissue.service;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import shop.gaship.scheduler.coupongenerationissue.dto.request.CouponGenerationIssueCreationRequestDto;

/**
 * 쿠폰 생성 및 발급요청에 대한 business 로직 처리를 담당합니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CouponGenerationIssueService {
    /**
     * 쿠폰 생성 및 발급에대한 client의 요청을 adapter에 전달합니다.
     *
     * @param couponGenerationIssueCreationRequestDto 쿠폰생성 및 발급을 위해 client에서 넘겨주는 정보가 들어있는 클래스입니다.
     * @author 최겸준
     */
    void addCouponGenerationIssue(CouponGenerationIssueCreationRequestDto couponGenerationIssueCreationRequestDto)
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
        JobParametersInvalidException, JobRestartException;
}
