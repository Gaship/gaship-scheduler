package shop.gaship.gashipscheduler.domain.membergrade.adapter.impl;

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
import shop.gaship.gashipscheduler.domain.membergrade.adapter.AdvancementAdapter;
import shop.gaship.gashipscheduler.domain.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipscheduler.domain.membergrade.dto.response.AdvancementTargetResponseDto;
import shop.gaship.gashipscheduler.exception.RequestFailureException;

/**
 * 회원 승급 adapter interface 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see shop.gaship.gashipscheduler.domain.membergrade.adapter.AdvancementAdapter
 */
@Component
@RequiredArgsConstructor
public class AdvancementAdapterImpl implements AdvancementAdapter {
    private static final Duration timeOut = Duration.of(3, ChronoUnit.SECONDS);
    private static final String ERROR_MESSAGE = "응답결과가 존재하지 않습니다.";
    private static final String ADVANCEMENT_URL = "/api/member-grades/advancement";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ServerConfig serverConfig;

    /**
     * {@inheritDoc}
     *
     * @throws RequestFailureException 서버로 보낸 요청이 실패하였습니다.
     */
    @Override
    public List<AdvancementTargetResponseDto> findTargetsByRenewalDate(LocalDate renewalGradeDate) {
        String path = "/target?renewalGradeDate" + renewalGradeDate;

        return objectMapper.convertValue(WebClient.builder()
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build()
                        .get()
                        .uri(serverConfig.getShoppingMallUrl() + ADVANCEMENT_URL + path)
                        .retrieve()
                        .toEntity(List.class)
                        .timeout(timeOut)
                        .blockOptional()
                        .orElseThrow(() -> new RequestFailureException(ERROR_MESSAGE)).getBody(),
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, AdvancementTargetResponseDto.class));
    }

    /**
     * {@inheritDoc}
     *
     * @throws RequestFailureException 서버로 보낸 요청이 실패하였습니다.
     */
    @Override
    public void renewalMemberGrade(RenewalMemberGradeRequestDto requestDto) {

        WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .post()
                .uri(serverConfig.getShoppingMallUrl() + ADVANCEMENT_URL)
                .bodyValue(requestDto)
                .retrieve()
                .toEntity(Void.class)
                .timeout(timeOut)
                .blockOptional()
                .orElseThrow(() -> new RequestFailureException(ERROR_MESSAGE)).getBody();
    }

    /**
     * {@inheritDoc}
     *
     * @throws RequestFailureException 서버로 보낸 요청이 실패하였습니다.
     */
    @Override
    public Long getAccumulatePurchaseAmount(Integer memberNo, LocalDate nextRenewalGradeDate) {
        String path = "/amount/" + memberNo + "?nextRenewalGradeDate=" + nextRenewalGradeDate;

        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .get()
                .uri(serverConfig.getShoppingMallUrl() + ADVANCEMENT_URL + path)
                .retrieve()
                .toEntity(Long.class)
                .timeout(timeOut)
                .blockOptional()
                .orElseThrow(() -> new RequestFailureException(ERROR_MESSAGE)).getBody();
    }
}
