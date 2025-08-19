package com.halggeol.backend.batch.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorBucketWriter {

    private final JdbcTemplate jdbc;
    private final ObjectMapper om = new ObjectMapper();

    public void write(String stage, Object payload, Throwable t) {
        String json = null;
        try { if (payload != null) json = om.writeValueAsString(payload); } catch (Exception ignore) {}
        String msg = (t.getMessage() != null ? t.getMessage() : t.toString());
        jdbc.update("""
      INSERT INTO batch_error_bucket(stage, payload, error_message, error_class, job_name, step_name)
      VALUES (?, ?, ?, ?, ?, ?)
    """,
            stage, json, msg, t.getClass().getName(),
            org.springframework.batch.core.scope.context.JobSynchronizationManager.getContext().getJobName(),
            org.springframework.batch.core.scope.context.StepSynchronizationManager.getContext().getStepName()
        );
    }
}
