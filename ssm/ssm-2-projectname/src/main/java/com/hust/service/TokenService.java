package com.hust.service;


import com.hust.entity.dto.TokenDataDto;
import com.hust.util.TDRequest;
import com.hust.util.TDResponse;

/**
 * @Auther: TX
 * @Description: Token操作服务
 */
public interface TokenService {
    /**
     * Token验证
     * @param requestInfo
     * @return
     */
    TDResponse<TokenDataDto> isTokenValid(TDRequest requestInfo);

    /**
     * Token续签验证
     * @param requestInfo
     * @return
     */
    TDResponse<TokenDataDto> isAllowTokenRenew(TDRequest requestInfo);

    /**
     * 解析Token数据，不进行签名和过期校验
     * @param requestInfo
     * @return
     */
    TDResponse<TokenDataDto> getTokenDataDto(TDRequest requestInfo);
}
