package s3f.framework.consul;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import s3f.Application;

@RequestMapping("/ping")
@RestController
public class PingController {

    @RequestMapping(method = RequestMethod.GET, produces = "text/plain")
    public String ping() {
        return Application.lifecycle;
    }

}
