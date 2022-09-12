package shop.gaship.scheduler.coupongenerationissue.scheduler.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * jdbcTemplete을 통해서 shopping-mall db에서 member_no를 가져왔을때 바인딩하기 위한 dto 입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Setter
@Getter
public class MemberNumber {

    Integer memberNo;
}
