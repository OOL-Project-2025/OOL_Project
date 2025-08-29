package com.OOL.oolfinance.controller.oAuth2;

import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.enums.StatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : OAuth2RestController
 * @date : 2025. 8. 6. / 오후 9:25
 * @modifyed :
 **/

@Slf4j
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class OAuth2RestController {
}
