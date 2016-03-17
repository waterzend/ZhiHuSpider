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
		String url = "http://www.zhihu.com/question/30355808";
		// 构造方法传url，获取ZhiHuPicBean
		ZhiHuPicBean myZhihu = new ZhiHuPicBean(url);
		// 获取ZhiHuPicBean中的图片列表
		ArrayList<String> picList = myZhihu.getZhihuPicUrl();
		// 打印结果
		System.out.println("标题：" + myZhihu.getQuestion());
		// 循环，在控制台打印图片地址
		for (String zhiHuPic : picList) {
			System.out.println(zhiHuPic);
		}
		// 把图片下载到本地文件夹
		FileReaderWriter.downLoadPics(myZhihu, "D:/知乎爬虫");

		/**
		 * 爬知乎推荐的内容
		 */

		// // 定义即将访问的链接
		// String url = "http://www.zhihu.com/question/30355808";
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
