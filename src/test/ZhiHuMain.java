package test;

import java.util.ArrayList;
import bean.ZhiHuPicBean;
import mothed.FileReaderWriter;

/**
 * 抓取知乎图片并下载 抓取知乎推荐内容并写入本地
 * 
 * @author KKys
 *
 */
public class ZhiHuMain {

	public static void main(String[] args) throws Exception {
		/**
		 * 爬知乎图片，并下载到本地
		 */
		// 定义即将访问的链接
		String url = "https://www.zhihu.com/question/28594126";
		// 构造方法传url，获取ZhiHuPicBean
		ZhiHuPicBean myZhihu = new ZhiHuPicBean(url);
		// 获取ZhiHuPicBean中的图片列表
		ArrayList<String> picList = myZhihu.getZhihuPicUrl();
		// 打印结果
		System.out.println("");
		System.out.println("标题：" + myZhihu.getQuestion());
		System.out.println("");
		// 循环，在控制台打印图片地址
		for (String zhiHuPic : picList) {
			System.out.println(zhiHuPic);
		}
		System.out.println("");
		//定义下载路径
		String addr = "D:/知乎爬虫/";
		System.out.println("即将开始下载图片到"+addr+myZhihu.getQuestion());
		System.out.println("");
		System.out.println("开始下载................");
		System.out.println("");
		// 把图片下载到本地文件夹
		FileReaderWriter.downLoadPics(myZhihu, addr);
		System.out.println("");
		System.out.println("图片下载完毕，请到"+addr+myZhihu.getQuestion()+"里去看看吧！！！");

		/**
		 * 爬知乎推荐的内容
		 */
		// // 定义即将访问的链接
		// String url = "https://www.zhihu.com/explore/recommendations";
		// // 访问链接并获取页面内容
		// String content = Spider.SendGet(url);
		// // 获取该页面的所有的知乎对象
		// ArrayList<ZhiHuBean> myZhihu = Spider.GetRecommendations(content);
		// for (ZhiHuBean zhihu : myZhihu) {
		// FileReaderWriter.writeIntoFile(zhihu.writeString(),"D:/知乎编辑推荐.txt",
		// true);
		// }
	}
}
