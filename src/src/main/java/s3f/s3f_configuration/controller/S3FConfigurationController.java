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
@RequestMapping(value = "/api/v1")
@Api(tags = "Application Configurations", value = "Shared Constants", description = "Key/Value List for Microservices")
public class S3FConfigurationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(S3FConfigurationController.class);
    @Autowired
    private S3FConfigurationService s3FConfigurationService;
    @Autowired
    private S3FConfigurationConstantService s3FConfigurationConstantService;

    @RequestMapping(value = "/s3f-configuration", method = RequestMethod.PUT)
    @ApiOperation(value = "Create a new configurarion variable", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration variable successful created", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration variable can't be saved. May be a duplicate entry. ", response = HttpStatus.class)
    })
    @CrossOrigin
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

    @RequestMapping(value = "/s3f-configuration", method = RequestMethod.POST)
    @ApiOperation(value = "Update a configuration", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Update a configuratione successful created", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be saved. May be a duplicate entry. ", response = HttpStatus.class)
    })
    public ResponseEntity<?> update(@RequestBody S3FConfiguration s3FConfigurationDto) {
        LOGGER.info("Update Configuration");
        try {
            LOGGER.info(s3FConfigurationDto.toString());
            s3FConfigurationService.update(s3FConfigurationDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/s3f-configuration/{service}/{version}/{lifecycle}", method = RequestMethod.GET)
    @ApiOperation(value = "Build configuration with constant replaced", produces = "application/json", consumes = "application/json")
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

    @RequestMapping(value = "/s3f-configuration/{service}/{version}/{lifecycle}/{variableName}", method = RequestMethod.GET)
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
