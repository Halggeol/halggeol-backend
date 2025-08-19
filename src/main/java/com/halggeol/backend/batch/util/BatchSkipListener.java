package com.halggeol.backend.batch.util;

import com.halggeol.backend.domain.UserActionLog;
import com.halggeol.backend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchSkipListener implements org.springframework.batch.core.SkipListener<UserActionLog, User>{

    private final ErrorBucketWriter errorWriter;

    @Override public void onSkipInRead(Throwable t) {
        errorWriter.write("READ", null, t);
    }
    @Override public void onSkipInProcess(UserActionLog item, Throwable t) {
        errorWriter.write("PROCESS", item, t);
    }
    @Override public void onSkipInWrite(User item, Throwable t) {
        errorWriter.write("WRITE", item, t);
    }
}
