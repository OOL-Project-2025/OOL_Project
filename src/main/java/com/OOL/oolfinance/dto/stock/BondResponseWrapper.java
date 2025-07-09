package com.OOL.oolfinance.dto.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : BondResponseWrapper
 * @date : 2025. 7. 9. / 오전 12:25
 * @modifyed :
 **/

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BondResponseWrapper {
    private List<BondRequest> result;
}
