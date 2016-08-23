package s3f.s3f_configuration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.entities.S3FConfigurationConstant;
import s3f.s3f_configuration.services.S3FConfigurationConstantService;

@RestController
@RequestMapping("/api/v1/s3f-configuration/constant")
public class S3FConfigurationConstantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(S3FConfigurationConstantController.class);
    @Autowired
    private S3FConfigurationConstantService s3FConfigurationConstantService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody S3FConfigurationConstantDto s3FConfigurationConstantDto) {
        LOGGER.info("Post");
        try {
            LOGGER.info(s3FConfigurationConstantDto.toString());
            s3FConfigurationConstantService.create(s3FConfigurationConstantDto);
        } catch (Exception e) {
            LOGGER.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}/{version}/{lifecycle}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable String version, @PathVariable String lifecycle) {
        LOGGER.info("GET (single S3FConfigurationConstant) " + version + " " + lifecycle);
        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity(s3FConfigurationConstantService.read(version, lifecycle), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            responseEntity = new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity put(@RequestBody S3FConfigurationConstant s3FConfigurationConstant) {
        LOGGER.info("PUT");
        ResponseEntity responseEntity;
        try {
            s3FConfigurationConstantService.update(s3FConfigurationConstant);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("", e);
            responseEntity = new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
