# TMDB API Java 封装库

一个简洁易用的 Java 封装库，用于访问 [The Movie Database (TMDB)](https://www.themoviedb.org/) API，支持电影和电视剧的搜索、信息获取等功能。

## 功能特性

- 🎬 **电影和电视剧搜索** - 根据标题名称搜索电影或电视剧
- 📺 **详细信息获取** - 获取完整的影视信息，包括概述、评分、发布日期等
- 🌍 **多语言支持** - 支持多语言翻译和标题获取
- 🎭 **演职人员信息** - 获取演员和制作人员信息
- 🖼️ **图片资源** - 获取海报、背景图、Logo 等图片资源
- 🎥 **视频资源** - 获取预告片和宣传视频
- 📚 **剧集信息** - 获取电视剧的季和集信息
- 🏷️ **替代标题** - 获取不同地区的标题名称
- 🎌 **动漫过滤** - 可选过滤动漫类型内容

## 依赖要求

- Java 17+
- Maven 3.6+

## 安装方式

### Maven 依赖

```xml
<repositories>
    <repository>
        <id>tmdb-api</id>
        <url>https://raw.github.com/wushuo894/tmdb-api/mvn-repo</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>wushuo.tmdb.api</groupId>
        <artifactId>tmdb-api</artifactId>
        <version>1.0.2</version>
    </dependency>
</dependencies>

```

### 本地安装

```bash
mvn clean install
```

## 快速开始

### 1. 获取 TMDB API Key

首先需要在 [TMDB 官网](https://www.themoviedb.org/settings/api) 注册账号并获取 API Key。

### 2. 创建配置

```java
// 使用默认配置（API 地址和语言）
TmdbConfig config = new TmdbConfig("your-api-key");

// 或自定义配置
TmdbConfig config = new TmdbConfig(
    "your-api-key",                    // API Key
    "https://api.themoviedb.org",      // API 地址
    "zh-CN"                            // 语言代码
);

// 可选配置
config.setTmdbAnime(true);            // 是否过滤动漫
config.setProxy(true);                 // 是否使用代理
config.setProxyHost("127.0.0.1");     // 代理地址
config.setProxyPort(7890);             // 代理端口
```

### 3. 创建工具实例

```java
TmdbUtil tmdbUtil = new TmdbUtil(config);
```

## 使用示例

### 搜索电影

```java
// 搜索电影
Optional<Tmdb> movie = tmdbUtil.getTmdbMovie("肖申克的救赎");
if (movie.isPresent()) {
    Tmdb tmdb = movie.get();
    System.out.println("名称: " + tmdb.getName());
    System.out.println("概述: " + tmdb.getOverview());
    System.out.println("评分: " + tmdb.getVoteAverage());
}

// 搜索多个结果
List<Tmdb> movies = tmdbUtil.search("肖申克", TmdbTypeEnum.MOVIE);
```

### 搜索电视剧

```java
// 搜索电视剧
Optional<Tmdb> tv = tmdbUtil.getTmdbTv("权力的游戏");
if (tv.isPresent()) {
    Tmdb tmdb = tv.get();
    System.out.println("名称: " + tmdb.getName());
}

// 搜索多个结果
List<Tmdb> tvShows = tmdbUtil.search("权力的游戏", TmdbTypeEnum.TV);
```

### 获取详细信息

```java
Optional<Tmdb> tmdb = tmdbUtil.getTmdbMovie("肖申克的救赎");
if (tmdb.isPresent()) {
    // 刷新并获取完整信息（包括翻译、演职人员、视频等）
    Optional<Tmdb> fullInfo = tmdbUtil.getTmdb(tmdb.get(), TmdbTypeEnum.MOVIE);
    if (fullInfo.isPresent()) {
        Tmdb info = fullInfo.get();
        System.out.println("宣传语: " + info.getTagline());
        System.out.println("视频数量: " + info.getVideos().size());
    }
}
```

### 获取剧集信息

```java
Optional<Tmdb> tv = tmdbUtil.getTmdbTv("权力的游戏");
if (tv.isPresent()) {
    // 获取第一季信息
    Optional<TmdbSeason> season = tmdbUtil.getTmdbSeason(tv.get(), 1);
    if (season.isPresent()) {
        TmdbSeason s = season.get();
        System.out.println("季名称: " + s.getName());
        System.out.println("集数: " + s.getEpisodes().size());
    }
    
    // 获取第一季所有集的标题
    Map<Integer, String> episodeTitles = tmdbUtil.getEpisodeTitleMap(tv.get(), 1);
    episodeTitles.forEach((episodeNum, title) -> {
        System.out.println("第" + episodeNum + "集: " + title);
    });
}
```

### 获取演职人员

```java
Optional<Tmdb> movie = tmdbUtil.getTmdbMovie("肖申克的救赎");
if (movie.isPresent()) {
    List<TmdbCredits> credits = tmdbUtil.getCredits(movie.get(), TmdbTypeEnum.MOVIE);
    credits.forEach(credit -> {
        System.out.println("演员: " + credit.getName() + " - " + credit.getCharacter());
    });
}
```

### 获取图片资源

```java
Optional<Tmdb> movie = tmdbUtil.getTmdbMovie("肖申克的救赎");
if (movie.isPresent()) {
    TmdbImages images = tmdbUtil.getTmdbImages(movie.get(), TmdbTypeEnum.MOVIE);
    
    // 获取海报（已按语言和评分排序）
    List<TmdbImage> posters = images.getPosters();
    if (!posters.isEmpty()) {
        String posterPath = posters.get(0).getFilePath();
        System.out.println("海报: " + posterPath);
    }
    
    // 获取背景图
    List<TmdbImage> backdrops = images.getBackdrops();
    
    // 获取 Logo
    List<TmdbImage> logos = images.getLogos();
}
```

### 获取视频资源

```java
Optional<Tmdb> movie = tmdbUtil.getTmdbMovie("肖申克的救赎");
if (movie.isPresent()) {
    List<TmdbVideo> videos = tmdbUtil.getVideos(movie.get(), TmdbTypeEnum.MOVIE);
    videos.forEach(video -> {
        System.out.println("视频类型: " + video.getType());
        System.out.println("视频 Key: " + video.getKey());
    });
}
```

### 获取替代标题

```java
Optional<Tmdb> movie = tmdbUtil.getTmdbMovie("肖申克的救赎");
if (movie.isPresent()) {
    List<TmdbTitle> titles = tmdbUtil.getTitles(movie.get(), TmdbTypeEnum.MOVIE);
    titles.forEach(title -> {
        System.out.println("标题: " + title.getTitle() + " (" + title.getIso31661() + ")");
    });
}
```

### 获取剧集组

```java
Optional<Tmdb> tv = tmdbUtil.getTmdbTv("权力的游戏");
if (tv.isPresent()) {
    List<TmdbGroup> groups = tmdbUtil.getTmdbGroup(tv.get());
    groups.forEach(group -> {
        System.out.println("组名称: " + group.getName());
        System.out.println("组类型: " + group.getTypeName());
    });
}
```

## API 说明

### TmdbUtil 主要方法

| 方法 | 说明 | 返回类型 |
|------|------|----------|
| `getTmdbMovie(String titleName)` | 根据标题获取电影信息 | `Optional<Tmdb>` |
| `getTmdbTv(String titleName)` | 根据标题获取电视剧信息 | `Optional<Tmdb>` |
| `getTmdb(String titleName, TmdbTypeEnum)` | 根据标题和类型获取信息 | `Optional<Tmdb>` |
| `search(String titleName, TmdbTypeEnum)` | 搜索电影或电视剧 | `List<Tmdb>` |
| `getTmdb(Tmdb, TmdbTypeEnum)` | 刷新并获取完整信息 | `Optional<Tmdb>` |
| `getTmdbSeason(Tmdb, Integer)` | 获取指定季的信息 | `Optional<TmdbSeason>` |
| `getEpisodeTitleMap(Tmdb, Integer)` | 获取指定季的所有集标题 | `Map<Integer, String>` |
| `getCredits(Tmdb, TmdbTypeEnum)` | 获取演职人员信息 | `List<TmdbCredits>` |
| `getTmdbImages(Tmdb, TmdbTypeEnum)` | 获取图片资源 | `TmdbImages` |
| `getVideos(Tmdb, TmdbTypeEnum)` | 获取视频资源 | `List<TmdbVideo>` |
| `getTitles(Tmdb, TmdbTypeEnum)` | 获取替代标题 | `List<TmdbTitle>` |
| `getTmdbGroup(Tmdb)` | 获取剧集组 | `List<TmdbGroup>` |

### TmdbTypeEnum 枚举

- `MOVIE` - 电影
- `TV` - 电视剧

## 配置说明

### TmdbConfig 配置项

| 配置项 | 类型 | 说明 | 默认值 |
|--------|------|------|--------|
| `tmdbApi` | String | TMDB API 地址 | `https://api.themoviedb.org` |
| `tmdbApiKey` | String | TMDB API Key（必需） | - |
| `tmdbLanguage` | String | 语言代码 | `zh-CN` |
| `tmdbAnime` | Boolean | 是否过滤动漫 | `false` |
| `proxy` | Boolean | 是否使用代理 | `false` |
| `proxyHost` | String | 代理地址 | - |
| `proxyPort` | Integer | 代理端口 | - |
| `proxyUsername` | String | 代理用户名 | - |
| `proxyPassword` | String | 代理密码 | - |

## 注意事项

1. **API Key 必需**：使用前必须获取有效的 TMDB API Key
2. **网络请求**：所有方法都会发起网络请求，请确保网络连接正常
3. **请求超时**：默认请求超时时间为 5 秒
4. **图片 URL**：获取的图片路径为相对路径，需要拼接 TMDB 图片基础 URL
5. **动漫过滤**：启用 `tmdbAnime` 后，搜索结果会过滤出类型为动漫（genre ID: 16）的内容

## 许可证

本项目采用开源许可证，详情请查看 LICENSE 文件。

## 相关链接

- [TMDB 官网](https://www.themoviedb.org/)
- [TMDB API 文档](https://developers.themoviedb.org/3)
- [项目 GitHub](https://github.com/wushuo894/tmdb-api)

