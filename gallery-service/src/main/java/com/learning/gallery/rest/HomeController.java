package com.learning.gallery.rest;

import com.learning.gallery.model.Gallery;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @RequestMapping("/")
    public String home() {
        return "Hello from Gallery service running at port: " + env.getProperty("local.server.port");
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping(value = "/gallery/{id}", produces = "application/json")
    public Gallery getGallery(@PathVariable final int id) {
        LOGGER.info("Creating gallery object");
        var gallery = new Gallery();
        gallery.setId(id);

        @SuppressWarnings("unchecked")
        List<Object> images = restTemplate.getForObject("http://image-service/images", List.class);
        gallery.setImages(images);
        
        LOGGER.info("Returning images");
        return gallery;
    }

    @RequestMapping("/admin")
    public String homeAdmin() {
        return "This is admin area of Gallery running at port: " + env.getProperty("local.server.port");
    }

    public Gallery fallback(int galleryId, Throwable hysterixCommand) {
        LOGGER.warn("Fallback method invoked");
        return new Gallery(galleryId);
    }
}
