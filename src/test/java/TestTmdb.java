import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wushuo.tmdb.api.TmdbUtil;
import wushuo.tmdb.api.common.GsonStatic;
import wushuo.tmdb.api.entity.Tmdb;
import wushuo.tmdb.api.entity.TmdbConfig;
import wushuo.tmdb.api.enums.TmdbTypeEnum;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class TestTmdb {

    TmdbConfig config = new TmdbConfig("450e4f651e1c93e31383e20f8e731e5f");
    TmdbUtil tmdbUtil = new TmdbUtil(config);

    @Test
    public void test() {
        List<Tmdb> tmdbList = tmdbUtil.search("叹气的亡灵想隐退 第2部分", TmdbTypeEnum.TV);
        for (Tmdb tmdb : tmdbList) {
            log.info("{}", GsonStatic.toJson(tmdb));
        }
    }

    /**
     * 补全标语
     */
    @Test
    public void completionTagline() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        List<File> list = FileUtil.loopFiles("/Users/wushuo/Movies/test")
                .stream()
                .filter(it -> {
                    String name = it.getName();
                    return name.equals("tvshow.nfo");
                })
                .toList();

        int index = 0;
        for (File tvShowNfo : list) {
            log.info("{}/{} {}", ++index, list.size(), tvShowNfo);

            Document document = documentBuilder.parse(tvShowNfo);
            Element element = document.getDocumentElement();
            Element titleEl = XmlUtil.getElement(element, "title");
            Element tmdbidEl = XmlUtil.getElement(element, "tmdbid");
            Element taglineEl = XmlUtil.getElement(element, "tagline");

            String title = titleEl.getTextContent();
            if (Objects.nonNull(taglineEl)) {
                log.info("已存在标语 {} {}", title, taglineEl.getTextContent());
                continue;
            }
            String tmdbId = tmdbidEl.getTextContent();
            Optional<Tmdb> tmdbOptional = tmdbUtil.getTmdb(new Tmdb().setId(tmdbId), TmdbTypeEnum.TV);
            if (tmdbOptional.isEmpty()) {
                log.warn("{} 未找到对应的tmdb", title);
                continue;
            }
            Tmdb tmdb = tmdbOptional.get();
            String tagline = tmdb.getTagline();
            if (StrUtil.isBlank(tagline)) {
                log.info("{} 未找到对应标语", title);
                continue;
            }
            addElement(document, element, "tagline", tagline);
            log.info("{} {}", title, tagline);
            saveXmlDocument(document, tvShowNfo.getAbsolutePath());
        }


    }

    /**
     * 添加元素
     *
     * @param doc     Document
     * @param parent  父级
     * @param tagName 标签名
     * @param value   文本值
     */
    private void addElement(Document doc, Element parent, String tagName, Object value) {
        if (Objects.isNull(value)) {
            return;
        }

        String s = value.toString();

        if (StrUtil.isBlank(s)) {
            return;
        }

        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(s));
        parent.appendChild(element);
    }

    /**
     * 保存
     *
     * @param doc      Document
     * @param savePath 保存位置
     * @throws Exception
     */
    private static void saveXmlDocument(Document doc, String savePath) throws Exception {
        FileUtil.mkdir(new File(savePath).getParentFile());

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        FileUtil.del(savePath);

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(savePath));
        transformer.transform(source, result);

        log.info("已保存NFO {}", savePath);
    }
}
