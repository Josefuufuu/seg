package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Incident {

    private final String description;
    private final LocalDateTime reportedAt;

    public Incident(String description) {
        this(description, LocalDateTime.now());
    }

    public Incident(String description, LocalDateTime reportedAt) {
        this.description = normalizeDescription(description);
        this.reportedAt = Objects.requireNonNull(reportedAt, "reportedAt must not be null");
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    private static String normalizeDescription(String description) {
        Objects.requireNonNull(description, "description must not be null");
        String trimmed = description.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("description must not be blank");
        }
        return trimmed;
    }

}
