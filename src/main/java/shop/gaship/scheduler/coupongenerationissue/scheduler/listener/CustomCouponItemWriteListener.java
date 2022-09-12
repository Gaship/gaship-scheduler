package shop.gaship.scheduler.coupongenerationissue.scheduler.listener;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Slf4j
public class CustomCouponItemWriteListener implements ItemWriteListener {

    @Override
    public void beforeWrite(List list) {
        log.debug("coupon item write start!");
    }

    @Override
    public void afterWrite(List list) {
        log.debug("coupon item write end!");
    }

    @Override
    public void onWriteError(Exception e, List list) {
        log.error("coupon item write error! error : " + e.getMessage());
    }
}
