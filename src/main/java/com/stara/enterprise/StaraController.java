package com.stara.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaraController {
    /**
     * Handle the root (/) endpoint
     * @return a basic start page
     */
    @RequestMapping("/")
    public String index() {
        return "start";
    }
}
