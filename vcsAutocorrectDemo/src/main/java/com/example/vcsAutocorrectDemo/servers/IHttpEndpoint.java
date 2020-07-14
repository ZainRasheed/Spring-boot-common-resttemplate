package com.example.vcsAutocorrectDemo.servers;

import org.springframework.core.ParameterizedTypeReference;

import java.util.Map;

public interface IHttpEndpoint {

    <T> T get(String url, Map<String, String> headers, Map<String, String> pathVariables, Class<T> responseType);

    <T> T get(String url, Map<String, String> headers, Map<String, String> pathVariables, ParameterizedTypeReference<T> responseType);

    <T> T patch(String url, Map<String, String> headers, Map<String, String> pathVariables, Object payload, Class<T> responseType);

    <T> T patch(String url, Map<String, String> headers, Map<String, String> pathVariables, Object payload, ParameterizedTypeReference<T> responseType);
}
