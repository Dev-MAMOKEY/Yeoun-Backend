package com.mamokey.yeoun.domain.internal.service;

import com.mamokey.yeoun.domain.internal.dto.InternalHealthResponse;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

@Service
public class InternalHealthService {

    private static final String VERSION = "0.1.0";
    private static final String MODEL_NOT_LOADED = "not_loaded";
    private static final String DB_OK = "ok";
    private static final String DB_ERROR = "error";

    private final DataSource dataSource;
    private final Instant startedAt;

    public InternalHealthService(DataSource dataSource) {
        this.dataSource = dataSource;
        this.startedAt = Instant.now();
    }

    public InternalHealthResponse getHealth() {
        String dbStatus = checkDbStatus();

        return new InternalHealthResponse(
                DB_OK.equals(dbStatus) ? "ok" : "degraded",
                VERSION,
                Duration.between(startedAt, Instant.now()).toSeconds(),
                new InternalHealthResponse.ModelStatus(
                        MODEL_NOT_LOADED,
                        MODEL_NOT_LOADED,
                        MODEL_NOT_LOADED
                ),
                0,
                true,
                dbStatus
        );
    }

    private String checkDbStatus() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(1) ? DB_OK : DB_ERROR;
        } catch (SQLException e) {
            return DB_ERROR;
        }
    }
}
