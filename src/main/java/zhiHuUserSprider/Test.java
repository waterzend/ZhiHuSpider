package zhiHuUserSprider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试爬知乎虫
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Pattern pattern;
        Matcher matcher;
        String userContent = "2931844 次赞同 获得 490842";
        pattern = Pattern.compile(".*?(^[0-9]*[1-9][0-9]*$).*?");
        matcher = pattern.matcher(userContent);
        if(matcher.find()){
            System.out.println(matcher.group());
        }

    }

}

