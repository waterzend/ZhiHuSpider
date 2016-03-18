# ZhiHuSpider

ZhiHuSpider简介

    原生Java无框架无jar包
    实现爬取知乎问题中的图片和知乎推荐内容
    并下载到本地
    通俗易懂，简介明了，可扩展性更高

ZhiHuSpider结构介绍

    src
         bean
               ZhiHuBean.java ----- 爬取推荐内容的bean
               ZhiHuBeanPicBean.java ----- 爬取问题中图片地址的bean
         mothod
               FlieReadWriter.java ----- 创建文件，下载推荐内容或图片的方法
               Spider.java() ----- 获取网页内容的方法
         test
               Main.java ----- 测试，抓取baidu的logo
               ZhiHuMain.java ----- 测试，下载图片或推荐内容
        
未完...

    准备做个界面，用户输入url和磁盘地址，以及是否分页或显示全部答案
    进一步爬些数据到redis或mysql，进行些数据分析
