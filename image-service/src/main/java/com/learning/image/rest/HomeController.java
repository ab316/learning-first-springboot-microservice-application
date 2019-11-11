package com.learning.image.rest;

import com.learning.image.db.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {
    @Autowired
    private Environment env;

    @RequestMapping("/images")
    public List<Image> getImages() {
        return Arrays.asList(
                new Image("1", "cat", "https://www.nationalgeographic.com/content/dam/news/2018/05/17/you-can-train-your-cat/02-cat-training-NationalGeographic_1484324.jpg"),
                new Image("2", "space", "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/images/649694main_pia15417-43_full.jpg")
        );
    }
}
