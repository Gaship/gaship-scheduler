package shop.gaship.gashipscheduler.domain.membergrade.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import shop.gaship.gashipscheduler.config.ServerConfig;
import shop.gaship.gashipscheduler.domain.membergrade.adapter.MemberGradeAdapter;
import shop.gaship.gashipscheduler.domain.membergrade.dto.response.MemberGradeResponseDto;

/**
 * MemberGradeAdapter interface 구현체.
 *
 * @author : 김세미
 * @see MemberGradeAdapter
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberGradeAdapterImpl implements MemberGradeAdapter {

    private final ServerConfig serverConfig;
    private static final String MEMBER_GRADE_RUL = "/api/member-grades";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(serverConfig.getShoppingMallUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public Flux<MemberGradeResponseDto> findMemberGrades() {
        UriBuilder uriBuilder = UriComponentsBuilder.newInstance();

        return webClient.get()
                .uri(uriBuilder.path(MEMBER_GRADE_RUL)
                        .build())
                .retrieve()
                .bodyToFlux(MemberGradeResponseDto.class);
    }
}
