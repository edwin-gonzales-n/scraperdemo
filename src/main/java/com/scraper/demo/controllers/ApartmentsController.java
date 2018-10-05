package com.scraper.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import com.scraper.demo.models.HackerNewsItem;
import com.scraper.demo.repositories.ApartmentsRepository;
import com.sun.jndi.toolkit.url.Uri;
import com.sun.jndi.toolkit.url.UrlUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
public class ApartmentsController {

    private final ApartmentsRepository apartmentsRepository;

    public ApartmentsController(ApartmentsRepository apartmentsRepository) {
        this.apartmentsRepository = apartmentsRepository;
    }

    // convert utc to cst
    SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
    String cstdate = dateFormatGmt.format(new Date());

    @GetMapping("/nuecesapartments")
    public Iterable<HackerNewsItem> showNuecesApartment() {

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
                String url = "No url available";
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

                    // convert utc to cst
                    SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
                    dateFormatGmt.setTimeZone(TimeZone.getTimeZone("CST"));
                    SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
                    String cstdate = dateFormatGmt.format(new Date());

                    HackerNewsItem hnItem = new HackerNewsItem(title,info,price,cstdate,url);
                    apartmentsRepository.save(hnItem);
                }
                // use when not using RestController
//                model.addAttribute("apartments", apartmentsRepository.findAll());
//                model.addAttribute("apartments", apartmentsRepository.findTop12ByOrderByIdDesc());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apartmentsRepository.findTop12ByOrderByIdDesc();
    }

    @GetMapping("/lakeshore-pearl")
    public Iterable<HackerNewsItem> showPearlShore() {

        System.out.println("looking for website");
        String baseUrl = "https://www.lakeshorepearl.com/Marketing/FloorPlans";
        System.out.println("Website pinged");
        WebClient client = new WebClient();
        System.out.println("WebClient object instantiated");
        client.getOptions().setCssEnabled(false);
        System.out.println("CSS disabled");
        client.getOptions().setJavaScriptEnabled(false);
        System.out.println("JS Disabled");
        try {
            HtmlPage page = client.getPage(baseUrl);
            System.out.println("HtmlPage executed, next is List HtmlElement itemList");
            List<HtmlElement> itemList = page.getByXPath("//div[contains(@class,'col-lg-4 col-sm-6 col-xs-12 floorplan item')]");
            System.out.println("page.getByXPath executed");
            System.out.println(itemList);

            if(itemList.isEmpty()){
                System.out.println("No item found");
            }else{
                int counter=1;
                for(HtmlElement htmlItem : itemList){
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div[contains(@class,'col-xs-9 col-sm-10')]")).asText().replaceAll("\\n"," ");
                    String pricing = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div[contains(@class,'info row pricing')]")).asText().replaceAll("\\n"," ");
                    String info = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div/div[contains(@class,'col-xs-8')]")).asText().replaceAll("\\n"," ");
                    String url = ((DomAttr) htmlItem.getFirstByXPath("./div/div/div/a[contains(@class,'btn btn-default btn-block')]/@href")).getValue();
                    String completeUrl = String.format("https://www.lakeshorepearl.com%s" ,url);

                    if (!pricing.contains("Inquire") && !info.contains("Inquire")){
                        System.out.printf("Dimensions: %s\n%s\n%s\n%s\n%s\n\n", title, pricing, info, cstdate, completeUrl);
                        HackerNewsItem hnItem = new HackerNewsItem(title,info,pricing,cstdate,completeUrl);
                        apartmentsRepository.save(hnItem);
                    }
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apartmentsRepository.findTop9ByOrderByIdDesc();
    }

    @GetMapping("/azulapartments")
    public Iterable<HackerNewsItem> showAzulApartments() {

        System.out.println("looking for website");
        String baseUrl = "https://azullakeshore.com/floorplans/";
        System.out.println("Website pinged");
        WebClient client = new WebClient();
        System.out.println("WebClient object instantiated");
        client.getOptions().setCssEnabled(false);
        System.out.println("CSS disabled");
        client.getOptions().setJavaScriptEnabled(false);
        System.out.println("JS Disabled");
        try {
            HtmlPage page = client.getPage(baseUrl);
            System.out.println("HtmlPage executed, next is List HtmlElement itemList");
//            System.out.println(page.asXml());
            List<HtmlElement> itemList = page.getByXPath("//a[contains(@class,'floorplan')]");
            System.out.println("page.getByXPath executed");
            System.out.println(itemList);

            if(itemList.isEmpty()){
                System.out.println("No item found");
            }else{
                int counter=1;
                String pricing = "Must inquire on-site";
                String info = "Must inquire on-site";

                for(HtmlElement htmlItem : itemList){
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div[contains(@class,'fp_info')]")).asText().replaceAll("\\n"," ");
                    System.out.println(title);
                    String url = ((DomAttr) htmlItem.getFirstByXPath("./@href")).getValue();
                    System.out.println(url);

                    HackerNewsItem hnItem = new HackerNewsItem(title,info,pricing,cstdate,url);
                    apartmentsRepository.save(hnItem);
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apartmentsRepository.findTop16ByOrderByIdDesc();
    }

    @GetMapping("/lenox-boardwalk")
    public Iterable<HackerNewsItem> showLenoxApartments() {

//        String baseUrl = "https://www.lenoxboardwalk.com/floorplans/";
        WebClient client = new WebClient(BrowserVersion.getDefault());
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = client.getPage("http://localhost:8080/");
//            JavaScriptJobManager manager = page.getEnclosingWindow().getJobManager();
//            while (manager.getJobCount() > 0){
//                Thread.sleep(500);
//            }

//            String htmlContent = page.asXml();
//            File htmlFile = new File("/Users/eogonzal/IdeaProjects/Scraper_demo/lenox-boardwalk.html");
//            PrintWriter pw = new PrintWriter(htmlFile);
//            pw.print(htmlContent);
//            pw.close();
//
//            System.out.println("HtmlPage executed, next is List HtmlElement itemList");
//            System.out.println(page.asXml());

            List<HtmlElement> itemList = page.getByXPath("//ul[contains(@id,'floorplan_slider_list')]/li");
            System.out.println("page.getByXPath executed");
            System.out.println("This is the itemList: " + itemList);

            if(itemList.isEmpty()){
                System.out.println("No item found");
            }
            else{
                int counter=1;
                String info = "Must inquire on-site";
                String url  =  "https://www.lenoxboardwalk.com/floorplans/";

                for(HtmlElement htmlItem : itemList){
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./ul[contains(@class,'floorplan-details')]"))
                            .asText().replaceAll("\\n"," ").replaceAll("Rent.*$","");
                    System.out.println(title);
                    String pricing = ((HtmlElement) htmlItem.getFirstByXPath("./ul[contains(@class,'floorplan-details')]"))
                            .asText().replaceAll("\\n"," ").replaceAll(".*Rent ","");
                    System.out.println(pricing);

                    HackerNewsItem hnItem = new HackerNewsItem(title,info,pricing,cstdate,url);
                    apartmentsRepository.save(hnItem);

                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return apartmentsRepository.findTop16ByOrderByIdDesc();
    }
}
