package vn.vnpt.ssdc.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.vnpt.ssdc.dto.AcsResponse;
import vn.vnpt.ssdc.utils.StringUtils;

import java.util.Map;

/**
 * Created by vietnq on 10/31/16.
 */
@Component
public class DeviceApiClient {

    private RestTemplate restTemplate;
    private String apiEndpointUrl;


    @Autowired
    public DeviceApiClient(@Value("${apiEndpointUrl}") String apiEndpointUrl) {
        this.restTemplate = new RestTemplate();
        this.apiEndpointUrl = apiEndpointUrl + "devices";
    }



    public AcsResponse findDevices(Map<String, String> queryParams) {
        String queryString = StringUtils.queryStringFromMap(queryParams);
        String url = this.apiEndpointUrl + "?" + queryString;
        return this.restTemplate.getForObject(url, AcsResponse.class, queryParams);
    }
}
