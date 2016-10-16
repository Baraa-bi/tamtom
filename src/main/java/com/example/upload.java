package com.example;



import com.mongodb.gridfs.GridFSDBFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class upload {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @RequestMapping("/uploadImage")
    public String upload(@RequestParam("file") MultipartFile file , @RequestParam(value ="name" , defaultValue ="0") String name) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(file.getBytes());
        if(name.equals("0"))name=""+x++;
        //FileUtils.writeByteArrayToFile(new File("C:\\Users\\babdelfattah\\Desktop\\"+file.getOriginalFilename()),file.getBytes());
        gridFsTemplate.store(inputStream,name,"image/png");
        return "Your image has been uploaded successfully :D";
    }

    @RequestMapping("/getPhoto/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("name")String name) throws IOException {
        List<GridFSDBFile> result = gridFsTemplate.find(
                new Query().addCriteria(Criteria.where("filename").is(name)));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        result.get(0).writeTo(stream);
        byte[] content = stream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
    }





}
