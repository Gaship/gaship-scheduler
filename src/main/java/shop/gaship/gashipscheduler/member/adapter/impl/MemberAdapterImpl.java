package shop.gaship.gashipscheduler.member.adapter.impl;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import shop.gaship.gashipscheduler.config.ServerConfig;
import shop.gaship.gashipscheduler.member.adapter.MemberAdapter;
import shop.gaship.gashipscheduler.member.dto.response.MemberResponseDto;


/**
 * MemberAdapter interface 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see shop.gaship.gashipscheduler.member.adapter.MemberAdapter
 */
@Component
@RequiredArgsConstructor
public class MemberAdapterImpl implements MemberAdapter {
    private final ServerConfig serverConfig;

    private static final String MEMBER_URL = "/api/members";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(serverConfig.getShoppingMallUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public Flux<MemberResponseDto> findMembersByRenewalDate(LocalDate renewalGradeDate) {
        UriBuilder uriBuilder = UriComponentsBuilder.newInstance();

        return webClient.get()
                .uri(uriBuilder.path(MEMBER_URL)
                        .queryParam("nextRenewalGradeDate", renewalGradeDate)
                        .build())
                .retrieve()
                .bodyToFlux(MemberResponseDto.class);
    }
}
