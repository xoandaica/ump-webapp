package vn.vnpt.ssdc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.vnpt.ssdc.api.client.DeviceTypeClient;
import vn.vnpt.ssdc.api.model.DeviceType;
import vn.vnpt.ssdc.controllers.decorator.DeviceTypeDecorator;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vietnq on 11/2/16.
 */
@Controller
public class DeviceTypeController {
    private Logger logger = LoggerFactory.getLogger(DeviceTypeController.class);

    @Autowired
    private DeviceTypeClient deviceTypeClient;

    @GetMapping("/device-types")
    public String index(Model model) {
        DeviceType[] deviceTypes = deviceTypeClient.findAll();
        model.addAttribute("deviceTypes",deviceTypes);
        model.addAttribute("deviceTypeDecorator",new DeviceTypeDecorator());
        return "device_type";
    }

    @PostMapping("/device-types")
    public String createDeviceType(Model model, @ModelAttribute DeviceTypeDecorator deviceTypeDecorator) {
        DeviceType created = deviceTypeClient.create(deviceTypeDecorator.toDeviceType());
        return index(model);
    }


}
