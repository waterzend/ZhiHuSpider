package ZhiHuSpider.test;


import util.DBStatement;
import com.mysql.jdbc.Statement;
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
import java.sql.SQLException;


import java.util.Date;

/**
 * Created by Administrator on 2016/11/15.
 */
public class CarSpider {
    public static void main(String[] args) {
        CarSpider spider = new CarSpider();
        spider.crawler("http://www.cnev.cn/chexingku/mini/");
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
            urlconnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
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
            //获取品牌+汽车列表节点
            Elements brandAndCarsList =  doc.getElementsByClass("list_all");
            for (Element brandAndCars : brandAndCarsList) {
                //获取汽车品牌列表
                Element brand = brandAndCars.getElementsByClass("listLogo").first();
                //循环列表
                //找到品牌二级页面url
                String BrandUrl = brand.select("a").first().attr("href");
                //获取商品详情
                String brandContent = getPageContent(BrandUrl);
                Document brandDoc= Jsoup.parse(brandContent);
                //获取品牌介绍
                String brandDescribe = brandDoc.getElementsByClass("jianjie").text();
                //找到第一个a标签里的第一个img标签里的src
                Element picElement = brand.select("a").first().select("img").first();
                String picUrl = picElement.attr("src");
                //找到第二个a标签里的文字
                String brandName = brand.select("a").last().text();
                String sql = "insert into brand (brand,pic,create_time,description) values (?,?,?,?)";
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,brandName);
                ps.setString(2,picUrl);
                //转换日期格式到sql
                ps.setDate(3,new java.sql.Date(new Date().getTime()));
                ps.setString(4,brandDescribe);
                //存储brand
                int i = ps.executeUpdate();
                System.out.println(i);
                //获取刚插入的brand的id
                results = ps.getGeneratedKeys();
                if(results.next())
                {
                    //品牌主键
                    int brandId = results.getInt(1);
                    //获取当前品牌下汽车列表信息
                    Elements carList = brandAndCars.getElementsByClass("list_con").first().getElementsByTag("li");
                    for (Element car : carList) {
                        //获取汽车图片，alt内是汽车名称
                        Element carPic = car.select("a").first().select("img").first();
                        String carPicUrl = carPic.attr("src");
                        String carName = carPic.attr("alt");
                        Elements carPropertyList = car.select("dd");
                        //第一个属性是价格
                        String carPrice = carPropertyList.first().text();
                        //循环属性到字符串中
                        String carpropertyString = "";
                        for (Element carProperty : carPropertyList) {
                            carpropertyString = carpropertyString + carProperty.text() + " ; ";
                        }
                        String carSql = "insert into car (name,brand,price,pic,detail,create_time) values (?,?,?,?,?,?)";
                        ps = con.prepareStatement(carSql);
                        ps.setString(1,carName);
                        ps.setInt(2,brandId);
                        ps.setString(3,carPrice);
                        ps.setString(4,carPicUrl);
                        ps.setString(5,carpropertyString);
                        //转换日期格式到sql
                        ps.setDate(6,new java.sql.Date(new Date().getTime()));
                        //存储car
                        ps.executeUpdate();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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

    //根据url获取内容
    public String getPageContent(String sUrl){
        URL url;
        InputStream is = null;

        try {
             url = new URL(sUrl);
            URLConnection urlconnection = url.openConnection();
            urlconnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            is = url.openStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
            StringBuffer sb = new StringBuffer();//sb为爬到的网页内容
            String rLine = null;
            while((rLine=bReader.readLine())!=null){
                sb.append(rLine);
            }
            String content = sb.toString();
            return content;
        } catch (Exception e){

        }
        return null;
    }
}
