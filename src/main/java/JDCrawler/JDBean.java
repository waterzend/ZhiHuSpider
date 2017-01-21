package JDCrawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/12.
 */
public class JDBean {

    public static void main(String[] args){

    }

    //商品url
    String url;
    //商品编号
    String number;
    //商品主图
    String srcPic;
    //商品名称
    String commodityName;
    //商品品牌
    String brand;
    //商品价格
    Double price;
    //详细信息
    String detail;
    //评论数
    Integer commentCount;
    //好评率
    Double goodRate;

    public JDBean(String context,String url) {
        StringBuilder sb = new StringBuilder();
        this.url = url;
        Pattern pattern;
        Matcher matcher;
        //商品编号
        pattern = Pattern.compile("com/(.+?).html");
        matcher = pattern.matcher(url);
        if (matcher.find()) {
            this.number = matcher.group(1);
            //由商品编号查价格
            String getPriceUrl = "http://p.3.cn/prices/mgets?skuIds=J_" + number + ",J_&type=1";
            setPrice(getPriceUrl);
            //由商品编号查评论
            String getCommentUrl = "http://club.jd.com/productpage/p-" + number + "-s-0-t-3-p-0.html";
            setComment(getCommentUrl);
        }

        /**
         * 京东的两种网页中的名称不一样，分为类1和类2
         * 由商品名称为h1包裹或者是class=sku-name来判断
         */
        //按商品名称分类型
        pattern = Pattern.compile("<h1>(.+?)</h1>|<div class=\"sku-name\">(.+?)</div>");
        matcher = pattern.matcher(context);
        if (matcher.find()) {
            if(matcher.group(1)!=null){//第一种网页，名称为组1
                //商品名称
                this.commodityName = matcher.group(1);
                //商品主图
                pattern = Pattern.compile("<img data-img=\"1\".*?src=\"//(.*?)\"");
                matcher = pattern.matcher(context);
                if (matcher.find()) {
                    this.srcPic = matcher.group(1);
                }
                //详细信息
                pattern = Pattern.compile("<ul id=\"parameter2\" class=\"p-parameter-list\">.*?</ul>");
                matcher = pattern.matcher(context);
                if(matcher.find()){
                    Matcher detailList = Pattern.compile("<li.*?>(.*?)</li>").matcher(matcher.group());
                    while (detailList.find()) {
                        String detail = detailList.group(1);
                        //为了把带超链接的属性去除
                        if(!detail.contains("<")){
                            sb.append(detail+" , ");
                        }
                    }
                    this.detail = sb.toString();
                }
            }else{ //第二种网页，匹配的是组2
                //商品名称
                this.commodityName = matcher.group(2);
                //商品主图
                pattern = Pattern.compile("data-origin=\"//(.*?)\"");
                matcher = pattern.matcher(context);
                if (matcher.find()) {
                    this.srcPic = matcher.group(1);
                }
                //详细信息
                pattern = Pattern.compile("<ul class=\"parameter2 p-parameter-list\">.*?</ul>");
                matcher = pattern.matcher(context);
                if(matcher.find()){
                    Matcher detailList = Pattern.compile("<li.*?>(.*?)</li>").matcher(matcher.group());
                    while (detailList.find()) {
                        String detail = detailList.group(1);
                        //为了把带超链接的属性去除
                        if(!detail.contains("<")){
                            sb.append(detail+" , ");
                        }
                    }
                    this.detail = sb.toString();
                }
            }
            //商品品牌
            pattern = Pattern.compile("<ul id=\"parameter-brand\" class=\"p-parameter-list\">.*?<li title='(.*?)'>");
            matcher = pattern.matcher(context);
            if (matcher.find()) {
                this.brand = matcher.group(1);
            }
        }
    }

    //根据请求获取商品价格
    //例子："http://p.3.cn/prices/mgets?skuIds=J_2172006,J_&type=1"
    private void setPrice(String priceUrl){
        JSONArray json = null;
        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(priceUrl));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            //只有一行数据，如 [{"id":"J_2172006","p":"108.00","m":"168.00"}]
            json = JSON.parseArray(in.readLine());
            this.price = json.getJSONObject(0).getDouble("p");
        } catch (Exception e){
        } finally {
            if (in != null) {
                try {
                    in.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //根据请求获取商品评论
    //例子："http://club.jd.com/productpage/p-1397092632-s-0-t-3-p-0.html"
    private void setComment(String commentUrl){
        JSONObject json = null;
        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(commentUrl));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"gbk"));
            json = JSON.parseObject(in.readLine());
            JSONObject productCommentSummary = json.getJSONObject("productCommentSummary");
            //总评价数
            int commentCount = productCommentSummary.getInteger("commentCount");
            //好评率
            double goodRate = productCommentSummary.getDouble("goodRate");
            this.commentCount = commentCount;
            this.goodRate = goodRate;
        } catch (Exception e){
        } finally {
            if (in != null) {
                try {
                    in.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public String getDetail() {
        return detail;
    }

    public String getUrl() {
        return url;
    }

    public Double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public String getSrcPic() {
        return srcPic;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "JDBean{" +
                "url='" + url + '\'' +
                ", number='" + number + '\'' +
                ", srcPic='" + srcPic + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", detail='" + detail + '\'' +
                ", commentCount=" + commentCount +
                ", goodRate=" + goodRate +
                '}';
    }
}
