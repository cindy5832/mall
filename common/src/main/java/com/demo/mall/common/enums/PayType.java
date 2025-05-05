/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.common.enums;


public enum PayType {

	WECHATPAY(1,"微信支付"),

	ALIPAY(2,"支付寶");

	private Integer num;
	
	private String payTypeName;
	
	public Integer value() {
		return num;
	}
	
	public String getPayTypeName() {
		return payTypeName;
	}
	
	PayType(Integer num, String payTypeName){
		this.num = num;
		this.payTypeName = payTypeName;
	}
	
	public static PayType instance(Integer value) {
		PayType[] enums = values();
		for (PayType statusEnum : enums) {
			if (statusEnum.value().equals(value)) {
				return statusEnum;
			}
		}
		return null;
	}
}
