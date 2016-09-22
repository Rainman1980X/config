package s3f.s3f_configuration.controller;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.services.S3FConfigurationConstantService;
import sun.net.www.protocol.http.HttpURLConnection;
//Todo PUT/POST falsch im LOG
@RestController
@Api(tags = "Shared Constants", value = "Shared Constants", description = "Simplify configuration")

public class S3FConfigurationConstantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(S3FConfigurationConstantController.class);
    @Autowired
    private S3FConfigurationConstantService s3FConfigurationConstantService;

    @RequestMapping(value = "/api/v1/s3f-configuration/constant", method = RequestMethod.PUT)
    @ApiOperation(value = "Create a new configurarion constant", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant successful created", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be saved.", response = HttpStatus.class)
    })
    public ResponseEntity<HttpStatus> create(@RequestBody S3FConfigurationConstantDto s3FConfigurationConstantDto) {
        LOGGER.info("PUT");
        try {
            LOGGER.info(s3FConfigurationConstantDto.toString());
            s3FConfigurationConstantService.create(s3FConfigurationConstantDto);
        } catch (Exception e) {
            LOGGER.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant/{constantName}/{version}/{lifecycle}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a configurarion constant", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be found.", response = HttpStatus.class)
    })
    public ResponseEntity read(@PathVariable String version,
                               @PathVariable String lifecycle,
                               @PathVariable String constantName) {
        LOGGER.info("GET (single S3FConfigurationConstant) " + version + " " + lifecycle + " " + constantName);
        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity(s3FConfigurationConstantService.read(version, lifecycle, constantName), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant", method = RequestMethod.POST)
    @ApiOperation(value = "Update a configurarion constant", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be found.", response = HttpStatus.class)
    })
    public ResponseEntity update(@RequestBody S3FConfigurationConstantDto s3FConfigurationConstant) {
        LOGGER.info("POST");
        ResponseEntity responseEntity;
        try {
            s3FConfigurationConstantService.update(s3FConfigurationConstant);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant/{version}/{lifecycle}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the list of configuration constants", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant list found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be found.", response = HttpStatus.class)
    })
    public ResponseEntity readAll(@PathVariable String version,
                                  @PathVariable String lifecycle) {

        LOGGER.info("GET (List S3FConfigurationConstant) " + version + " " + lifecycle);
        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity(s3FConfigurationConstantService.readAll(version, lifecycle), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant/{version}/{lifecycle}/{constantName}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Removes a constant", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant removed.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be removed.", response = HttpStatus.class)
    })
    public ResponseEntity delete(@PathVariable String version,
                                 @PathVariable String lifecycle,
                                 @PathVariable String constantName) {

        LOGGER.info("Delete  " + version + " " + lifecycle+ " " + constantName + " from the list");
        try {
            s3FConfigurationConstantService.remove(version, lifecycle,constantName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
