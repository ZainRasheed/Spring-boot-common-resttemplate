package com.example.vcsAutocorrectDemo.controller;

import com.example.vcsAutocorrectDemo.props.EndpointInfo;
import com.example.vcsAutocorrectDemo.servers.impl.HttpEndpointImpl;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;

import static com.example.vcsAutocorrectDemo.utility.EndpointUtil.*;
import static com.example.vcsAutocorrectDemo.utility.EndpointUtil.Names.MERCHANTS;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    private static final String TYPE = "type";
    private static final String POP_ES = "populateEntity";
    private static final String START = "startSettlementTime";
    private static final String END = "endSettlementTime";
    private static final String SETT_TYPE = "settlementType";

    private EndpointInfo merchantEndpointInfo = getEndpoints(MERCHANTS);

    private HttpEndpointImpl httpEndpoint = new HttpEndpointImpl(merchantEndpointInfo);

    @GetMapping("/get/{contractId}")
    public Object getMerchant(@PathVariable(value = "contractId") String contractId) {
        return httpEndpoint.get(
                getCreatedURL(merchantEndpointInfo, SLASH + contractId),
                prepareHeaders(merchantEndpointInfo.getAuthToken()),
                null,
                Object.class);
    }

    @GetMapping("/get/bulk")
    public Object getAllMerchants(
            @RequestParam(value = TYPE, required = true) String type,
            @RequestParam(value = POP_ES, required = true) Boolean populateEntity,
            @RequestParam(value = START, required = true) String startSettlementTime,
            @RequestParam(value = END, required = true) String endSettlementTime,
            @RequestParam(value = SETT_TYPE, required = true) String settlementType){

        return httpEndpoint.get(
                getCreatedURL(merchantEndpointInfo, QUESTION + TYPE + EQ + type + AND + POP_ES + EQ + populateEntity + AND + START + EQ + startSettlementTime + AND + END + EQ + endSettlementTime + AND + SETT_TYPE + EQ + settlementType),
                prepareHeaders(merchantEndpointInfo.getAuthToken()),
                null,
                new ParameterizedTypeReference<Object>() {});
    }

    @PatchMapping("/patch/{contractUid}")
    public Object patchMerchant(@RequestBody Object patchedData, @PathVariable(name = "contractUid") String contractUid) {
        return httpEndpoint.patch(
                getCreatedURL(merchantEndpointInfo, SLASH + contractUid),
                prepareHeaders(merchantEndpointInfo.getAuthToken()),
                null,
                patchedData,
                Object.class);
    }

    @PatchMapping("/patch/settlementInfo/{contractId}")
    public Object patchMerchantSettlementData(@RequestBody Object patchedData, @PathVariable("contractId") String contractId) {
        return httpEndpoint.patch(
                getCreatedURL(merchantEndpointInfo, SLASH + contractId + SLASH + "settlement"),
                prepareHeaders(merchantEndpointInfo.getAuthToken()),
                null,
                patchedData,
                Object.class);
    }
}
