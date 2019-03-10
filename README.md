# seckill
second kill
秒杀

##一 秒杀流程

###整体流程图
![流程](https://raw.githubusercontent.com/20160805xcy/staticSource/master/seckill/image/%E5%89%8D%E7%AB%AF%E9%A1%B5%E9%9D%A2%E5%92%8C%E8%AF%A6%E6%83%85%E9%A1%B5%E6%B5%81%E7%A8%8B.png)
###前端js处理秒杀逻辑
1. 倒计时插件
2. cookie插件

##二 秒杀优化

###原来的秒杀逻辑

1. 方法1: 从优化秒杀逻辑入手,减少网络延迟和GC影响

![简单优化](https://raw.githubusercontent.com/20160805xcy/staticSource/master/seckill/image/simpleStrong.png)

代码层面只需要这么改一下就可以达到优化的效果
![代码修改](https://raw.githubusercontent.com/20160805xcy/staticSource/master/seckill/image/%E4%BB%A3%E7%A0%81%E6%89%A7%E8%A1%8C%E9%A1%BA%E5%BA%8F%E4%BF%AE%E6%94%B9.png)

2. 方法2:深度优化,事务sql在mysql端执行(存储过程)


3. 总结
![总结](https://raw.githubusercontent.com/20160805xcy/staticSource/master/seckill/image/%E5%88%86%E6%9E%90%E6%80%BB%E7%BB%93.png)

红色部分是可能会出现高并发的点.绿色代表没有影响.

1. 为什么要单独获取系统时间. 用户会不断刷新页面,将css.js资源改成CDN方式获取,以达到快速的响应,同时减少自己系统的压力.

2. Java获取系统时间不用优化,访问一次内存(Cacheline)大约10ns.(折算下来一秒钟可以访问1亿次)

3. 地址接口分析:无法使用CDN缓存,CDN只适合放一些静态资源,js,css,一些不会变化的数据.  可以使用redis做缓存. 一致性维护成本低.