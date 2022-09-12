package shop.gaship.scheduler.coupongenerationissue.scheduler.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Slf4j
public class CustomCouponItemReadListener implements ItemReadListener {

    @Override
    public void beforeRead() {
        log.debug("coupon item read start!");
    }

    @Override
    public void afterRead(Object o) {
        log.debug("coupon item read end!");
    }

    @Override
    public void onReadError(Exception e) {
        log.error("coupon item read error! error : " + e.getMessage());
    }
}
