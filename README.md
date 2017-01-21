# ZhiHuSpider简介

    2017.1.21更新:
    爬取知乎20万用户信息并用Echarts展示
    详情地址：https://zhuanlan.zhihu.com/p/24973518
    展示地址：http://kkys.online/zhihu/spider.html
    目录：src/main/java/java/zhiHuUserSprider 
    
    -----------------------------------------
    
    2016.12.1更新:
    添加爬取京东所有商品
    目录：src/main/java/java/JDCrawler 
    
    -----------------------------------------
    
    2016.3.22更新:
    添加爬取某话题下精华问题中第一个用户的信息
    利用JDBC把信息导入本地或远程数据库中
    用来进行数据分析或数据挖掘
    并且优化了代码，层次更加清晰
     
    -----------------------------------------
   
    2016.3.18
    原生Java无框架无jar包
    实现爬取知乎问题中的图片和知乎推荐内容
    并下载到本地
    通俗易懂，简介明了，可扩展性更高
    

#ZhiHuSpider结构介绍

    zhihu      早期爬虫
    
         bean
         
               ZhiHuBean.java ----- 爬取推荐内容的bean
               ZhiHuPicBean.java ----- 爬取问题中图片地址的bean
               ZhiHuUserBean.java ----- 爬取用户信息的bean
         mothod
               FlieReadWriter.java ----- 创建文件，下载推荐内容或图片的方法
               Spider.java() ----- 目前支持的三种功能方法
               Util.java() ----- 工具类
         test
               Main.java ----- 测试，抓取baidu的logo
               ZhiHuMain.java ----- 测试，下载图片及推荐内容、爬取用户信息
               
    JDCrawler   爬取京东商品信息
    
    zhihuUser   爬取百万级用户信息
    
#想法与问题...

    报No subject alternative DNS name matching错的兄弟
    把url里的https，手动改成http就好

    已实现爬取万级数据爬虫，下一步往分布式上靠吧.
