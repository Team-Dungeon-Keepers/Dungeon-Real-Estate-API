package com.revature.dungeonsite.controllers;

import com.revature.dungeonsite.exceptions.ResourceNotFoundException;
import com.revature.dungeonsite.models.Schedule;
import com.revature.dungeonsite.repositories.ScheduleRepository;
import com.revature.dungeonsite.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//possibly change mapping later?
@RestController
@CrossOrigin
@RequestMapping("/api/schedules")
public class ScheduleController {
    private ScheduleRepository schedules;
	
    public ScheduleController(ScheduleRepository schedules) {
        this.schedules = schedules;
    }
	
    private ResponseEntity<Schedule> getScheduleByScheduleID(Long scheduleID) throws ResourceNotFoundException {
        return schedules.findById(scheduleID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Schedule not found for ID: " + scheduleID)
                );
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> findAll() {
        return this.schedules.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleByID(@PathVariable(value="id") Long scheduleID)
            throws ResourceNotFoundException {
        Schedule schedule = getScheduleByScheduleID(scheduleID);
        return ResponseEntity.ok().body(schedule);
    }

    private Schedule getNeoSchedule(@PathVariable("id") Long scheduleID) throws ResourceNotFoundException {
        return schedules.findById(schedulesID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Schedule not found for ID: " + schedulesID)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable(value = "id") Long scheduleID,
        @RequestBody Schedule schedule) throws ResourceNotFoundException {
        Schedule neoSchedule = getNeoSchedule(scheduleID);
        if (schedule.getStartTime() != null)
            neoSchedule.setStartTime(schedule.getStartTime());
        if (schedule.getEndTime() != null)
            neoSchedule.setEndTime(schedule.getEndTime());
        if (schedule.getStartDate() != null)
            neoSchedule.setStartDate(schedule.getStartDate());
        if (schedule.getEndDate() != null)
            neoSchedule.setEndDate(schedule.getEndDate());
        if (schedule.getSetting() != null)
            neoSchedule.setSetting(schedule.getSetting());
        return ResponseEntity.ok(this.schedules.save(neoSchedule));
    }

    @PostMapping
    public Schedule makeSchedule(@RequestBody Schedule neoSchedule) {
		neoSchedule.setScheduleID(KeyUtils.nextKey());
        return this.schedules.save(neoSchedule);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteSchedule(@PathVariable(value = "id") Long scheduleID)
            throws ResourceNotFoundException {
        Schedule oldSchedule = getScheduleByScheduleID(scheduleID);
        this.schedules.delete(oldSchedule);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
