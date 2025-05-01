/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author lanhai
 */
@Data
public class DeliveryDto {

	@Schema(description = "物流公司名稱")
	private String companyName;
	
	@Schema(description = "物流公司網站")
	private String companyHomeUrl;
	
	@Schema(description = "物流訂單號")
	private String dvyFlowId;
	
	@Schema(description = "查詢到的物流訊息")
	private List<DeliveryInfoDto> data;

}
