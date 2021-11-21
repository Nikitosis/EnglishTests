package io.english.controller;

import io.english.entity.dao.Assignment;
import io.english.entity.request.AssignmentRequest;
import io.english.entity.response.AssignmentResponse;
import io.english.mappers.AssignmentMapper;
import io.english.service.AssignmentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping
    public AssignmentResponse createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        Assignment assignment = assignmentService.createAssignment(assignmentRequest);
        return AssignmentMapper.INSTANCE.toResponse(assignment);
    }

    @PutMapping("{id}")
    public AssignmentResponse updateAssignment(@RequestBody AssignmentRequest assignmentRequest, @PathVariable Long id) {
        Assignment assignment = assignmentService.updateAssignment(assignmentRequest, id);
        return AssignmentMapper.INSTANCE.toResponse(assignment);
    }

    @DeleteMapping("{id}")
    public AssignmentResponse deleteAssignment(@PathVariable Long id) {
        Assignment assignment = assignmentService.deleteAssignment(id);
        return AssignmentMapper.INSTANCE.toResponse(assignment);
    }
}
