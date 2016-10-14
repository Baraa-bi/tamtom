package com.example;



import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class upload {

    HashMap<String , MultipartFile> map = new HashMap<>();
    @RequestMapping("/uploadImage")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        map.put(file.getOriginalFilename(),file);
        return "Your image has been uploaded successfully :D";
    }
    @RequestMapping("/getImage")
    public MultipartFile get(@RequestParam("name") String name) {
        
        return map.get(name);
                
    }

}
