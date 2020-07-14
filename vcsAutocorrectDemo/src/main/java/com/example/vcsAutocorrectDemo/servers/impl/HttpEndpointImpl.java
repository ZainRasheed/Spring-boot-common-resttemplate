package com.example.vcsAutocorrectDemo.servers.impl;

import com.example.vcsAutocorrectDemo.props.EndpointInfo;
import com.example.vcsAutocorrectDemo.servers.IHttpEndpoint;
import com.example.vcsAutocorrectDemo.servers.handler.HttpClientHandler;
import com.example.vcsAutocorrectDemo.servers.handler.HttpClientHandler.HttpRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;

//@Component
public class HttpEndpointImpl implements IHttpEndpoint {

    private HttpClientHandler httpClientHandler;

    private EndpointInfo endpointInfo;

    public HttpEndpointImpl(EndpointInfo endpointInfo/*, HttpClientHandler httpClientHandler*/){
        this.endpointInfo = endpointInfo;
        this.httpClientHandler = new HttpClientHandler(endpointInfo);
        /*this.httpClientHandler = httpClientHandler;*/
    }

    private  <T> T send(HttpRequest<T> request, SSLContext wontBeUsedNow) {
            return send(httpClientHandler, request, wontBeUsedNow);
    }

    private  <T> T send(HttpClientHandler httpClientHandler, HttpRequest<T> request, SSLContext wontBeUsedNow) {
        String url = request.getUrl();
        System.out.printf("Connecting... [HTTP_METHOD:- %s] [URL:- %s]\n", request.getMethod(), url);
        RestTemplate restTemplate = httpClientHandler.getRestTemplate();

        /** FOR SSL
         * RestTemplate restTemplate = Objects.isNull(sslContext) ? handler.getRestTemplate()
         *                 : handler.getRestTemplate(sslContext);
         */
        /** For RETRY TEMPLATE
         *        RetryTemplate retryTemplate = handler.getRetryTemplate
         *
         *        ResponseEntity<T> response;
         *         if (nonNull(request.getResponseType())) {
         *             if (getEndpointInfo().isRetryRequired()) {
         *                 response = retryTemplate.execute(retryContext -> restTemplate.exchange(url, request.getMethod(),
         *                         new HttpEntity(request.getBody(), request.getHeaders()),
         *                         request.getResponseType(),
         *                         request.getPathParameters()));
         *             } else {
         *                 response = restTemplate.exchange(url, request.getMethod(),
         *                         new HttpEntity(request.getBody(), request.getHeaders()),
         *                         request.getResponseType(),
         *                         request.getPathParameters());
         *             }
         */

        ResponseEntity<T> response;
        if (nonNull(request.getResponseType())) {
            response = restTemplate.exchange(url,
                    request.getMethod(),
                    new HttpEntity(request.getBody(), request.getHeaders()),
                    request.getResponseType(),
                    request.getPathParameters());
        } else {
            response = restTemplate.exchange(url,
                    request.getMethod(),
                    new HttpEntity(request.getBody(), request.getHeaders()),
                    request.getParameterizedResponseType(),
                    request.getPathParameters());
        }
        T body = getBody(response);
        System.out.printf("Received %s response code. \n", response.getStatusCode());
        return body;
    }

    private <T> T getBody(ResponseEntity<T> response) {
        T body = response.getBody();
        if (Objects.isNull(body)) {
            return (T) "{}";
        }
        return body;
    }

    private  <T> HttpRequest<T> createRequest(HttpMethod method, String url, Map<String, String > headers, Map<String,String> pathParameters, Class<T> responseType, ParameterizedTypeReference<T> parameterizedResponseType) {
        HttpRequest<T> request = new HttpRequest<>(method, url, null, responseType, parameterizedResponseType);
        if (nonNull(headers)) {
            headers.forEach(request.getHeaders()::add);
        }
        if (nonNull(parameterizedResponseType)) {
            request.getPathParameters().putAll(pathParameters);
        }
        return request;
    }

    @Override
    public <T> T get(String url, Map<String, String> headers, Map<String, String> pathVariables, Class<T> responseType) {
        HttpRequest<T> request = createRequest(HttpMethod.GET, url, headers, pathVariables, responseType, null);
        return send(request, null);
    }

    @Override
    public <T> T get(String url, Map<String, String> headers, Map<String, String> pathVariables, ParameterizedTypeReference<T> responseType) {
        HttpRequest<T> request = createRequest(HttpMethod.GET, url, headers, pathVariables, null, responseType);
        return send(request, null);    }

    @Override
    public <T> T patch(String url, Map<String, String> headers, Map<String, String> pathVariables, Object payload, Class<T> responseType) {
        HttpRequest<T> request = createRequest(HttpMethod.PATCH, url, headers, pathVariables, responseType, null);
        request.setBody(payload);
        return send(request, null);
    }

    @Override
    public <T> T patch(String url, Map<String, String> headers, Map<String, String> pathVariables, Object payload, ParameterizedTypeReference<T> responseType) {
        return null;
    }
}
