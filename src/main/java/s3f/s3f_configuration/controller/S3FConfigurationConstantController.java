package s3f.s3f_configuration.controller;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import s3f.s3f_configuration.action.constants.*;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

@RestController
@Api(tags = "Shared Constants", description = "Simplify configuration")
@ApiModel(value = "Configuration Constants", description = "The constants which can be used at all combination of version/lifecycle away all services.")
public class S3FConfigurationConstantController {

    @Autowired
    private S3FConfigurationConstantRepository s3fConfigurationConstantRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/api/v1/s3f-configuration/constant", method = RequestMethod.PUT)
    @ApiOperation(value = "Create a new configurarion constant", produces = "application/json", consumes = "application/json", notes = "Create a new configuration as a raw configuration. Within the key value list the value part is encrypted stored to the database.")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant successful created", response = S3FConfigurationConstantDto.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Configuration constant has double entry", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be saved.", response = HttpStatus.class) })
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Constant name does not match pattern $env_[a-zA-Z0-9][.a-zA-Z0-9]*", response = HttpStatus.class)
    public ResponseEntity<S3FConfigurationConstantDto> create(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @RequestBody S3FConfigurationConstantDto s3FConfigurationConstantDto) {
        return (new CreateConstantAction()).doActionOnConstant(s3fConfigurationConstantRepository, mongoTemplate,
                authorization, correlationToken, s3FConfigurationConstantDto);
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant", method = RequestMethod.POST)
    @ApiOperation(value = "Update a configurarion constant", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Configuration constant not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be found.", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> update(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken,
            @RequestBody S3FConfigurationConstantDto s3FConfigurationConstantDto) {
        return (new EditConstantAction()).doActionOnConstant(s3fConfigurationConstantRepository, mongoTemplate,
                authorization, correlationToken, s3FConfigurationConstantDto);
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant/list", method = RequestMethod.GET)
    @ApiOperation(value = "Get the list of configuration constants", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant list found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Configuration constant not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be found.", response = HttpStatus.class) })
    public ResponseEntity<List<S3FConfigurationConstantDto>> readAll(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken) {

        Map<String, String> httpsValues = new HashMap<>();
        return (new GetAllConstantAction()).doActionOnConstant(s3fConfigurationConstantRepository, mongoTemplate,
                authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant/{version}/{lifecycle}", method = RequestMethod.GET)
    @ApiOperation(value = "Get the list of configuration constants", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant list found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Configuration constant not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Configuration Constant wrong combination ", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be found.", response = HttpStatus.class) })
    public ResponseEntity<List<S3FConfigurationConstantDto>> readAllForVersionAndLifecycle(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable String version,
            @PathVariable String lifecycle) {

        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("version", version);
        httpsValues.put("lifecycle", lifecycle);

        return (new GetAllConstantAction()).doActionOnConstant(s3fConfigurationConstantRepository, mongoTemplate,
                authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant/{constantName}/{version}/{lifecycle}", method = RequestMethod.GET)
    @ApiOperation(value = "Get a configurarion constant", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = "Configuration Constant wrong combination ", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Configuration constants not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be found.", response = HttpStatus.class) })
    public ResponseEntity<List<S3FConfigurationConstantDto>> read(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable String version,
            @PathVariable String lifecycle, @PathVariable String constantName) {

        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("version", version);
        httpsValues.put("lifecycle", lifecycle);
        httpsValues.put("constantName", constantName);

        return (new GetAllConstantAction()).doActionOnConstant(s3fConfigurationConstantRepository, mongoTemplate,
                authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant/{version}/{lifecycle}/{constantName}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Removes a constant", produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Configuration constant removed.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Configuration constant not found.", response = HttpStatus.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Configuration constant can't be removed.", response = HttpStatus.class) })
    public ResponseEntity<HttpStatus> delete(@RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "CorrelationToken") String correlationToken, @PathVariable String version,
            @PathVariable String lifecycle, @PathVariable String constantName) {

        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("version", version);
        httpsValues.put("lifecycle", lifecycle);
        httpsValues.put("constantName", constantName);

        return (new DeleteConstantAction()).doActionOnConstant(s3fConfigurationConstantRepository, mongoTemplate,
                authorization, correlationToken, httpsValues);
    }

    @RequestMapping(value = "/api/v1/s3f-configuration/constant/", method = RequestMethod.POST)
    public ResponseEntity<?> copyConstant(@RequestHeader(value = "Authorization") String authorization,
                                                   @RequestHeader(value = "CorrelationToken") String correlationToken,
                                                   @RequestParam(value = "ConstantName") String constantName,
                                                   @RequestParam(value = "LifecycleToCopyFrom") String sourceLifecycle,
                                                   @RequestHeader(value = "LifecycleToCopyTo") String targetLifecycle,
                                                   @RequestHeader(value = "SourceVersion") String sourceVersion,
                                                   @RequestHeader(value = "TargetVersion") String targetVersion){

        Map<String, String> httpsValues = new HashMap<>();
        httpsValues.put("ConstantName", constantName);
        httpsValues.put("LifecycleToCopyFrom", sourceLifecycle);
        httpsValues.put("LifecycleToCopyTo", targetLifecycle);
        httpsValues.put("SourceVersion", sourceVersion);
        httpsValues.put("TargetVersion", targetVersion);
        return new CopyConstantAction().doActionOnConstant(s3fConfigurationConstantRepository,mongoTemplate,authorization,correlationToken,httpsValues);
    }
}
