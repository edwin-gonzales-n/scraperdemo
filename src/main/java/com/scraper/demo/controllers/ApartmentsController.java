package com.scraper.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.scraper.demo.models.HackerNewsItem;
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
    private final ApartmentsRepository apartmentsRepository;

    public ApartmentsController(ApartmentsRepository apartmentsRepository) {
        this.apartmentsRepository = apartmentsRepository;
    }

    @GetMapping("/")
    public String showAll(Model model) {

        String baseUrl = "http://www.2400nuecesapartments.com/Floor-Plans/";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
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

                    HackerNewsItem hnItem = new HackerNewsItem(title,info,price,dtf.format(date));
                    apartmentsRepository.save(hnItem);

                    model.addAttribute("apartments", apartmentsRepository.findAll());

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(hnItem) ;
                    System.out.println(jsonString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }
}
