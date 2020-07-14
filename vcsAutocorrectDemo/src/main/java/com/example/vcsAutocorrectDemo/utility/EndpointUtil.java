package com.example.vcsAutocorrectDemo.utility;


import com.example.vcsAutocorrectDemo.props.EndpointInfo;
import org.springframework.http.HttpHeaders;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class EndpointUtil {

    private static final String GSC_HOST = "https://dev.test-gsc.vfims.com/internal";
    private static final String CONTEXTPATH_ENTITY = "/ds-entity-service";
    private static final String CONTEXTPATH_PDSP_POST = "/transaction-service";
    private static final String CONTEXTPATH_PDSP_GET = "/dsapiquery";
    private static final String URL_PDSP_POST = "/ds/api/transactions";
    private static final String URL_PDSP_GET = "/getTransactions";
    private static final String URL_ACQUIRER = "/entities";
    private static final String URL_MERCHANT = "/paymentContracts";
    private static final String AUTH = "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NjNENzQ1NC1CRTkwLTQ1NjMtODBDMi03M0E1NTZCRUQzQTciLCJpYXQiOjE1MTYyMzkwMjIsImlzcyI6Imh0dHBzOi8vdmZpYXV0aC52ZXJpZm9uZS5jb20vb3BlbmFtL29hdXRoMiIsIm5hbWUiOiJKb2huIERvZSIsImVtYWlsIjoiam9obi5kb2VAZXhhbXBsZS5jb20iLCJlbnRpdHlfaWQiOiI3NmU4NGRhYS1jOTU0LTRjNmEtOGY3Zi0wOTc1OGMwNzg2NzAiLCJyb2xlcyI6WyJNRVJDSEFOVF9BRE1JTiJdLCJleHAiOiIxNTE2MjM5MDIyIiwiYXVkIjoiVmVyaWZvbmUgR1NDIn0.Dc1TYy17GkRtfHm5Np-beBCEaNlyPLmVzH-FsB267YEfTtx0QvlLBkNw3U62vJgMZgGz252mWiBtzrDWSw387A5_whTskxyO7_GQeBGxNZH8TcG6qaJmfbRopvod6iI9ORB-zVZ8JoGxRPLUlinIiMxayhuauaimdx_m5k8Iu9c";
    private static EndpointInfo endPoint;
    public static final String SLASH = "/";
    public static final String QUESTION = "?";
    public static final String AND = "&";
    public static final String EQ = "=";
    public static final String EMPTY = "";

    public enum Names{
        ACQUIRER,
        MERCHANTS,
        TRANSACTIONS_GET,
        TRANSACTIONS_POST
    }

    public static EndpointInfo getEndpoints(Names serviceName) {
        endPoint = new EndpointInfo();
        endPoint.setHost(GSC_HOST);
        endPoint.setAuthToken(AUTH);
        endPoint.setName(serviceName.name());
        switch (serviceName) {
            case ACQUIRER:
                endPoint.setContextPath(CONTEXTPATH_ENTITY);
                endPoint.setUrlPath(URL_ACQUIRER);
                break;
            case MERCHANTS:
                endPoint.setContextPath(CONTEXTPATH_ENTITY);
                endPoint.setUrlPath(URL_MERCHANT);
                break;
            case TRANSACTIONS_GET:
                endPoint.setContextPath(CONTEXTPATH_PDSP_GET);
                endPoint.setUrlPath(URL_PDSP_GET);
                break;
            case TRANSACTIONS_POST:
                endPoint.setContextPath(CONTEXTPATH_PDSP_POST);
                endPoint.setUrlPath(URL_PDSP_POST);
                break;
        }
        return endPoint;
    }

    public static String getCreatedURL(EndpointInfo endpointInfo, String urlExtension) {
        return new StringBuilder(endpointInfo.getHost())
                .append(endpointInfo.getContextPath())
                .append(endpointInfo.getUrlPath())
                .append(urlExtension)
                .toString();
    }

    public static Map<String, String> prepareHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers.toSingleValueMap();
    }

}
