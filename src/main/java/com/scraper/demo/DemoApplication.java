package com.scraper.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlContent;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        // 1st try to do in http://www.2400nuecesapartments.com/Floor-Plans

        String baseUrl = "http://www.2400nuecesapartments.com/Floor-Plans/";
//        String baseUrl = "https://news.ycombinator.com/" ;
//        String baseUrl = "https://www.drudgereport.com/";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
//            HtmlPage page = client.getPage(baseUrl);
//            System.out.println(page.asXml());
//            System.out.println(page.asText());
            HtmlPage page = client.getPage(baseUrl);
//            System.out.println(page.asText());
            System.out.println("This is the start of the page.getByXpath");
//            System.out.println(page.getByXPath("//tr[@class='athing']"));
            System.out.println(page.getByXPath("//div"));
            System.out.println("Title out:");
            System.out.println(page.getByXPath("//div[@class='fp-info has-description']//div//div"));

//            List<HtmlElement> itemList = page.getByXPath("//tr[@class='athing']");
//            List<HtmlElement> itemList = page.getByXPath("//div[@class='fp-info has-description']//div//div");
            List<HtmlElement> itemList = page.getByXPath("//div[@id='isotope']");
            if(itemList.isEmpty()){
                System.out.println("No item found");
            }else{
                for(HtmlElement htmlItem : itemList){
//                    int position = Integer.parseInt(((HtmlElement) htmlItem.getFirstByXPath("./td/span")).asText().replace(".", ""));
//                    int id = Integer.parseInt(htmlItem.getAttribute("id"));
//                    String title = ((HtmlElement) htmlItem.getFirstByXPath("./td[not(@valign='top')] [@class='title']")).asText();
//                    String url = ((HtmlAnchor) htmlItem.getFirstByXPath("./td[not(@valign='top')] [@class='title']/a")).getHrefAttribute();
//                    String author = ((HtmlElement) htmlItem.getFirstByXPath("./following-sibling:: tr/td[@class='subtext']/a[@class='hnuser']")).asText();
                    String title = ((HtmlElement) htmlItem.getFirstByXPath("//h4")).asText();
//                    System.out.println("Title out:");
//                    System.out.println(title);
                    String info = ((HtmlElement) htmlItem.getFirstByXPath("//a//p")).asText();
//                    System.out.println(info);
                    String price = ((HtmlElement) htmlItem.getFirstByXPath("//*[@class='fp-info has-description']")).asText();
                    System.out.println(price);
//                    String author = ((HtmlElement) htmlItem.getFirstByXPath("./following-sibling:: tr/td[@class='subtext']/a[@class='hnuser']")).asText();

//                    int score = Integer.parseInt(((HtmlElement) htmlItem.getFirstByXPath("./following-sibling:: tr/td[@class='subtext']/span[@class='score'] ")).asText().replace(" points", ""));

//                    HackerNewsItem hnItem = new HackerNewsItem(title, url, author, score, position, id);
//                    HackerNewsItem hnItem = new HackerNewsItem(title, info, price);
//
//                    ObjectMapper mapper = new ObjectMapper();
//                    String jsonString = mapper.writeValueAsString(hnItem) ;
////                    7/9
//                    System.out.println(jsonString);
                }
            }
        }
        catch(Exception e){
                e.printStackTrace();
            }
    }

}

