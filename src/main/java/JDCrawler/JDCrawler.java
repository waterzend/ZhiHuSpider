package JDCrawler;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 商城爬虫
 */
public class JDCrawler {

    Set<String> allurlSet = new HashSet<>();//所有的网页url，用来去重
    ArrayList<String> notCrawlurlSet = new ArrayList<>();//未爬过的网页url
    HashMap<String, Integer> depth = new HashMap<>();//所有网页的url深度
    int crawDepth  = 10; //爬虫深度
    int threadCount = 10; //线程数量
    int count = 0; //表示有多少个线程处于wait状态
    public static final Object signal = new Object();   //线程间通信变量

    public static void main(String[] args) {
        final JDCrawler wc = new JDCrawler();
        wc.parseHomePage("http://www.jd.com/");
        long start= System.currentTimeMillis();
        System.out.println("开始爬虫.........................................");
        wc.begin();

        while(true){
            if(wc.notCrawlurlSet.isEmpty()&& Thread.activeCount() == 1||wc.count==wc.threadCount){
                long end = System.currentTimeMillis();
                System.out.println("总共爬了"+wc.allurlSet.size()+"个网页");
                System.out.println("总共耗时"+(end-start)/1000+"秒");
                System.exit(1);
//              break;
            }
        }
    }

    private void begin() {

        for(int i=0;i<threadCount;i++){
            new Thread(new Runnable(){
                public void run() {

                    while (true) {
//                      System.out.println("当前进入"+Thread.currentThread().getName());
                        String tmp = getAUrl();
                        if(tmp!=null){
                            crawler(tmp);
                        }else{
                            synchronized(signal) {  //------------------（2）
                                try {
                                    count++;
                                    System.out.println("当前有"+count+"个线程在等待");
                                    signal.wait();
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            },"thread-"+i).start();
        }
    }

    public synchronized String getAUrl() {
        if(notCrawlurlSet.isEmpty())
            return null;
        String tmpAUrl;
//      synchronized(notCrawlurlSet){
        tmpAUrl= notCrawlurlSet.get(0);
        notCrawlurlSet.remove(0);
//      }
        return tmpAUrl;
    }

    public synchronized void addUrl(String url,int d){
        notCrawlurlSet.add(url);
        allurlSet.add(url);
        depth.put(url, d);
    }

    //爬商品详情Url
    public void crawler(String sUrl){
        URL url;
        InputStream is = null;
        try {
            url = new URL(sUrl);
            URLConnection urlconnection = url.openConnection();
            urlconnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            is = url.openStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is,"gbk"));
            StringBuffer sb = new StringBuffer();//sb为爬到的网页内容
            String rLine = null;
            while((rLine=bReader.readLine())!=null){
                sb.append(rLine);
            }
            int d = depth.get(sUrl);
            System.out.println("爬网页"+sUrl+"成功，深度为"+d+" 是由线程"+Thread.currentThread().getName()+"来爬");
            if(d<crawDepth){
                String content = sb.toString();
                //解析详情页内容，从中提取内容和下一个链接
                parseContext(content,d+1,sUrl);
            }
        } catch (IOException e) {
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

    //从context提取url地址
    public  void parseContext(String context,int dep,String url) {
        Pattern pattern;
        Matcher matcher;
        /**
         * 判断是否为商品详情页
         * 是的话，构造bean
         * 不是的话，向下执行
         */
        pattern = Pattern.compile("item.jd.com/(.+?).html");
        matcher = pattern.matcher(url);
        if(matcher.find()&&matcher.group(1)!=null){
            JDBean commodity = new JDBean(context,url);
            System.out.println(commodity);
        }
        //匹配下一个商品详情页
        pattern = Pattern.compile("<a href.*?/a>");
        matcher = pattern.matcher(context);
        while (matcher.find()) {
            Matcher detailUrl = Pattern.compile("href=\"//item.jd.com/.*?\"").matcher(
                    matcher.group());
            while(detailUrl.find()){
                String str = detailUrl.group().replaceAll("href=\"|\"", "");
                str = "http:"+str;
                if(!allurlSet.contains(str)){
            //        System.out.println("网址是:"+ str);
                    addUrl(str, dep);//加入一个新的url
                    if(count>0){ //如果有等待的线程，则唤醒
                        synchronized(signal) {  //---------------------（2）
                            System.out.println("唤醒了线程，当前等待线程:"+ (count-1));
                            count--;
                            signal.notify();
                        }
                    }
                }
            }
        }
    }

    //从获取主页上分类的url
    public void parseHomePage(String sUrl) {
        URL url;
        InputStream is = null;
        try {
            url = new URL(sUrl);
            URLConnection urlconnection = url.openConnection();
            urlconnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            is = url.openStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();//sb为爬到的网页内容
            String rLine = null;
            while((rLine=bReader.readLine())!=null){
                sb.append(rLine);
                sb.append("/r/n");
            }
            String regex = "href.*?>";
            Pattern pt = Pattern.compile(regex);
            Matcher mt = pt.matcher(sb.toString());
            System.out.println(sb.toString());
            System.out.println(mt);
            while (mt.find()) {
                Matcher typeUrl = Pattern.compile("href=\"//.*?.jd.com/\"|href=\"//channel.jd.com/.*?.html\"").matcher(
                        mt.group());
                while(typeUrl.find()){
                    String str = typeUrl.group().replaceAll("href=\"|\"", "");
                    str = "http:"+str;
                    if(!allurlSet.contains(str)){
                        System.out.println("类型网址是:"+ str);
                        addUrl(str, 1);//加入一个新的url
                        if(count>0){ //如果有等待的线程，则唤醒
                            synchronized(signal) {  //---------------------（2）
                                count--;
                                signal.notify();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}