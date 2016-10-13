package s3f.s3f_configuration.controller;

import java.net.HttpURLConnection;
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
import s3f.s3f_configuration.action.configuration.ConvertData;
import s3f.s3f_configuration.action.configuration.CreateConfigurationAction;
import s3f.s3f_configuration.action.configuration.DeleteConfigurationAction;
import s3f.s3f_configuration.action.configuration.EditConfigurationAction;
import s3f.s3f_configuration.action.configuration.GetAllConfigurationAction;
import s3f.s3f_configuration.action.configuration.GetCompiledConfigurationAction;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = "Application Configurations", value = "Shared Constants", description = "Key/Value List for Microservices")
public class S3FConfigurationController {

    @Autowired
    private S3FConfigurationRepository configurationRepository;
    @Autowired
    private S3FConfigurationConstantRepository configurationConstantRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/s3f-configuration", method = RequestMethod.PUT)
    @ApiOperation(value = "Create a new configuration", produces = "application/json", consumes = "application/json", notes = "To create a new configuration all fields have to be filled up with information. "
            + "The configuration, keyValuePairs, shall be complete formulated, because it will be store at once. ")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successful created", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Configuration duplicate found", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be saved. May be a duplicate entry. ", response = HttpStatus.class) })
    @CrossOrigin
    public ResponseEntity<S3FConfigurationDto> create(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @ApiParam(value = "The created configuration sent from the gui. "
                    + "The parameter keyValuePairs holds the complete configuration definition. The keyValuePairs is a commaseperated array"
                    + "{\"VariableName1\" : \"VariableValue1\"," + "\"VariableName2\" : \"VariableValue2\"}"
                    + "The parameter lifecycle holds the live cycle information develop, test or productive. "
                    + "The parameter service holds the name of the service which the service is acknowledged at the consul server. "
                    + "The parameter version holds the version of the service. Which is important to differ between the data structure definitions.", required = true) @RequestBody S3FConfigurationDto s3FConfigurationDto) {
        return (new CreateConfigurationAction()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                authorization, correlationToken, s3FConfigurationDto);
    }

    @RequestMapping(value = "/s3f-configuration", method = RequestMethod.POST)
    @ApiOperation(value = "Update a configuration", produces = "application/json", consumes = "application/json", notes = "To update a configuration all fields have to be filled up with information. "
            + "The configuration, keyValuePairs, shall be complete formulated, because it will be store at once. "
            + "The id of the data record has to be sent with the data record. The data record is identified only by this id.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Update a configuration was successful.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be saved. May be a duplicate entry. ", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> edit(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @ApiParam(value = "The update configuration sent from the gui. "
                    + "The is build up like the create parameter."
                    + "The id of the data record is needed to identify the data record.", required = true) @RequestBody S3FConfigurationDto s3FConfigurationDto) {
        return (new EditConfigurationAction()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                authorization, correlationToken, s3FConfigurationDto);
    }

    @RequestMapping(value = "/s3f-configuration/{configurationId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a configuration", produces = "application/json", consumes = "application/json", notes = "If a configuration will be deleted than the configuration will be deleted physically.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successfully deleted", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Configuration not found", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> deleteConfiguration(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @ApiParam(value = "The service parameter is sent from the gui. "
                    + "The service is needed to identify the data record.", required = true) @PathVariable String configurationId) {
        return (new DeleteConfigurationAction()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                authorization, correlationToken, configurationId);
    }

    @RequestMapping(value = "/s3f-configuration/list/{service}/{version}/{lifecycle}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a configuration", produces = "application/json", consumes = "application/json", notes = "If a configuration will be deleted than the configuration will be deleted physically.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successfully deleted", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Configuration not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Configuration wrong combination ", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class) })
    public ResponseEntity<List<S3FConfigurationDto>> getConfiguration(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @ApiParam(value = "The service parameter is sent from the gui. "
                    + "The service is needed to identify the data record.", required = true) @PathVariable String version,
            @PathVariable String lifecycle, @PathVariable String service) {
        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("version", version);
        httpsValues.put("lifecycle", lifecycle);
        httpsValues.put("service", service);
        return (new GetAllConfigurationAction()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/s3f-configuration/list", method = RequestMethod.GET)
    @ApiOperation(value = "Get a configuration", produces = "application/json", consumes = "application/json", notes = "If a configuration will be deleted than the configuration will be deleted physically.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successfully deleted", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Configuration not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Configuration wrong combination ", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class) })
    public ResponseEntity<List<S3FConfigurationDto>> getAllConfiguration(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken) {
        Map<String, String> httpsValues = new HashMap<>();
        return (new GetAllConfigurationAction()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/s3f-configuration/completion/{service}/{version}/{lifecycle}", method = RequestMethod.GET)
    @ApiOperation(value = "Build configuration with constant replaced", produces = "application/json", consumes = "application/json", notes = "Both configuration constants and the configuration itself will be merged together and will deliver a complete configuration. "
            + "The configuration depends on the service, version and lifecyle.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successful compiled.", response = List.class),
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successful compiled.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be build.", response = HttpStatus.class) })
    public ResponseEntity<List<S3FConfigurationDto>> getRoot(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @ApiParam(value = "The service parameter is sent from the gui. "
                    + "The service is needed to identify the data record.", required = true) @PathVariable String service,
            @ApiParam(value = "The version parameter is sent from the gui. "
                    + "The version is needed to identify the data record.", required = true) @PathVariable String version,
            @ApiParam(value = "The lifecycle parameter is sent from the gui. "
                    + "The lifecycle is needed to identify the data record.", required = true) @PathVariable String lifecycle) {
        LoggerHelper.logData(Level.INFO,
                "GET (single compiled S3FConfiguration) " + service + " " + version + " " + lifecycle, correlationToken,
                authorization, S3FConfigurationController.class.getName());

        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("version", version);
        httpsValues.put("lifecycle", lifecycle);
        httpsValues.put("service", service);

        return (new GetCompiledConfigurationAction()).doActionOnConfiguration(configurationConstantRepository,
                configurationRepository, mongoTemplate, authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/s3f-configuration/list/converter", method = RequestMethod.POST)
    @ApiOperation(value = "Convert a configuration from plain text to encrypt version ", produces = "application/json", consumes = "application/json", notes = "If a configuration will be deleted than the configuration will be deleted physically.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration successfully deleted", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Configuration not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Configuration wrong combination ", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration can't be found.", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> convertAllConfiguration(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken) {

        return (new ConvertData()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                authorization, correlationToken, "");
    }
}
