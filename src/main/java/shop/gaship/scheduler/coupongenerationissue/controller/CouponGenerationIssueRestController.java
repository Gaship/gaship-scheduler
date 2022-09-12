package shop.gaship.scheduler.coupongenerationissue.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.scheduler.coupongenerationissue.dto.request.CouponGenerationIssueCreationRequestDto;
import shop.gaship.scheduler.coupongenerationissue.service.CouponGenerationIssueService;

/**
 * 쿠폰 관련 요청을 처리하기 위한 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/coupon-generations-issues")
@RequiredArgsConstructor
public class CouponGenerationIssueRestController {
    private final CouponGenerationIssueService couponGenerationIssueService;

    @PostMapping
    public ResponseEntity<Void> couponGenerationIssueAdd(@RequestBody
                                                         @Valid CouponGenerationIssueCreationRequestDto couponGenerationIssueCreationRequestDto)
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
        JobParametersInvalidException, JobRestartException {
        couponGenerationIssueService.addCouponGenerationIssue(couponGenerationIssueCreationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
