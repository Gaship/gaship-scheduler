package shop.gaship.gashipscheduler.domain.member.adapter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipscheduler.config.ServerConfig;
import shop.gaship.gashipscheduler.domain.member.adapter.MemberAdapter;
import shop.gaship.gashipscheduler.domain.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipscheduler.domain.member.dto.response.MemberSchedulerResponseDto;
import shop.gaship.gashipscheduler.exception.RequestFailureException;

/**
 * MemberAdapter interface 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see MemberAdapter
 */
@Component
@RequiredArgsConstructor
public class MemberAdapterImpl implements MemberAdapter {
    private static final Duration timeOut = Duration.of(3, ChronoUnit.SECONDS);
    private static final String ERROR_MESSAGE = "응답결과가 존재하지 않습니다.";
    private static final String MEMBER_URL = "/api/members";
    private final ObjectMapper mapper = new ObjectMapper();

    private final ServerConfig serverConfig;

    @Override
    public List<MemberSchedulerResponseDto> findMembersByRenewalDate(LocalDate renewalGradeDate) {
        String queryParam = "?nextRenewalGradeDate=" + renewalGradeDate;

        return mapper.convertValue(WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build()
                .get()
                .uri(serverConfig.getShoppingMallUrl() + MEMBER_URL + queryParam)
                .retrieve()
                .toEntity(List.class)
                .timeout(timeOut)
                .blockOptional()
                .orElseThrow(() -> new RequestFailureException(ERROR_MESSAGE)).getBody(),
                mapper.getTypeFactory()
                        .constructCollectionType(List.class, MemberSchedulerResponseDto.class));
    }

    @Override
    public void modifyMemberGrade(MemberModifyRequestDto requestDto) {
        String queryParam = "?memberGradeNo=" + requestDto.getMemberGradeNo()
                + "&nextRenewalGradeDate=" + requestDto.getNextRenewalGradeDate();

        WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build()
                .put()
                .uri(serverConfig.getShoppingMallUrl() + MEMBER_URL + queryParam)
                .bodyValue(requestDto)
                .retrieve()
                .toEntity(Void.class)
                .timeout(timeOut)
                .blockOptional()
                .orElseThrow(() -> new RequestFailureException(ERROR_MESSAGE)).getBody();
    }
}
