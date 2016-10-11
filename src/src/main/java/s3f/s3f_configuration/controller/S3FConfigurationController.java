package s3f.s3f_configuration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import s3f.framework.logger.LoggerHelper;
import s3f.s3f_configuration.action.constants.ReadAllConstantAction;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.dto.S3FConfigurationRootDto;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;
import s3f.s3f_configuration.services.S3FConfigurationService;
import sun.net.www.protocol.http.HttpURLConnection;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = "Application Configurations", value = "Shared Constants", description = "Key/Value List for Microservices")
public class S3FConfigurationController {

    @Autowired
    private S3FConfigurationService s3FConfigurationService;

    @Autowired
    private S3FConfigurationConstantRepository s3fConfigurationConstantRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/s3f-configuration", method = RequestMethod.PUT)
    @ApiOperation(value = "Create a new configuration", produces = "application/json", consumes = "application/json", notes = "To create a new configuration all fields have to be filled up with information. "
	    + "The configuration, keyValuePairs, shall be complete formulated, because it will be store at once. ")
    @ApiResponses(value = {
	    @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successful created", response = HttpStatus.class),
	    @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be saved. May be a duplicate entry. ", response = HttpStatus.class) })
    @CrossOrigin
    public ResponseEntity<HttpStatus> create(@RequestHeader(value = "Authorization") String authorization,
	    @RequestHeader(value = "CorrelationToken") String correlationToken,
	    @ApiParam(value = "The created configuration sent from the gui. "
		    + "The parameter keyValuePairs holds the complete configuration definition. The keyValuePairs is a commaseperated array"
		    + "{\"VariableName1\" : \"VariableValue1\"," + "\"VariableName2\" : \"VariableValue2\"}"
		    + "The parameter lifecycle holds the live cycle information develop, test or productive. "
		    + "The parameter service holds the name of the service which the service is acknowledged at the consul server. "
		    + "The parameter version holds the version of the service. Which is important to differ between the data structure definitions.", required = true) @RequestBody S3FConfigurationDto s3FConfigurationDto) {
	LoggerHelper.logData(Level.INFO, "Create configuration", "", "", S3FConfigurationController.class.getName());
	try {
	    LoggerHelper.logData(Level.INFO, "Create configuration", "", "",
		    S3FConfigurationController.class.getName());
	    s3FConfigurationService.create(s3FConfigurationDto);
	    return new ResponseEntity<>(HttpStatus.OK);
	} catch (Exception e) {
	    LoggerHelper.logData(Level.ERROR, "Create configuration", "", "",
		    S3FConfigurationController.class.getName(), e);
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @RequestMapping(value = "/s3f-configuration", method = RequestMethod.POST)
    @ApiOperation(value = "Update a configuration", produces = "application/json", consumes = "application/json", notes = "To update a configuration all fields have to be filled up with information. "
	    + "The configuration, keyValuePairs, shall be complete formulated, because it will be store at once. "
	    + "The id of the data record has to be sent with the data record. The data record is identified only by this id.")
    @ApiResponses(value = {
	    @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Update a configuration was successful.", response = HttpStatus.class),
	    @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be saved. May be a duplicate entry. ", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> update(@RequestHeader(value = "Authorization") String authorization,
	    @RequestHeader(value = "CorrelationToken") String correlationToken,
	    @ApiParam(value = "The update configuration sent from the gui. "
		    + "The is build up like the create parameter."
		    + "The id of the data record is needed to identify the data record.", required = true) @RequestBody S3FConfiguration s3FConfigurationDto) {
	LoggerHelper.logData(Level.INFO, "Update Configuration", "", "", S3FConfigurationController.class.getName());
	try {
	    s3FConfigurationService.update(s3FConfigurationDto);
	    return new ResponseEntity<>(HttpStatus.OK);
	} catch (Exception e) {
	    LoggerHelper.logData(Level.ERROR, "Update Configuration", "", "",
		    S3FConfigurationController.class.getName(), e);
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @RequestMapping(value = "/s3f-configuration/{service}/{version}/{lifecycle}", method = RequestMethod.GET)
    @ApiOperation(value = "Build configuration with constant replaced", produces = "application/json", consumes = "application/json", notes = "Both configuration constants and the configuration itself will be merged together and will deliver a complete configuration. "
	    + "The configuration depends on the service, version and lifecyle.")
    @ApiResponses(value = {
	    @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration found", response = HttpStatus.class),
	    @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class) })
    public ResponseEntity<S3FConfigurationRootDto> getRoot(@RequestHeader(value = "Authorization") String authorization,
	    @RequestHeader(value = "CorrelationToken") String correlationToken,
	    @ApiParam(value = "The service parameter is sent from the gui. "
		    + "The service is needed to identify the data record.", required = true) @PathVariable String service,
	    @ApiParam(value = "The version parameter is sent from the gui. "
		    + "The version is needed to identify the data record.", required = true) @PathVariable String version,
	    @ApiParam(value = "The lifecycle parameter is sent from the gui. "
		    + "The lifecycle is needed to identify the data record.", required = true) @PathVariable String lifecycle) {
	LoggerHelper.logData(Level.INFO, "GET (single S3FConfiguration) " + service + " " + version + " " + lifecycle,
		"", "", S3FConfigurationController.class.getName());
	try {
	    Map<String, String> httpsValues = new HashMap<>();
	    httpsValues.put("version", version);
	    httpsValues.put("lifecycle", lifecycle);
	    final List<S3FConfigurationConstantDto> s3FConfigurationConstants = (new ReadAllConstantAction())
		    .doActionOnConstant(s3fConfigurationConstantRepository, mongoTemplate, authorization,
			    correlationToken, httpsValues)
		    .getBody();
	    final S3FConfiguration s3FConfiguration = s3FConfigurationService.read(service, version, lifecycle);
	    return new ResponseEntity(s3FConfigurationService.build(s3FConfigurationConstants, s3FConfiguration),
		    HttpStatus.OK);
	} catch (Exception e) {
	    LoggerHelper.logData(Level.ERROR,
		    "GET (single S3FConfiguration) " + service + " " + version + " " + lifecycle, "", "",
		    S3FConfigurationController.class.getName(), e);
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @RequestMapping(value = "/s3f-configuration/{service}/{version}/{lifecycle}/{variableName}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a configuration", produces = "application/json", consumes = "application/json", notes = "If a configuration will be deleted than the configuration will be deleted physically.")
    @ApiResponses(value = {
	    @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successfully deleted", response = HttpStatus.class),
	    @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> deleteConfiguration(@RequestHeader(value = "Authorization") String authorization,
	    @RequestHeader(value = "CorrelationToken") String correlationToken,
	    @ApiParam(value = "The service parameter is sent from the gui. "
		    + "The service is needed to identify the data record.", required = true) @PathVariable String service,
	    @ApiParam(value = "The version parameter is sent from the gui. "
		    + "The version is needed to identify the data record.", required = true) @PathVariable String version,
	    @ApiParam(value = "The lifecycle parameter is sent from the gui. "
		    + "The lifecycle is needed to identify the data record.", required = true) @PathVariable String lifecycle) {
	LoggerHelper.logData(Level.INFO,
		"delete (single S3FConfiguration) " + service + " " + version + " " + lifecycle, "", "",
		S3FConfigurationController.class.getName());
	try {
	    s3FConfigurationService.delete(service, version, lifecycle);
	    return new ResponseEntity(HttpStatus.OK);
	} catch (Exception e) {
	    LoggerHelper.logData(Level.INFO,
		    "GET (single S3FConfiguration) " + service + " " + version + " " + lifecycle, "", "",
		    S3FConfigurationController.class.getName(), e);
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @RequestMapping(value = "/s3f-configuration", method = RequestMethod.GET)
    @ApiOperation(value = "Build all configuration with constant replaced", produces = "application/json", consumes = "application/json", notes = "Both configuration constants and the configuration itself will be merged together and will deliver a complete configuration. "
	    + "The configuration depends on the service, version and lifecyle.")
    @ApiResponses(value = {
	    @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration found", response = HttpStatus.class),
	    @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> getRootAll(@RequestHeader(value = "Authorization") String authorization,
	    @RequestHeader(value = "CorrelationToken") String correlationToken) {
	LoggerHelper.logData(Level.INFO, "GET (all S3FConfiguration) ", "", "",
		S3FConfigurationController.class.getName());
	try {
	    final List<S3FConfigurationConstantDto> s3FConfigurationConstants = (new ReadAllConstantAction())
		    .doActionOnConstant(s3fConfigurationConstantRepository, mongoTemplate, authorization,
			    correlationToken, new HashMap<>())
		    .getBody();
	    final List<S3FConfiguration> s3FConfigurations = s3FConfigurationService.readAll();

	    return new ResponseEntity(s3FConfigurationService.build(s3FConfigurationConstants, s3FConfigurations),
		    HttpStatus.OK);
	} catch (Exception e) {
	    LoggerHelper.logData(Level.ERROR, "GET (single S3FConfiguration) ", "", "",
		    S3FConfigurationController.class.getName(), e);
	    return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }
}
