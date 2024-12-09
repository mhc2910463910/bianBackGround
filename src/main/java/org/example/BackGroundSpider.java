package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.naming.directory.SearchControls;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
public class BackGroundSpider {
    static String url="http://www.netbian.com/";
    static String content;
    public void Play(String url) throws IOException {
//        System.out.println(url);
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpGet httpget=new HttpGet(url);
        RequestConfig config=RequestConfig.custom().setConnectionRequestTimeout(1000)
                .setConnectTimeout(1000)
                .setSocketTimeout(10*1000).build();
        CloseableHttpResponse response =null;
        httpget.setConfig(config);
        try{
            response= httpClient.execute(httpget);
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity httpentity=response.getEntity();
                content= EntityUtils.toString(httpentity,"gbk");
//                System.out.println(content);
//                this.SearchUrl(content);
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            response.close();
            httpClient.close();
        }
    }
    public void SearchUrl(String content){
        Document doc= Jsoup.parse(content);
        Elements element=doc.getElementsByClass("nav cate");
        int i=0;
        for(Element e:element){
            Elements ee=e.getElementsByTag("a");
            for(Element eee:ee){
//                if(i>0&&i<25){
                    System.out.println(eee.text()+":"+url+eee.attr("href"));
                    BackGroundFrame.BackGroundBut.add(new MyButton(eee.text(),url+eee.attr("href")));
//                }
                i++;
            }
        }
//        System.out.println(str);
    }
    public void searchHref(String content) {
        Document doc =Jsoup.parse(content);
        Elements elements=doc.getElementsByClass("list");
        for(Element e : elements){
//            System.out.println(e.text());
            Elements img=e.getElementsByTag("a");
            for(Element imgurl:img){
                if(imgurl.attr("title")!=""){
                    System.out.println(imgurl.attr("title")+":"+"http://www.netbian.com"+imgurl.attr("href"));
                    url="http://www.netbian.com"+imgurl.attr("href");
                    try {
                        Play(url);
                        // System.out.println(this.content);
                        Document doc2 = Jsoup.parse(BackGroundSpider.content);
                        Elements elements2 = doc2.getElementsByClass("pic");
                        for (Element e2 : elements2) {
                            Element img_src = e2.getElementsByTag("img").first();
                            System.out.println(img_src.attr("alt") + ":" + img_src.attr("src"));
                            BackGroundFrame.BackGroundImg.add(new MyImageBut(img_src.attr("alt"), img_src.attr("src")));
                        }
                    }catch(Exception err){
                        err.printStackTrace();
                    }
                }
                //BackGroundFrame.BackGroundImg.add(new MyImageBut(imgurl.attr("alt").substring(0,2),imgurl.attr("src")));
            }
        }
    }
//    public static void main(String[] args) throws IOException {
////        BackGroundSpider spider=new BackGroundSpider();
////        spider.Play(BackGroundSpider.url);
////        spider.SearchUrl(BackGroundSpider.content);
////        spider.Play(BackGroundFrame.BackGroundBut.get(3).getUrl());
////        spider.searchHref(BackGroundSpider.content);
//
////        System.out.println(BackGroundSpider.butList.toString());
//    }
}
