package com.rocketseat.planner.controller;

import com.rocketseat.planner.dto.ParticipantRequestPayload;
import com.rocketseat.planner.model.entity.Participant;
import com.rocketseat.planner.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipants(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        return this.participantService.confirmParticipants(id, payload);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantDetail(@PathVariable UUID id) {
        return this.participantService.getParticipantDetail(id);
    }
}
