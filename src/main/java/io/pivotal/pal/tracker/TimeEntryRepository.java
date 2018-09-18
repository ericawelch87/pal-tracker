package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry timeEntryToCreate);

    public TimeEntry find(long l);

    public List<TimeEntry> list();

    public TimeEntry update(long l, TimeEntry expected);

    public TimeEntry delete(long l);
}

