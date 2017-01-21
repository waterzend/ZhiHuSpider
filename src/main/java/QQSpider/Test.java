package QQSpider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.concurrent.*;

/**
 * 测试QQ空间爬虫，运用selenium模拟浏览器
 */
public class Test {
    public static void main(String[] args){

        System.getProperties().setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        // 获取所有cookie个数
        webDriver.get("https://www.zhihu.com/question/31677442");
        webDriver.manage().deleteAllCookies();
        webDriver.manage().addCookie(new Cookie("__utma","51854390.1933539417.1484371727.1484371727.1484371727.1"));
        webDriver.manage().addCookie(new Cookie("__utmb","51854390.0.10.1484371727"));
        webDriver.manage().addCookie(new Cookie("__utmc","51854390"));
        webDriver.manage().addCookie(new Cookie("__utmv","51854390.100-1|2=registration_date=20120302=1^3=entry_date=20120302=1"));
        webDriver.manage().addCookie(new Cookie("__utmz","51854390.1484370788.1.1.utmcsr=zhihu.com|utmccn=(referral)|utmcmd=referral|utmcct=/collection/64749345"));
        webDriver.manage().addCookie(new Cookie("_xsrf","5636e2584657c9012ece5059fbf117c2"));
        webDriver.manage().addCookie(new Cookie("_zap","74efccc9-367f-481c-89d8-e43e0408f08f"));
        webDriver.manage().addCookie(new Cookie("aliyungf_tc","AQAAAMSnXD6FIgwAgXbCbyQqOcn6ePxr"));
        webDriver.manage().addCookie(new Cookie("cap_id","\"ZGJhOGNlOThlMjlkNDYyYTg2ZTVmOTFiYWQ0Y2MyOTA=|1484370639|02f9b3955417487ab4b1b100659904521a4fac4a\""));
        webDriver.manage().addCookie(new Cookie("d_c0","\"ACCCpEnsJguPTtEeyk6-Z8Z0l7t_J-ceZ6o=|1484370639\""));
        webDriver.manage().addCookie(new Cookie("l_cap_id","\"N2VmZDY5ZmQ1NzQ5NDE4Y2IwYjczYjcwNGI2YTY0YzI=|1484370639|cbb97626130ef5d1328cb0d4f3ca4ef4439d1212\""));
        webDriver.manage().addCookie(new Cookie("login","\"MjUwMGMzMTNkODA3NDQ2OWI4ZjI5MWYzYzU4NTIwMWY=|1484370658|ee7e957d571ad14add00ee86d56a3f1141b666f5\""));
        webDriver.manage().addCookie(new Cookie("nweb_qa","heifetz"));
        webDriver.manage().addCookie(new Cookie("q_c1","3a835723c8074634a099832d6553a878|1484370639000|1484370639000"));
        webDriver.manage().addCookie(new Cookie("r_cap_id","\"NGExYzI0ZmU4YWVmNGI0ZWJiMzY4NmJkMGI3M2ZmYjM=|1484370639|02e6714ad8793c6550fd0dcd3904366274050619\""));
        webDriver.manage().addCookie(new Cookie("z_c0","Mi4wQUFDQTlpZ1pBQUFBSUlLa1Nld21DeGNBQUFCaEFsVk40ai1oV0FCZkFtZ3N6U0JyYWNHVHJsNHRlaWNZNC1MYkpn|1484372819|c669824d1bd715856820f6b2dec4b77157b18413"));

        webDriver.get("https://www.zhihu.com/people/yao-cheng-46");



        WebElement loginBtn = webDriver.findElement(By.cssSelector(".ProfileHeader-contentFooter button"));

        loginBtn.click();

        String content = webDriver.getPageSource();
        System.out.println(content);


        //获取登陆按钮的className，并点击
//          WebElement Btn = webDriver.findElement(By.id("switcher_plogin"));
//        Btn.click();
//        System.out.println(1);
//        //获取输入框的id,并在输入框中输入用户名
//          WebElement loginForm = webDriver.findElement(By.id("SidebarSignFlow"));
//       //   loginInput.findElement(By.className("sns clearfix"));
//           WebElement loginBtn = loginForm.findElement(By.tagName("a"));
//          System.out.println(loginBtn.toString());
//           loginBtn.click();
//        loginInput.sendKeys("88171985");
//        System.out.println(2);
//        //获取输入框的id，并在输入框中输入密码
//        WebElement pwdInput = webDriver.findElement(By.id("p"));
//        pwdInput.sendKeys("ys22ys");
//        System.out.println(3);
//        //获取登陆按钮的className，并点击
//        WebElement loginBtn = webDriver.findElement(By.id("login_button"));
//        loginBtn.click();
//        System.out.println(4);
//      //  String content = webDriver.getPageSource();
////        WebElement webElement = webDriver.findElement(By.xpath("/html"));
////        System.out.println(webElement.getAttribute("outerHTML"));
//
//        System.out.println(content);
//        webDriver.close();
    }


    private static void res(){
        String url = "https://h5.qzone.qq.com/proxy/domain/taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?uin=88171985&ftype=0&sort=0&pos=0&num=20&replynum=100&g_tk=171686857&callback=_preloadCallback&code_version=1&format=jsonp&need_private_comment=1";
        JSONArray json = null;
        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.addHeader(new BasicHeader("RK","1YUjj7GzGW"));
            request.addHeader(new BasicHeader("__Q_w_s_hat_seed","1"));
            request.addHeader(new BasicHeader("blabla","dynamic"));
            request.addHeader(new BasicHeader("p_skey","gTiE1A5wtQIZbn0IRevzMxZoQI9wKikQKB1qaw7XvH8_"));
            request.addHeader(new BasicHeader("pgv_info","ssid=s4468419824"));
            request.addHeader(new BasicHeader("pgv_pvi","8278756352"));
            request.addHeader(new BasicHeader("pgv_pvid","6272751583"));
            request.addHeader(new BasicHeader("pgv_si","s8021849088"));
            request.addHeader(new BasicHeader("pt2gguin","o0088171985"));
            request.addHeader(new BasicHeader("pt4_token","EP8kmW2gqZaAQ6bo2VevhLp"));
            request.addHeader(new BasicHeader("skey","@RFpU8i6L2"));
            request.addHeader(new BasicHeader("ptcz","a354a28ea66b4ce7d8c2d96f451e95c3147d841558b73a894714cad8e7c59381"));
            request.setURI(new URI(url));

            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp = "";

            String s = "";
            while((temp = in.readLine()) != null)
            {
                s = s + temp;
            }
            System.out.println(s);

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
}
