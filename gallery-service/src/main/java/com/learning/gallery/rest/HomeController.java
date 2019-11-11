package com.learning.gallery.rest;

import com.learning.gallery.model.Gallery;
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
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @RequestMapping("/")
    public String home() {
        return "Hello from Gallery service running at port: " + env.getProperty("local.server.port");
    }

    @GetMapping(
            value="/gallery/{id}",
            produces = "application/json"
    )
    public Gallery getGallery(@PathVariable final int id) {
        var gallery = new Gallery();
        gallery.setId(id);

        List<Object> images = restTemplate.getForObject("http://image-service/images", List.class);
        gallery.setImages(images);
        return gallery;
    }

    @RequestMapping("/admin")
    public String homeAdmin() {
        return "This is admin area of Gallery running at port: " + env.getProperty("local.server.port");
    }
}
