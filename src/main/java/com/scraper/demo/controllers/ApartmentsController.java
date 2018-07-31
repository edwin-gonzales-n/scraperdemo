package com.scraper.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.scraper.demo.models.HackerNewsItem;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.scraper.demo.repositories.ApartmentsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ApartmentsController {
    public ApartmentsRepository apartmentsRepository;

    public ApartmentsController(ApartmentsRepository apartmentsRepository) throws IOException {
        this.apartmentsRepository = apartmentsRepository;
    }

    @GetMapping("/apartments")
    public String showAll(Model model) throws IOException {

        String baseUrl = "http://www.2400nuecesapartments.com/Floor-Plans/";
        WebClient client = new WebClient();
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

                hnItem.setTitle(title);
                hnItem.setInfo(info);
                hnItem.setPrice(price);
                hnItem.setDate(dtf.format(date));
                apartmentsRepository.save(hnItem);

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(hnItem) ;
                System.out.println(jsonString);
            }
        }

        return "apartments/all";
    }


}
