# ZhiHuSpider简介

2017.1.21更新:
----
爬取知乎20万用户信息并用Echarts展示</br>
详情地址：https://zhuanlan.zhihu.com/p/24973518</br>
展示地址：http://kkys.online/zhihu/spider.html</br>
目录：src/main/java/java/zhiHuUserSprider </br>

2016.12.1更新:
----
添加爬取京东所有商品</br>
目录：src/main/java/java/JDCrawler </br>

2016.3.22更新:
----
添加爬取某话题下精华问题中第一个用户的信息</br>
利用JDBC把信息导入本地或远程数据库中</br>
用来进行数据分析或数据挖掘</br>
并且优化了代码，层次更加清晰</br>

2016.3.18
----
原生Java无框架无jar包</br>
实现爬取知乎问题中的图片和知乎推荐内容</br>
并下载到本地</br>
通俗易懂，简介明了，可扩展性更高</br>
    

#ZhiHuSpider结构介绍

    zhihu      早期爬虫
               
    JDCrawler   爬取京东商品信息
    
    zhihuUser   爬取知乎百万级用户信息
    
#想法与问题...

    报No subject alternative DNS name matching错的兄弟
    把url里的https，手动改成http就好

    已实现爬取万级数据爬虫，下一步往分布式上靠吧.
