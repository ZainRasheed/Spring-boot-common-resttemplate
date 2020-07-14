package com.example.vcsAutocorrectDemo;

import com.example.vcsAutocorrectDemo.props.EndpointInfo;
import com.example.vcsAutocorrectDemo.servers.impl.HttpEndpointImpl;
import org.springframework.web.bind.annotation.*;

import static com.example.vcsAutocorrectDemo.utility.EndpointUtil.*;
import static com.example.vcsAutocorrectDemo.utility.EndpointUtil.Names.ACQUIRER;

@RestController
@RequestMapping("/acquirer")
public class AcquirerController {

    private EndpointInfo entityServiceEndpoint = getEndpoints(ACQUIRER);

//    @Autowired
    private HttpEndpointImpl httpEndpoint = new HttpEndpointImpl(entityServiceEndpoint);

    @GetMapping("/get/{acquirerName}")
    public Object getEntity(@PathVariable(name = "acquirerName") String acquirerName){
        return httpEndpoint.get(
                getCreatedURL(entityServiceEndpoint, QUESTION + "name" + EQ + acquirerName),
                prepareHeaders(entityServiceEndpoint.getAuthToken()),
                null,
                Object.class);
    }

    @PatchMapping("/patch/{acquirerId}")
    public Object patchEntity(@RequestBody Object PatchedData, @PathVariable String acquirerId) {
        return httpEndpoint.patch(
                getCreatedURL(entityServiceEndpoint, SLASH + acquirerId),
                prepareHeaders(entityServiceEndpoint.getAuthToken()),
                null,
                PatchedData,
                Object.class);
    }
}
