package ZhiHuSpider.test;

import util.DBStatement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/1.
 */
public class NewsSpider {
    public static void main(String[] args) {
        NewsSpider spider = new NewsSpider();
        spider.crawler("http://www.evlook.com/news/list-1.html");
    }

    public void crawler(String sUrl){
        URL url;
        InputStream is = null;
        Connection con = DBStatement.getCon();
        PreparedStatement ps = null;
        ResultSet results = null;
        try {
            url = new URL(sUrl);
            URLConnection urlconnection = url.openConnection();
            urlconnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.0)");
            is = url.openStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
            StringBuffer sb = new StringBuffer();//sb为爬到的网页内容
            String rLine = null;
            while((rLine=bReader.readLine())!=null){
                sb.append(rLine);
            }
            String content = sb.toString();
            //解析html
            Document doc= Jsoup.parse(content);
            //获取一级新闻列表页
            Elements newsTagList =  doc.getElementById("choosecar").getElementsByTag("li");
            for (Element newsTag : newsTagList) {
                //获取新闻url
                String newsUrl = newsTag.getElementsByTag("h3").first().getElementsByTag("a").first().attr("href");
                //获取新闻pic
                String pic = newsTag.getElementsByTag("img").attr("src");
                System.out.println();
                String newsContent = sprderUrlDetail(newsUrl);
                //解析新闻详情页
                Document newsDoc= Jsoup.parse(newsContent);
                //标题
                String title = newsDoc.getElementsByTag("h1").first().html();
                //内容
                String newsCont = newsDoc.getElementById("newscon") + "<strong>本文转自：" + url + "</strong>";
   //             Document newsCon= Jsoup.parse(newsCont);
                String sql = "insert into news (url,title,content,create_time,pic) values (?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1,newsUrl);
                ps.setString(2,title);
                ps.setString(3,newsCont);
                //转换日期格式到sql
                ps.setDate(4,new java.sql.Date(new Date().getTime()));
                ps.setString(5,pic);
                //存储brand
                ps.executeUpdate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //爬取二级页面
    public String sprderUrlDetail(String sUrl) throws Exception{
        URL url;
        InputStream is = null;
        url = new URL(sUrl);
        URLConnection urlconnection = url.openConnection();
        urlconnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        is = url.openStream();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        StringBuffer sb = new StringBuffer();//sb为爬到的网页内容
        String rLine = null;
        while ((rLine = bReader.readLine()) != null) {
            sb.append(rLine);
        }
        String content = sb.toString();
        return content;
    }
}
