package shop.gaship.scheduler.coupongenerationissue.scheduler.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Slf4j
public class CustomCouponItemProcessListener implements ItemProcessListener {
    @Override
    public void beforeProcess(Object o) {
        log.debug("coupon item process start!");
    }

    @Override
    public void afterProcess(Object o, Object o1) {
        log.debug("coupon item process end!");
    }

    @Override
    public void onProcessError(Object o, Exception e) {
        log.error("coupon item process error! error : " + e.getMessage());
    }
}
