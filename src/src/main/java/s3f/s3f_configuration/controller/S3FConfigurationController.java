package s3f.s3f_configuration.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.services.S3FConfigurationConstantService;
import s3f.s3f_configuration.services.S3FConfigurationService;
import sun.net.www.protocol.http.HttpURLConnection;

import java.util.List;

@RestController
@CrossOrigin
@Api(tags = "Application Configurations", value = "Shared Constants", description = "Key/Value List for Microservices")
public class S3FConfigurationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(S3FConfigurationController.class);
    @Autowired
    private S3FConfigurationService s3FConfigurationService;
    @Autowired
    private S3FConfigurationConstantService s3FConfigurationConstantService;

    @RequestMapping(value = "/api/v1/s3f-configuration/variable", method = RequestMethod.PUT)
    @ApiOperation(value = "Create a new configurarion variable", produces = "application/json", consumes = "application/json", notes = "" +
            "<h1>Create a new configuration</h1>\n" +
            "\t<h2>Rest call</h2>\n" +
            "\t{\n" +
            "\t<br /> \"keyValuePairs\": {\n" +
            "\t<br /> },\n" +
            "\t<br /> \"lifecycle\": \"string\",\n" +
            "\t<br /> \"service\": \"string\",\n" +
            "\t<br /> \"version\": \"string\"\n" +
            "\t<br /> }\n" +
            "\t<br />\n" +
            "\t<p>The rest call has got the parameter</p>\n" +
            "\t<ul>\n" +
            "\t\t<li>keyValuePairs</li>\n" +
            "\t\t<li>lifecycle</li>\n" +
            "\t\t<li>service</li>\n" +
            "\t\t<li>version</li>\n" +
            "\t</ul>\n" +
            "<h2>Description</h2>\n" +
            "\t<p>The key / value list</p>\n" +
            "\t<table style=\"border : \"1px solid; border-collapse:collapse;td{border:1px solid;\"}\">\n" +
            "\t\t<thead>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td style=\"border : 1px solid;\">key</td>\n" +
            "\t\t\t\t<td>value</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t</thead>\n" +
            "\t\t<tbody>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>s3f.amqp.system.host</td>\n" +
            "\t\t\t\t<td>192.168.8.103</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>s3f.amqp.system.username</td>\n" +
            "\t\t\t\t<td>s3f</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>s3f.amqp.system.password</td>\n" +
            "\t\t\t\t<td>s3f</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>s3f.amqp.system.port</td>\n" +
            "\t\t\t\t<td>5672</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>s3f.amqp.system.virtualHost</td>\n" +
            "\t\t\t\t<td>s3f-application</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>s3f.amqp.system.queue</td>\n" +
            "\t\t\t\t<td>s3f-Systemqueue</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>s3f.amqp.system.key</td>\n" +
            "\t\t\t\t<td>E</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t\t<tr>\n" +
            "\t\t\t\t<td>s3f.amqp.system.type</td>\n" +
            "\t\t\t\t<td>fanout</td>\n" +
            "\t\t\t</tr>\n" +
            "\t\t</tbody>\n" +
            "\t</table>")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration variable successful created", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration variable can't be saved.", response = HttpStatus.class)
    })
    public ResponseEntity<?> create(@RequestBody S3FConfigurationDto s3FConfigurationDto) {
        LOGGER.info("Create new Configuration variable");
        try {
            LOGGER.info(s3FConfigurationDto.toString());
            s3FConfigurationService.create(s3FConfigurationDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/variable/{service}/{version}/{lifecycle}", method = RequestMethod.GET)
    @ApiOperation(value = "Create configuration with constant replaced", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration found", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class)
    })
    public ResponseEntity getRoot(@PathVariable String service,
                                  @PathVariable String version,
                                  @PathVariable String lifecycle) {
        LOGGER.info("GET (single S3FConfiguration) " + service + " " + version + " " + lifecycle);
        try {
            final List<S3FConfigurationConstantDto> s3FConfigurationConstants = s3FConfigurationConstantService.readAll(version, lifecycle);
            final S3FConfiguration s3FConfiguration = s3FConfigurationService.read(service, version, lifecycle);
            return new ResponseEntity(s3FConfigurationService.build(s3FConfigurationConstants, s3FConfiguration), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/variable/{service}/{version}/{lifecycle}/{variableName}", method = RequestMethod.GET)
    @ApiOperation(value = "Get configuration variable", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration found", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class)
    })
    public ResponseEntity getConfigurationVariable(@PathVariable String service,
                                                   @PathVariable String version,
                                                   @PathVariable String lifecycle,
                                                   @PathVariable String variableName) {
        LOGGER.info("GET (single S3FConfiguration variable) " + service + " " + version + " " + lifecycle);
        try {
            final S3FConfiguration s3FConfiguration = s3FConfigurationService.readOneVariable(service, version, lifecycle, variableName);
            return new ResponseEntity(s3FConfiguration, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
