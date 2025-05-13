package com.demo.mall.common.xss;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

public class XssUtil {
    // 使用自帶的basicWithImages 白名單
    private static final Safelist WHITE_LIST = Safelist.relaxed();
    // 配置過濾參數，不對代碼進行格式化
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    static {
        // 當文本編輯時，有些樣式是使用style進行實現
        // 如紅色字體 style="color:red" 所以須給所有標籤添加style屬性
        WHITE_LIST.addAttributes(":all", "style");
    }

    public static String clean(String content) {
        return Jsoup.clean(content, "", WHITE_LIST, OUTPUT_SETTINGS);
    }
}
