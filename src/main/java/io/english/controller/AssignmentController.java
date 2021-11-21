package io.english.controller;

import io.english.entity.dao.Assignment;
import io.english.entity.dao.AssignmentSearchRequest;
import io.english.entity.dao.AssignmentType;
import io.english.entity.request.AssignmentRequest;
import io.english.entity.response.AssignmentResponse;
import io.english.mappers.AssignmentMapper;
import io.english.service.AssignmentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping
    public AssignmentResponse createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        var assignment = assignmentService.createAssignment(assignmentRequest);
        return AssignmentMapper.INSTANCE.toResponse(assignment);
    }

    @PutMapping("{id}")
    public AssignmentResponse updateAssignment(@RequestBody AssignmentRequest assignmentRequest, @PathVariable Long id) {
        var assignment = assignmentService.updateAssignment(assignmentRequest, id);
        return AssignmentMapper.INSTANCE.toResponse(assignment);
    }

    @DeleteMapping("{id}")
    public AssignmentResponse deleteAssignment(@PathVariable Long id) {
        var assignment = assignmentService.deleteAssignment(id);
        return AssignmentMapper.INSTANCE.toResponse(assignment);
    }

    @GetMapping
    public List<AssignmentResponse> search(@RequestParam(value = "id", required = false) Long id,
                                           @RequestParam(value = "type", required = false) AssignmentType type,
                                           @RequestParam(value = "createdById", required = false) Long createdById) {
        AssignmentSearchRequest request = new AssignmentSearchRequest()
                .setId(id)
                .setType(type)
                .setCreatedById(createdById);
        List<Assignment> assignments = assignmentService.search(request);
        return  AssignmentMapper.INSTANCE.toResponses(assignments);
    }
}
