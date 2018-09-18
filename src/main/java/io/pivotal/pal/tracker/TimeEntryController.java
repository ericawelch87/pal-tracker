package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry created = timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry foundTimeEntry = timeEntryRepository.find(id);
        return new ResponseEntity<>(foundTimeEntry,notFoundIfNullOtherwiseOK(foundTimeEntry));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntryToUpdate) {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(id,timeEntryToUpdate);
        return new ResponseEntity<>(updatedTimeEntry,notFoundIfNullOtherwiseOK(updatedTimeEntry));
    }

    private HttpStatus notFoundIfNullOtherwiseOK(TimeEntry updatedTimeEntry) {
        return updatedTimeEntry == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        return new ResponseEntity<>(timeEntryRepository.delete(id), HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntryRepository.list(),HttpStatus.OK);
    }
}
