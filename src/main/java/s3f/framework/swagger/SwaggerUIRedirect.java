package s3f.framework.swagger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SwaggerUIRedirect {

    // Für .NET kommen wir normalerweise immer auf /swagger oder /swagger/ raus um das Swagger-UI zu sehen
    // springfox-swagger-ui bietet das aber nur unter /swagger-ui.html an. Deshalb hier für mehr Komfort ein Redirect.
    @RequestMapping(path = "/swagger", method = RequestMethod.GET)
    public ModelAndView redirect(HttpServletRequest request) {
        return new ModelAndView("redirect:" + request.getRequestURL().toString().replaceAll("swagger/?", "swagger-ui.html"));
    }

}
