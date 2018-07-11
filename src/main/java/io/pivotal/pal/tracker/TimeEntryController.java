package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
@ResponseBody
public class TimeEntryController {

    private final CounterService counter;
    private final GaugeService gauge;

    private TimeEntryRepository timeEntryRepository;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService gauge) {
        this.timeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry){
        TimeEntry createdTimeEntry = this.timeEntryRepository.create(timeEntry);

        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTimeEntry);
    }

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id){
        TimeEntry timeEntry = this.timeEntryRepository.find(id);

        if (timeEntry == null) {
            return ResponseEntity.notFound().build();
        } else {
            counter.increment("TimeEntry.read");
            return ResponseEntity.ok(timeEntry);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry timeEntryUpdated = this.timeEntryRepository.update(id, timeEntry);
        if(timeEntryUpdated == null) {
            return ResponseEntity.notFound().build();
        } else {
            counter.increment("TimeEntry.updated");
            return ResponseEntity.ok(timeEntryUpdated);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        this.timeEntryRepository.delete(id);

        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");

        return ResponseEntity.ok(this.timeEntryRepository.list());
    }
}
