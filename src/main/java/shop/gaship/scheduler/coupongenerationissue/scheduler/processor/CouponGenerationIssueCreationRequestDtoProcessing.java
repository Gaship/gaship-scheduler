package shop.gaship.scheduler.coupongenerationissue.scheduler.processor;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Reader로 읽어온 값을 처리하여 결과물을 반환할때 사용할 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class CouponGenerationIssueCreationRequestDtoProcessing {
    private Integer couponTypeNo;

    private Integer memberNo;

    private LocalDateTime generationDatetime;

    private LocalDateTime issueDatetime;

    private LocalDateTime expirationDatetime;
}
