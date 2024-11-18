package com.se.system.service.dto;

import com.se.annotation.Query;
import lombok.Data;

import java.util.List;

/**
 * @author wyr
 * @date 2020-05-12
 */
@Data
public class SeQueryCriteria {

    // 模糊
    @Query(type = Query.Type.UNIX_TIMESTAMP)
    private List<String> createTime;
    @Query
    private Double id;
    @Query
    private Double md;
    @Query
    private Double qd;
    @Query
    private Double kd;
    @Query
    private Double txml;
    @Query
    private Double bsb;
    @Query
    private Double kjin;
    @Query
    private Double kju;
    @Query
    private Double zydh;
    @Query
    private Double dkl;
    @Query
    private Integer type;
    @Query
    private Integer status;
    @Query
    private Integer isDel;

}
