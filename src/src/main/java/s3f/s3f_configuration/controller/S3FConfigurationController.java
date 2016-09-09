package s3f.s3f_configuration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.entities.S3FConfigurationConstant;
import s3f.s3f_configuration.services.S3FConfigurationConstantService;
import s3f.s3f_configuration.services.S3FConfigurationService;

@RestController
@RequestMapping("/api/v1/s3f-configuration")
public class S3FConfigurationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(S3FConfigurationController.class);
    @Autowired
    private S3FConfigurationService s3FConfigurationService;
    @Autowired
    private S3FConfigurationConstantService s3FConfigurationConstantService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody S3FConfigurationDto s3FConfigurationDto) {
        LOGGER.info("Post");
        try {
            LOGGER.info(s3FConfigurationDto.toString());
            s3FConfigurationService.create(s3FConfigurationDto);
        } catch (Exception e) {
            LOGGER.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{service}/{version}/{lifecycle}", method = RequestMethod.GET)
    public ResponseEntity getRoot(@PathVariable String service, @PathVariable String version, @PathVariable String lifecycle) {
        LOGGER.info("GET (single S3FConfiguration) " + service + " " + version + " " + lifecycle);
        ResponseEntity responseEntity;
        try {
            final S3FConfigurationConstant s3FConfigurationConstants = s3FConfigurationConstantService.read(version, lifecycle);
            final S3FConfiguration s3FConfiguration = s3FConfigurationService.read(service, version, lifecycle);
            responseEntity = new ResponseEntity(s3FConfigurationService.build(s3FConfigurationConstants, s3FConfiguration), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            responseEntity = new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}