package com.OOL.oolfinance.dto;

import com.OOL.oolfinance.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : WishlistResponse
 * @date : 2025. 6. 3. / 오전 1:34
 * @modifyed :
 **/

@Data
@Builder
public class WishlistResponse {

    private StatusEnum status;

    private String message;

    private List<WishlistDTO> data;
}
