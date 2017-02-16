package com.example.parsingHtml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by baraa on 2/16/2017.
 */


@RestController
public class HtmlController {



        @RequestMapping(value = "/getAllMovies",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public String getAll(@RequestParam(value = "id",required = false,defaultValue = "1")String id) throws IOException, JSONException {

            Document doc = Jsoup.connect("http://www.anakbnet.com/video/index.php?p="+id).get();
            Elements elements = doc.getElementsByClass("file_index");
            JSONArray object = new JSONArray();
            int i=0;
            for(Element element : elements)
            {
                JSONObject object1 = new JSONObject();
                String image = element.select("img").attr("src");
                String link = element.select("a").attr("href");
                String name = element.select(".file_title1").select("a").text();

                Document doc1 = Jsoup.connect(link).get();
                link = doc1.select("iframe").attr("src");

                String description = element.select(".file_description1-1").text();

                object1.put("name",name);
                object1.put("image",image);
                object1.put("link",link);
                object1.put("description",description);
                object.put(object1);
            }

            return object.toString();
        }


}
