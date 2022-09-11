package shop.gaship.scheduler.coupongenerationissue.service.impl;

import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import shop.gaship.scheduler.coupongenerationissue.dto.request.CouponGenerationIssueCreationRequestDto;
import shop.gaship.scheduler.coupongenerationissue.service.CouponGenerationIssueService;

/**
 * couponGenerationIssueService를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @see shop.gaship.scheduler.coupongenerationissue.service.CouponGenerationIssueService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CouponGenerationIssueServiceImpl implements CouponGenerationIssueService {

    private final JobLauncher jobLauncher;

    @Qualifier(value = "couponGenerationIssueJob")
    private final Job couponGenerationIssueJob;

    private final BasicBatchConfigurer basicBatchConfigurer;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCouponGenerationIssue(
        CouponGenerationIssueCreationRequestDto couponGenerationIssueCreationRequestDto)
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
        JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters
            = new JobParametersBuilder()
            .addLong("couponTypeNo",
                couponGenerationIssueCreationRequestDto.getCouponTypeNo().longValue())
            .addLong("memberGradeNo",
                couponGenerationIssueCreationRequestDto.getMemberGradeNo().longValue())
            .addString("generationDatetime",
                couponGenerationIssueCreationRequestDto.getGenerationDatetime().format(
                    DateTimeFormatter.ISO_DATE_TIME))
            .addString("issueDatetime",
                couponGenerationIssueCreationRequestDto.getIssueDatetime().format(
                    DateTimeFormatter.ISO_DATE_TIME))
            .addString("expirationDatetime",
                couponGenerationIssueCreationRequestDto.getExpirationDatetime().format(
                    DateTimeFormatter.ISO_DATE_TIME)).toJobParameters();

        SimpleJobLauncher jobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());

        jobLauncher.run(couponGenerationIssueJob, jobParameters);
    }
}
