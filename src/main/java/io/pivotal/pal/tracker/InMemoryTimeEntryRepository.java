package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long,TimeEntry> timeEntryMap = new HashMap<>();

    public TimeEntry create(TimeEntry inTimeEntry) {
        TimeEntry timeEntryToCreate = inTimeEntry;
        if (!hasIdDefined(timeEntryToCreate)){
            timeEntryToCreate = new TimeEntry(new Long(timeEntryMap.size()+1),inTimeEntry);
        }
        timeEntryMap.put(timeEntryToCreate.getId(),timeEntryToCreate);
        return find(timeEntryToCreate.getId());
    }

    private boolean hasIdDefined(TimeEntry timeEntry){
        return timeEntry.getId() != 0L;
    }

    public TimeEntry find(long id){
        return timeEntryMap.get(id);
    }

    public List<TimeEntry> list(){
        return new ArrayList(timeEntryMap.values());
    }

    public TimeEntry update(long id, TimeEntry timeEntryToUpdate){
        if(timeEntryMap.containsKey(id)) {
            timeEntryMap.put(id, new TimeEntry(id, timeEntryToUpdate.getProjectId(), timeEntryToUpdate.getUserId(), timeEntryToUpdate.getDate(), timeEntryToUpdate.getHours()));
            return find(id);
        }else{
            return null;
        }
    }

    public TimeEntry delete(long id){
        return timeEntryMap.remove(id);
    }
}
