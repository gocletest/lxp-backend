package com.gocle.lxp.mapper;

import com.gocle.lxp.dto.apikey.ApiClientCreateRequest;
import com.gocle.lxp.dto.apikey.ApiClientResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApiClientMapper {

    /** Client 목록 */
    List<ApiClientResponse> selectClientList();

    /** Client 등록 */
    int insertClient(ApiClientCreateRequest req);

    /** Client 수정 */
    int updateClient(
        @Param("clientId") Long clientId,
        @Param("req") ApiClientCreateRequest req
    );

    /** Client 상태 변경 (ACTIVE / INACTIVE) */
    int updateClientStatus(
        @Param("clientId") Long clientId,
        @Param("status") String status
    );
    
    ApiClientResponse selectClientById(Long clientId);
}
