<p align="center">
  <a href="https://android-arsenal.com/api?level=19">
    <img src="https://img.shields.io/badge/API-19%2B-blueviolet.svg?style=flat-square" alt="Min Sdk Version" />
  </a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0">
     <img src="http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square" alt="License" />
  </a>
  <a href="#"><img src="https://img.shields.io/badge/%E9%9D%A2%E5%90%91-Android%E5%BC%80%E5%8F%91-%232CC159.svg"></a>
  <a href="#"><img src="https://img.shields.io/badge/language-kotlin-orange.svg"></a>
  <a href="https://github.com/Vension">
    <img src="https://img.shields.io/badge/Author-Vension-brightgreen.svg?style=flat-square" alt="Author" />
  </a>
  <a href="#"><img src="https://img.shields.io/github/languages/count/unboundedcode/V-FastFrame.svg"></a>
  <a href="#"><img src="https://img.shields.io/github/languages/top/unboundedcode/V-FastFrame.svg?style=flat-square"></a>
  <a href="https://github.com/unboundedcode/V-FastFrame/releases">
    <img src="https://img.shields.io/github/release/unboundedcode/V-FastFrame.svg">
  </a>
  <a href="https://bintray.com/vension/maven/V-FastFrame/_latestVersion">
     <img src="https://api.bintray.com/packages/vension/maven/V-FastFrame/images/download.svg?style=flat-square" alt="JCenter" />
  </a>
  <a href="#"><img src="https://img.shields.io/github/repo-size/unboundedcode/V-FastFrame.svg?style=flat-square"></a>
  <a href="#"><img src="https://img.shields.io/github/languages/code-size/unboundedcode/V-FastFrame.svg?style=flat-square"></a>
  <a href="#"><img src="https://img.shields.io/github/downloads/unboundedcode/V-FastFrame/total.svg?style=flat-square"></a>
</p>

# V-FastFrame
无界编码，快捷构建，助你起飞 - use Kotlin abd support androidX

## Remark
（**商用慎重**）**FastFrame**是一个提供了常用模块的**个人快捷开发框架**，采用Glide4.9+rxjava2+retrofit2+mvp的组件化开发模式，不断完善中。。。
<br>
主要包括 **网络请求**、**图片加载**、**数据库**、**事件总线**、**缓存**、**权限管理**、**工具类** 等模块。
其中，网络请求使用Retrofit+RxJava实现，图片加载使用Glide实现（可替换），事件总线使用EventBus实现（可替换），数据库使用Room实现（未实现），权限管理使用RxPermission实现。
<br>

## Download （maven上传遇到问题，骚等哈,有解决办法的请指教）[ ![Download](https://api.bintray.com/packages/vension/maven/V-FastFrame/images/download.svg) ](https://bintray.com/vension/maven/V-FastFrame/_latestVersion)
``` gradle
    api 'kv.vension:fastframe:_latestVersion'
```

## Screenshots

#### fastframe
<a href="screenshots/fastframe_1.png"><img src="screenshots/fastframe_1.png" width="30%"/></a>
<a href="screenshots/fastframe_2.png"><img src="screenshots/fastframe_2.png" width="30%"/></a>
<a href="screenshots/fastframe_3.png"><img src="screenshots/fastframe_3.png" width="30%"/></a>

#### wanAndroid
<a href="screenshots/wan/wan_1.png"><img src="screenshots/wan/wan_1.png" width="30%"/></a>
<a href="screenshots/wan/wan_2.png"><img src="screenshots/wan/wan_2.png" width="30%"/></a>
<a href="screenshots/wan/wan_3.png"><img src="screenshots/wan/wan_3.png" width="30%"/></a>
<a href="screenshots/wan/wan_4.png"><img src="screenshots/wan/wan_4.png" width="30%"/></a>
<a href="screenshots/wan/wan_5.png"><img src="screenshots/wan/wan_5.png" width="30%"/></a>
<a href="screenshots/wan/wan_6.png"><img src="screenshots/wan/wan_6.png" width="30%"/></a>
<a href="screenshots/wan/wan_7.png"><img src="screenshots/wan/wan_7.png" width="30%"/></a>
<a href="screenshots/wan/wan_8.png"><img src="screenshots/wan/wan_8.png" width="30%"/></a>

#### 微课
<a href="screenshots/wk/one.png"><img src="screenshots/wk/one.png" width="30%"/></a> 
<a href="screenshots/wk/two.png"><img src="screenshots/wk/two.png" width="30%"/></a>
<a href="screenshots/wk/three.png"><img src="screenshots/wk/three.png" width="30%"/></a>
<a href="screenshots/wk/four.png"><img src="screenshots/wk/four.png" width="30%"/></a> 
<a href="screenshots/wk/five.png"><img src="screenshots/wk/five.png" width="30%"/></a>
<a href="screenshots/wk/.png"><img src="screenshots/wk/six.png" width="30%"/></a>
<a href="screenshots/wk/seven.png"><img src="screenshots/wk/seven.png" width="90%"/></a> 

#### News
<a href="screenshots/news/news_1.png"><img src="screenshots/news/news_1.png" width="30%"/></a> 
<a href="screenshots/news/news_2.png"><img src="screenshots/news/news_2.png" width="30%"/></a> 
<a href="screenshots/news/news_3.png"><img src="screenshots/news/news_3.png" width="30%"/></a> 
<a href="screenshots/news/news_4.png"><img src="screenshots/news/news_4.png" width="30%"/></a> 
<a href="screenshots/news/news_5.png"><img src="screenshots/news/news_5.png" width="30%"/></a> 

## 版本信息
 - v0.0.2 （2019-09-03）
   - 优化代码，使用策略模式实现时间总线、图片加载模块可动态切换
   - 新增自定义Toast等工具类
 - v0.0.1 （2019-07-05）
   - 初步完成基础功能
   
## License


```
 Copyright 2019, Vension

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
