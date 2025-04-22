
package com.demo.mall.bean.enums;

// 文件上傳方式 1. 本地文件上傳 2. 七牛雲
public enum UploadType {

    // 本地文件上傳
    LOCAL(1),

    // 七牛雲
    QINIU(2);

    private Integer num;

    public Integer value() {
        return num;
    }

    UploadType(Integer num) {
        this.num = num;
    }

    public static UploadType instance(Integer value) {
        UploadType[] enums = values();
        for (UploadType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
