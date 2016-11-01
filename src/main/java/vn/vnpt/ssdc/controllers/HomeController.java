package vn.vnpt.ssdc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.vnpt.ssdc.api.client.DeviceApiClient;
import vn.vnpt.ssdc.dto.AcsResponse;
import vn.vnpt.ssdc.model.Device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vietnq on 10/20/16.
 */
@Controller
public class HomeController {

    @Autowired
    private DeviceApiClient deviceApiClient;

    @GetMapping("/")
    public String index(Model model) {
        Map<String,String> indexParams = indexParams();

        //query devices from acs
        Map<String,String> query = new HashMap<String,String>();
        query.put("parameters", String.join(",", indexParams.keySet()));
        //TODO add paging param

        AcsResponse response = deviceApiClient.findDevices(query);

        List<Device> devices = Device.fromJsonString(response.body,indexParams.keySet());

        //add render args
        model.addAttribute("total",response.nbOfItems);
        model.addAttribute("indexParams",indexParams);
        model.addAttribute("devices", devices);
        return "index";
    }

    //returns a map containing friendly name and actual path for parameters shown in device index view
    //TODO move this to configuration
    private Map<String,String> indexParams() {
        return new HashMap<String,String>() {{
            put("_deviceId._SerialNumber","Serial Number");
            put("_deviceId._Manufacturer","Manufacturer");
            put("_deviceId._ProductClass","Product Class");
            put("_lastInform","Last Inform");
        }};
    }
}
