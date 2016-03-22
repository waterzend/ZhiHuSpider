# ZhiHuSpider简介
2016.3.22更新:
添加爬取某话题下精华问题中第一个用户的信息
利用JDBC把信息导入本地或远程数据库中
用来进行数据分析或数据挖掘
并且优化了代码，层次更加清晰

2016.3.18
原生Java无框架无jar包
实现爬取知乎问题中的图片和知乎推荐内容
并下载到本地
通俗易懂，简介明了，可扩展性更高

#ZhiHuSpider结构介绍

    src
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
        
#想法与问题...
目前模拟登陆和java操纵js模拟点击'更多'等按钮功能还未实现
还想做一个网页，通过输入url直接显示
大家有什么问题和想法直接私我，共同学习
下一次更新没准就可以拉取知乎百万级用户数据了
未完...



    
