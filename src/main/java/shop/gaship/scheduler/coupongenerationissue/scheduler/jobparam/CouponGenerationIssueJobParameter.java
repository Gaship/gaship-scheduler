package shop.gaship.scheduler.coupongenerationissue.scheduler.jobparam;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * jobParameter를 효율적으로 사용하기위해 만든 파라미터 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
public class CouponGenerationIssueJobParameter {
    @Value("#{jobParameters['couponTypeNo']}")
    private Integer couponTypeNo;

    @Value("#{jobParameters['memberGradeNo']}")
    private Integer memberGradeNo;

    @Value("#{jobParameters['generationDatetime']}")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime generationDatetime;

    @Value("#{jobParameters['issueDatetime']}")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime issueDatetime;

    @Value("#{jobParameters['expirationDatetime']}")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expirationDatetime;

}
