package vn.vnpt.ssdc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

/**
 * Created by vietnq on 10/20/16.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("devices",new RestTemplate().getForObject("http://localhost:8090/devices",String.class));
        return "index";
    }
}
