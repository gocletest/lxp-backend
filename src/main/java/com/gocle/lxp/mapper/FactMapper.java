package com.gocle.lxp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FactMapper {

    /* ===============================
     * EVENT DAILY
     * =============================== */
    int upsertEventDaily(
        @Param("statDate") String statDate,
        @Param("clientId") Long clientId
    );

    /* ===============================
     * COURSE DAILY
     * =============================== */
    int upsertCourseDaily(
        @Param("statDate") String statDate,
        @Param("clientId") Long clientId
    );

    /* ===============================
     * USER SUMMARY
     * =============================== */
    int upsertUserSummary(
        @Param("clientId") Long clientId
    );

    /* ===============================
     * LESSON PROGRESS
     * =============================== */
    int upsertLessonProgress(
        @Param("clientId") Long clientId
    );
}
