package org.eulerframework.uc.hengrun.web.controller;

import org.eulerframework.web.core.annotation.ApiEndpoint;
import org.eulerframework.web.core.base.controller.ApiSupportWebController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ApiEndpoint
@RequestMapping("plugin/hengrun/info")
public class HengRunPluginInfoApi extends ApiSupportWebController {

    @GetMapping
    public String helloWorld() {
        return "Heng Run plugin enabled";
    }

}
