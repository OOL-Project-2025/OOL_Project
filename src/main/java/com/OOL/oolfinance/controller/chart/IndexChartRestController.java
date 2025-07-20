package com.OOL.oolfinance.controller.chart;

import com.OOL.oolfinance.dto.chart.ChartResponse;
import com.OOL.oolfinance.dto.chart.IndexChartDTO;
import com.OOL.oolfinance.dto.chart.IndexChartResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.service.chart.IndexChartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : IndexChartRestController
 * @date : 2025. 4. 9. / 오후 2:51
 * @modifyed :
 **/

@Slf4j
@RequestMapping(value = "/api/indexChart")
@RestController
@RequiredArgsConstructor
@Tag(name = "Chart", description = "주식 차트 관련 API")
public class IndexChartRestController {
    private final IndexChartService indexChartService;

    @GetMapping(value = "/{indexCode}")
    @Operation(summary = "지수 차트 데이터 조회", description = "특정 지수의 차트 데이터를 전체 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
    })
    @Parameters(value = {
            @Parameter(name = "indexCode", description = "지수 코드", example = "11111")
    })
    public ResponseEntity<IndexChartResponse> getIndexChartData(@PathVariable(value = "indexCode") String indexCode) {
        log.info("지수 차트 조회");
        List<IndexChartDTO> data = indexChartService.getIndexChartDataAll(indexCode);
        IndexChartResponse response;
        if (data.isEmpty()) {
            response = IndexChartResponse.builder()
                    .status(StatusEnum.BAD_REQUEST)
                    .message("fail")
                    .data(null)
                    .build();
        } else {
            response = IndexChartResponse.builder()
                    .status(StatusEnum.OK)
                    .message("success")
                    .data(data)
                    .build();
        }
        return ResponseEntity.ok(response);
    }
}
