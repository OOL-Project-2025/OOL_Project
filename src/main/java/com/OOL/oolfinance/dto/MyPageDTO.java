package com.OOL.oolfinance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : MyPageDTO
 * @date : 2025. 8. 27. / 오후 11:47
 * @modifyed :
 **/

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "MyPageDTO", description = "mypage용 DTO")
public class MyPageDTO {
    @Schema(name = "providerId", type = "String", description = "유저 id")
    private String providerId;

    @Schema(name = "memberPassword", type = "String", description = "유저 password")
    private String memberPassword;

    @Schema(name = "memberNickname", type = "String", description = "유저 닉네임")
    private String memberNickname;

    @Schema(name = "provider", type = "String", description = "플랫폼")
    private String provider;

    @Schema(name = "image", type = "String", description = "이미지")
    private String image;
}
