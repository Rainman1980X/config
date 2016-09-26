package s3f.s3f_configuration.controller;

import io.swagger.annotations.*;
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
    @ApiOperation(value = "Create a new configuration",
            produces = "application/json",
            consumes = "application/json",
            notes = "To create a new configuration all fields have to be filled up with information. " +
                    "The configuration, keyValuePairs, shall be complete formulated, because it will be store at once. ")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successful created", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be saved. May be a duplicate entry. ", response = HttpStatus.class)
    })
    @CrossOrigin
    public ResponseEntity<?> create(
            @ApiParam(value = "The created configuration sent from the gui. " +
                    "The parameter keyValuePairs holds the complete configuration definition. The keyValuePairs is a commaseperated array" +
                    "{\"VariableName1\" : \"VariableValue1\"," +
                    "\"VariableName2\" : \"VariableValue2\"}" +
                    "The parameter lifecycle holds the live cycle information develop, test or productive. " +
                    "The parameter service holds the name of the service which the service is acknowledged at the consul server. " +
                    "The parameter version holds the version of the service. Which is important to differ between the data structure definitions.", required = true)
            @RequestBody S3FConfigurationDto s3FConfigurationDto) {
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
    @ApiOperation(value = "Update a configuration", produces = "application/json",
            consumes = "application/json",
            notes = "To update a configuration all fields have to be filled up with information. " +
                    "The configuration, keyValuePairs, shall be complete formulated, because it will be store at once. " +
                    "The id of the data record has to be sent with the data record. The data record is identified only by this id.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Update a configuration was successful.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be saved. May be a duplicate entry. ", response = HttpStatus.class)
    })
    public ResponseEntity<?> update(@ApiParam(value = "The update configuration sent from the gui. " +
            "The is build up like the create parameter." +
            "The id of the data record is needed to identify the data record.", required = true)
                                    @RequestBody S3FConfiguration s3FConfigurationDto) {
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
    @ApiOperation(value = "Build configuration with constant replaced", produces = "application/json",
            consumes = "application/json",
            notes = "Both configuration constants and the configuration itself will be merged together and will deliver a complete configuration. " +
            "The configuration depends on the service, version and lifecyle.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration found", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class)
    })
    public ResponseEntity getRoot(
            @ApiParam(value = "The service parameter is sent from the gui. " +
            "The service is needed to identify the data record.", required = true) @PathVariable String service,
            @ApiParam(value = "The version parameter is sent from the gui. " +
            "The version is needed to identify the data record.", required = true) @PathVariable String version,
            @ApiParam(value = "The lifecycle parameter is sent from the gui. " +
            "The lifecycle is needed to identify the data record.", required = true) @PathVariable String lifecycle) {
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

    @RequestMapping(value = "/s3f-configuration/{service}/{version}/{lifecycle}/{variableName}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a configuration", produces = "application/json", consumes = "application/json",
            notes = "If a configuration will be deleted than the configuration will be deleted physically.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successfully deleted", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class)
    })
    public ResponseEntity deleteConfiguration(
            @ApiParam(value = "The service parameter is sent from the gui. " +
            "The service is needed to identify the data record.", required = true) @PathVariable String service,
            @ApiParam(value = "The version parameter is sent from the gui. " +
            "The version is needed to identify the data record.", required = true) @PathVariable String version,
            @ApiParam(value = "The lifecycle parameter is sent from the gui. " +
            "The lifecycle is needed to identify the data record.", required = true) @PathVariable String lifecycle) {
        LOGGER.info("DELETE a S3FConfiguration) " + service + " " + version + " " + lifecycle);
        try {
            s3FConfigurationService.delete(service, version, lifecycle);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
