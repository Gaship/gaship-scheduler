package shop.gaship.gashipscheduler.scheduler.gradeadvancement.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.entity.AdvancementTarget;

/**
 * 승급 대상회원 관련 repository.
 *
 * @author : 김세미
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface AdvancementTargetRepository extends JpaRepository<AdvancementTarget, Integer> {
    @Query("select t from AdvancementTarget t where t.memberNo =: memberNo "
            + "and t.nextRenewalGradeDate =: at")
    Optional<AdvancementTarget> findByMemberAndDate(@Param("memberNo") Integer memberNo,
                                                    @Param("at") LocalDate at);
}
