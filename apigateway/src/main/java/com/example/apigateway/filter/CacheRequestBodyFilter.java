package com.example.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Component
public class CacheRequestBodyFilter extends AbstractGatewayFilterFactory<CacheRequestBodyFilter.Config> {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public CacheRequestBodyFilter(ReactiveRedisTemplate<String, String> redisTemplate) {
        super(Config.class);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("cacheName", "timeToLive");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String cacheKey = generateCacheKey(exchange);

            return redisTemplate.opsForValue().get(cacheKey)
                    .flatMap(cachedResponse -> {
                        exchange.getResponse().getHeaders().add("X-Cache", "HIT");
                        return exchange.getResponse().writeWith(
                                Mono.just(exchange.getResponse().bufferFactory().wrap(cachedResponse.getBytes())));
                    })
                    .switchIfEmpty(
                            chain.filter(exchange)
                                    .then(Mono.fromRunnable(() -> {
                                        exchange.getResponse().getHeaders().add("X-Cache", "MISS");
                                        String responseBody = exchange.getResponse().bufferFactory().toString();
                                        redisTemplate.opsForValue()
                                                .set(cacheKey, responseBody, Duration.ofSeconds(config.timeToLive))
                                                .subscribe();
                                    })));
        };
    }

    private String generateCacheKey(org.springframework.web.server.ServerWebExchange exchange) {
        return exchange.getRequest().getPath().value() + ":" +
                exchange.getRequest().getMethod() + ":" +
                exchange.getRequest().getQueryParams().toString();
    }

    public static class Config {
        private String cacheName;
        private long timeToLive;

        public String getCacheName() {
            return cacheName;
        }

        public void setCacheName(String cacheName) {
            this.cacheName = cacheName;
        }

        public long getTimeToLive() {
            return timeToLive;
        }

        public void setTimeToLive(long timeToLive) {
            this.timeToLive = timeToLive;
        }
    }
}