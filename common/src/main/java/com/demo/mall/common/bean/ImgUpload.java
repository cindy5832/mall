/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.common.bean;

import lombok.Data;

// 本地儲存配置
@Data
public class ImgUpload {

	// 本地上傳文件夾
	private String imagePath;

	// 文件上傳方式 1. 本地文件上傳 2. 七牛雲
	private Integer uploadType;

	// 網站url
	private String resourceUrl;
}
