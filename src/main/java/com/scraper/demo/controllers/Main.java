package com.scraper.demo.controllers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.scraper.demo.models.apartments;
import com.scraper.demo.repositories.ApartmentsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Controller
public class Main {

    public ApartmentsRepository apartmentsRepository;

    public Main(ApartmentsRepository apartmentsRepository){
        this.apartmentsRepository = apartmentsRepository;
    }

    // convert utc to cst
    SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
    String cstdate = dateFormatGmt.format(new Date());

    @GetMapping("/")
    public String apartmentController(){
        return "index";
    }

    @GetMapping("/fakelenox")
    public String fakeLenox(){
        return "lenox-boardwalk";
    }

    @GetMapping("/sensors")
    public String sensorsController(){
        return "sensors";
    }

    @GetMapping("/filldatabase")
    public String filldatabase(){
        // timer to refill database with new data everyday, once a day.
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                apartmentsRepository.deleteAll();
                getNuecesApartment();
                getLakeShore();
                getAzul();
                getLenox();
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
//        long period = 1000L * 60L * 60L * 24L;
        long period = 10000L;
        timer.scheduleAtFixedRate(repeatedTask, delay , period);
        return "fillDatabase";
    }

//    @GetMapping("/filldatabase")
//    public String filldatabase(){
//        //cleaning database if filled.
//        apartmentsRepository.deleteAll();
//        //filling up database with new data
//        getNuecesApartment();
//        getLakeShore();
//        getAzul();
//        getLenox();
//        return "fillDatabase";
//    }

    // logic to fill database
    private void getNuecesApartment() {
        long property_id=1;
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
                    System.out.println("This is the property_id" + property_id);
                    String info = ((HtmlElement) htmlItem.getFirstByXPath("./div/div[contains(@class,'fp-avil')]")).asText();
                    String price = ((HtmlElement) htmlItem.getFirstByXPath("./p[contains(text(),'Starting')]")).asText();
                    System.out.printf("%d. Title: %s\nInfo: %s\nDimensions & Price: %s\n\n", counter, title, info, price);

                    apartments hnItem = new apartments(title,info,price,cstdate,url,property_id);
                    apartmentsRepository.save(hnItem);
                    counter++;
                }
                // use when not using RestController
//                model.addAttribute("apartments", apartmentsRepository.findAll());
//                model.addAttribute("apartments", apartmentsRepository.findTop12ByOrderByIdDesc());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLakeShore(){
        long property_id=2;
        String baseUrl = "https://www.lakeshorepearl.com/Marketing/FloorPlans";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> itemList = page.getByXPath("//div[contains(@class,'col-lg-4 col-sm-6 col-xs-12 floorplan item')]");

            if(itemList.isEmpty()){
                System.out.println("No item found");
            }else{
                int counter=1;

                for(HtmlElement htmlItem : itemList){
                    System.out.println("This is the property_id" + property_id);
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div[contains(@class,'col-xs-9 col-sm-10')]"))
                            .asText().replaceAll("\\n"," ");
                    String pricing = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div[contains(@class,'info row pricing')]"))
                            .asText().replaceAll("\\n"," ");
                    String info = ((HtmlElement) htmlItem.getFirstByXPath("./div/div/div/div/div[contains(@class,'col-xs-8')]"))
                            .asText().replaceAll("\\n"," ");
                    String url = ((DomAttr) htmlItem.getFirstByXPath("./div/div/div/a[contains(@class,'btn btn-default btn-block')]/@href"))
                            .getValue();
                    String completeUrl = String.format("https://www.lakeshorepearl.com%s" ,url);

                    if (!pricing.contains("Inquire") && !info.contains("Inquire")){
                        System.out.printf("Dimensions: %s\n%s\n%s\n%s\n%s\n\n", title, pricing, info, cstdate, completeUrl);
                        apartments hnItem = new apartments(title,info,pricing,cstdate,completeUrl,property_id);
                        apartmentsRepository.save(hnItem);
                    }
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAzul(){
        long property_id=3;
        String baseUrl = "https://azullakeshore.com/floorplans/";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = client.getPage(baseUrl);
            System.out.println("HtmlPage executed, next is List HtmlElement itemList");
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
                    System.out.println("This is the property_id" + property_id);
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./div[contains(@class,'fp_info')]")).asText().replaceAll("\\n"," ");
                    System.out.println(title);
                    String url = ((DomAttr) htmlItem.getFirstByXPath("./@href")).getValue();
                    System.out.println(url);

                    apartments hnItem = new apartments(title,info,pricing,cstdate,url,property_id);
                    apartmentsRepository.save(hnItem);
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLenox(){
        long property_id=4;
        WebClient client = new WebClient(BrowserVersion.getDefault());
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = client.getPage("http://localhost:8080/fakelenox");

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
                    System.out.println("This is the property_id" + property_id);
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./ul[contains(@class,'floorplan-details')]"))
                            .asText().replaceAll("\\n"," ").replaceAll("Rent.*$","");
                    System.out.println(counter + ") " + title);
                    String pricing = ((HtmlElement) htmlItem.getFirstByXPath("./ul[contains(@class,'floorplan-details')]"))
                            .asText().replaceAll("\\n"," ").replaceAll(".*Rent ","");
                    System.out.println(pricing);
                    System.out.println(info);
                    System.out.println(url);

                    apartments hnItem = new apartments(title,info,pricing,cstdate,url,property_id);
                    apartmentsRepository.save(hnItem);
                    System.out.println("done saving to DB");

                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
