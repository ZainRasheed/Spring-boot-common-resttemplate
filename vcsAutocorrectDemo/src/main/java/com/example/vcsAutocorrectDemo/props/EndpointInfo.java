package com.example.vcsAutocorrectDemo.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class EndpointInfo {

    private String name;

    //eg: https://dev.test-gsc.vfims.com/
    private String host;

    //Common project path
    private String contextPath;

    private String urlPath;

    private String authToken;
}
