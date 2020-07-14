package com.example.vcsAutocorrectDemo.servers.handler;

import com.example.vcsAutocorrectDemo.props.EndpointInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class HttpClientHandler {

    @Getter
    private String name;

    @Getter
    private EndpointInfo endpointInfo;

    /*If needed
    private SSLContext sslContext;*/

    public HttpClientHandler(EndpointInfo endpointInfo){
        this.name = endpointInfo.getName();
        this.endpointInfo =  endpointInfo;
    }

    /**
     Create a sub class to create The COMMON REQUEST (This class contains properties of API.
     */
    @Getter
    @Setter
    public static class HttpRequest<T>{

        private HttpMethod method;

        private MultiValueMap<String, String> headers = new HttpHeaders();

        private String url;

        private Map<String, String> pathParameters = new HashMap<>();
        /**
         * String url = "http://www.sample.com?foo={fooValue}";
         *
         * Map<String, String> uriVariables = new HashMap<>();
         * uriVariables.put("fooValue", "2");
         *
         * // "http://www.sample.com?foo=2"
         * restTemplate.getForObject(url, Object.class, uriVariables);
         */

        private Object body;

        private Class<T> responseType;

        private ParameterizedTypeReference<T> parameterizedResponseType;

        public HttpRequest(HttpMethod method, /*MultiValueMap<String, String> headers,*/ String url, /*Map<String, String> pathParameters,*/ Object body, Class<T> responseType, ParameterizedTypeReference<T> parameterizedResponseType) {
//            super();
            this.method = method;
            this.url = url;
            /**
             Because every API might not have headers and parameters.*/
            /*this.headers = headers;
            this.pathParameters = pathParameters;*/
            this.body = body;
            this.responseType = responseType;
            this.parameterizedResponseType = parameterizedResponseType;
        }
    }

    public RestTemplate getRestTemplate(){
        /**
         * add IF ELSE to GET restTemplate with SSlContext and without SSL
         */
        return createRestTemplate();
    }

    private RestTemplate createRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    /**
     *       RETRY TEMPLATE
     *
     * <!-- https://mvnrepository.com/artifact/org.springframework.retry/spring-retry -->
     * <dependency>
     *     <groupId>org.springframework.retry</groupId>
     *     <artifactId>spring-retry</artifactId>
     *     <version>1.2.4.RELEASE</version>
     * </dependency>
     *
     *
     *     public RetryTemplate retryTemplate(EndpointInfo endpointInfo) {
     *         Map<String, Object> metadata = endpointInfo.getMetadata();
     *         Long backOffPeriod = nonNull(metadata) && metadata.containsKey(BACK_OFF_PERIOD) && nonNull(BACK_OFF_PERIOD) ? Long.parseLong(metadata.get(BACK_OFF_PERIOD).toString())  : 5000L;
     *         int maxAttempt = nonNull(metadata) && metadata.containsKey(MAX_ATTEMPT) && nonNull(MAX_ATTEMPT) ? (int) metadata.get(MAX_ATTEMPT) : 3;
     *         RetryTemplate retryTemplate = new RetryTemplate();
     *         FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
     *         fixedBackOffPolicy.setBackOffPeriod(backOffPeriod);
     *         retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
     *         SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(maxAttempt, singletonMap(HttpHostConnectException.class, true), true);
     *         retryTemplate.setRetryPolicy(retryPolicy);
     *         retryTemplate.registerListener(retryListeners());
     *         return retryTemplate;
     *     }
     *
     *     private RetryListener retryListeners() {
     *         return new RetryListenerSupport() {
     *             @Override
     *             public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
     *                 log.warn("[RETRY_COUNT:: {}] " + EXCEPTION_OCCURRED_MSG, context.getRetryCount(), throwable.getMessage());
     *             }
     *         };
     *     }
     *
     *
     *     ------------------
     *     In place of resttemplate.exchange
     *
     *     retryTemplate.execute(retryContext -> restTemplate.exchange(url, request.getMethod(),
     *                         new HttpEntity(request.getBody(), request.getHeaders()),
     *                         request.getResponseType(),
     *                         request.getPathParameters()));
     *
     *
     */
}
