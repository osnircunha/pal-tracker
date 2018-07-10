package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
@ResponseBody
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.timeEntryRepository.create(timeEntry));
    }

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable Long id){
        TimeEntry timeEntry = this.timeEntryRepository.find(id);
        return timeEntry == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(timeEntry);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry timeEntryUpdated = this.timeEntryRepository.update(id, timeEntry);
        return timeEntryUpdated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(timeEntryUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        this.timeEntryRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok(this.timeEntryRepository.list());
    }
}
