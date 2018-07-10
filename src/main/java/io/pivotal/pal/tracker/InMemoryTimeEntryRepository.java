package io.pivotal.pal.tracker;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private long ID_COUNTER = 1;
    private Map<Long, TimeEntry> timeEntries = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(timeEntry.getId() != 0 ? timeEntry.getId() : ID_COUNTER++);
        this.timeEntries.put(timeEntry.getId(), timeEntry);
        return this.find(timeEntry.getId());
    }

    @Override
    public List<TimeEntry> list() {
        return timeEntries.values().stream().collect(Collectors.toList());
    }

    @Override
    public TimeEntry find(Long id) {
        Optional<TimeEntry> optionalTimeEntry = Optional.ofNullable(this.timeEntries.get(id));
        return optionalTimeEntry.orElse(null);
    }

    @Override
    public void delete(Long id) {
        this.timeEntries.remove(id);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        TimeEntry update = this.find(id);
        update.setDate(timeEntry.getDate());
        update.setHours(timeEntry.getHours());
        update.setProjectId(timeEntry.getProjectId());
        update.setUserId(timeEntry.getUserId());
        this.timeEntries.put(id, update);
        return this.find(id);
    }
}
