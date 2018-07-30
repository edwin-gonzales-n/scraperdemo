package com.scraper.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

        String baseUrl = "http://www.2400nuecesapartments.com/Floor-Plans/";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
//            HtmlPage page = client.getPage(baseUrl);
//            System.out.println(page.asXml());
//            System.out.println(page.asText());
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> itemList = page.getByXPath("//div[contains(@class,'has-description')]");
            if(itemList.isEmpty()){
                System.out.println("No item found");
            }else{
                int counter=1;
                for(HtmlElement htmlItem : itemList){
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div/div[contains(@class,'fp-title')]")).asText();
                    String info = ((HtmlElement) htmlItem.getFirstByXPath("./div/div[contains(@class,'fp-avil')]")).asText();
                    String price = ((HtmlElement) htmlItem.getFirstByXPath("./p[contains(text(),'Starting')]")).asText();
                    System.out.printf("%d. Title: %s\nInfo: %s\nDimensions & Price: %s\n\n", counter, title, info, price);
                    counter++;
                }
                for(HtmlElement htmlItem : itemList){
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div/div[contains(@class,'fp-title')]")).asText();
                    String info = ((HtmlElement) htmlItem.getFirstByXPath("./div/div[contains(@class,'fp-avil')]")).asText();
                    String price = ((HtmlElement) htmlItem.getFirstByXPath("./p[contains(text(),'Starting')]")).asText();

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime date = LocalDateTime.now();
                    System.out.println(dtf.format(date));

                    HackerNewsItem hnItem = new HackerNewsItem(title,info, price, dtf.format(date));
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(hnItem) ;
                    System.out.println(jsonString);
                }
            }
        }
        catch(Exception e){
                e.printStackTrace();
            }
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }
}