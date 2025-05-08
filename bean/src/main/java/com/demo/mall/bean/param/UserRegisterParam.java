/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.bean.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "設置用戶訊息")
public class UserRegisterParam {

    @Schema(description = "密碼")
    private String passWord;

    @Schema(description = "信箱")
    private String userMail;

    @Schema(description = "暱稱")
    private String nickName;

    @Schema(description = "用戶名")
    private String userName;

    @Schema(description = "手機號")
    private String mobile;

    @Schema(description = "頭像")
    private String img;

    @Schema(description = "校驗登入註冊驗證碼成功的標示")
    private String checkRegisterSmsFlag;

    @Schema(description = "當帳戶未綁定時，臨時的uid")
    private String tempUid;

    @Schema(description = "用户id")
    private Long userId;
}
