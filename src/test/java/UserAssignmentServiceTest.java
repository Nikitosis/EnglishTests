import io.english.entity.dao.*;
import io.english.entity.request.AnswerRequest;
import io.english.entity.request.UserAnswersRequest;
import io.english.repository.UserAssignmentRepository;
import io.english.service.AssignmentItemAnswerService;
import io.english.service.AssignmentService;
import io.english.service.UserAssignmentService;
import io.english.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class UserAssignmentServiceTest {
    @Mock
    private UserAssignmentRepository userAssignmentRepository;
    @Mock
    private AssignmentService assignmentService;
    @Mock
    private UserService userService;
    @Mock
    private AssignmentItemAnswerService assignmentItemAnswerService;

    @InjectMocks
    private UserAssignmentService userAssignmentService;

    @Test
    void checkUserAnswers_customTest_success() {
        // GIVEN
        Long assignmentId = 1L;
        Long userId = 1L;
        UserAnswersRequest userAnswersRequest = new UserAnswersRequest();
        AnswerRequest answerRequest = AnswerRequest.builder()
                .assignmentId(assignmentId)
                .assignmentAnswerId(1L)
                .build();
        userAnswersRequest.setAnswers(Collections.singletonList(answerRequest));
        Assignment assignment = Assignment.builder()
                .type(AssignmentType.CUSTOM_TEST)
                .id(assignmentId)
                .build();
        User user = User.builder()
                .id(userId)
                .build();
        AssignmentItemAnswer assignmentItemAnswer = AssignmentItemAnswer.builder()
                .isCorrectAnswer(true)
                .build();
        UserAssignment userAssignment = UserAssignment.builder()
                .mark(1)
                .build();

        //WHEN
        when(assignmentService.getById(any())).thenReturn(assignment);
        when(userService.getById(any())).thenReturn(user);
        when(assignmentItemAnswerService.getById(any())).thenReturn(assignmentItemAnswer);
        when(userAssignmentRepository.save(any())).thenReturn(userAssignment);
        when(userAssignmentRepository.findByAssignmentAndUser(any(), any())).thenReturn(Optional.ofNullable(userAssignment));
        //THEN
        UserAssignment actualUserAssignment = userAssignmentService.checkUserAnswers(userAnswersRequest, assignmentId, userId);
        Assertions.assertEquals(actualUserAssignment, userAssignment);
    }

    @Test
    void checkUserAnswers_defaultKnowledgeTest_success() {
        // GIVEN
        Long assignmentId = 1L;
        Long userId = 1L;
        UserAnswersRequest userAnswersRequest = new UserAnswersRequest();
        AnswerRequest answerRequest = AnswerRequest.builder()
                .assignmentId(assignmentId)
                .assignmentAnswerId(1L)
                .build();
        userAnswersRequest.setAnswers(Collections.singletonList(answerRequest));
        AssignmentItem assignmentItem = new AssignmentItem();
        Assignment assignment = Assignment.builder()
                .type(AssignmentType.DEFAULT_KNOWLEDGE_TEST)
                .assignmentItems(Collections.singletonList(assignmentItem))
                .id(assignmentId)
                .build();
        User user = User.builder()
                .id(userId)
                .build();
        AssignmentItemAnswer assignmentItemAnswer = AssignmentItemAnswer.builder()
                .isCorrectAnswer(true)
                .build();
        UserAssignment userAssignment = UserAssignment.builder()
                .mark(1)
                .build();

        //WHEN
        when(assignmentService.getById(any())).thenReturn(assignment);
        when(userService.getById(any())).thenReturn(user);
        when(assignmentItemAnswerService.getById(any())).thenReturn(assignmentItemAnswer);
        when(userAssignmentRepository.save(any())).thenReturn(userAssignment);
        when(userAssignmentRepository.findByAssignmentAndUser(any(), any())).thenReturn(Optional.ofNullable(userAssignment));
        //THEN
        UserAssignment actualUserAssignment = userAssignmentService.checkUserAnswers(userAnswersRequest, assignmentId, userId);
        Assertions.assertEquals(actualUserAssignment, userAssignment);
        verify(userService).assignEnglishLevel(user, EnglishLevel.C2);
    }

    @Test
    void checkUserAnswers_answerRequestNull_exception() {
        // GIVEN
        Long assignmentId = 1L;
        Long userId = 1L;
        UserAnswersRequest userAnswersRequest = new UserAnswersRequest();
        AnswerRequest answerRequest = null;
        userAnswersRequest.setAnswers(Collections.singletonList(answerRequest));
        Assignment assignment = Assignment.builder()
                .type(AssignmentType.CUSTOM_TEST)
                .id(assignmentId)
                .build();
        User user = User.builder()
                .id(userId)
                .build();
        AssignmentItemAnswer assignmentItemAnswer = AssignmentItemAnswer.builder()
                .isCorrectAnswer(true)
                .build();
        UserAssignment userAssignment = UserAssignment.builder()
                .mark(1)
                .build();

        //WHEN
        when(assignmentService.getById(any())).thenReturn(assignment);
        when(userService.getById(any())).thenReturn(user);
        when(assignmentItemAnswerService.getById(any())).thenReturn(assignmentItemAnswer);
        when(userAssignmentRepository.save(any())).thenReturn(userAssignment);
        when(userAssignmentRepository.findByAssignmentAndUser(any(), any())).thenReturn(Optional.ofNullable(userAssignment));
        //THEN
        Assertions.assertThrows(NullPointerException.class, () -> userAssignmentService.checkUserAnswers(userAnswersRequest, assignmentId, userId));
    }

    @Test
    void checkUserAnswers_assignmentId_exception() {
        // GIVEN
        Long assignmentId = null;
        Long userId = 1L;
        UserAnswersRequest userAnswersRequest = new UserAnswersRequest();
        AnswerRequest answerRequest = null;
        userAnswersRequest.setAnswers(Collections.singletonList(answerRequest));
        Assignment assignment = Assignment.builder()
                .type(AssignmentType.CUSTOM_TEST)
                .id(assignmentId)
                .build();
        User user = User.builder()
                .id(userId)
                .build();
        AssignmentItemAnswer assignmentItemAnswer = AssignmentItemAnswer.builder()
                .isCorrectAnswer(true)
                .build();
        UserAssignment userAssignment = UserAssignment.builder()
                .mark(1)
                .build();

        //WHEN
        when(assignmentService.getById(any())).thenReturn(assignment);
        when(userService.getById(any())).thenReturn(user);
        when(assignmentItemAnswerService.getById(any())).thenReturn(assignmentItemAnswer);
        when(userAssignmentRepository.save(any())).thenReturn(userAssignment);
        when(userAssignmentRepository.findByAssignmentAndUser(any(), any())).thenReturn(Optional.ofNullable(userAssignment));
        //THEN
        Assertions.assertThrows(NullPointerException.class, () -> userAssignmentService.checkUserAnswers(userAnswersRequest, assignmentId, userId));
    }

    @Test
    void checkUserAnswers_userId_exception() {
        // GIVEN
        Long assignmentId = 1L;
        Long userId = null;
        UserAnswersRequest userAnswersRequest = new UserAnswersRequest();
        AnswerRequest answerRequest = null;
        userAnswersRequest.setAnswers(Collections.singletonList(answerRequest));
        Assignment assignment = Assignment.builder()
                .type(AssignmentType.CUSTOM_TEST)
                .id(assignmentId)
                .build();
        User user = User.builder()
                .id(userId)
                .build();
        AssignmentItemAnswer assignmentItemAnswer = AssignmentItemAnswer.builder()
                .isCorrectAnswer(true)
                .build();
        UserAssignment userAssignment = UserAssignment.builder()
                .mark(1)
                .build();

        //WHEN
        when(assignmentService.getById(any())).thenReturn(assignment);
        when(userService.getById(any())).thenReturn(user);
        when(assignmentItemAnswerService.getById(any())).thenReturn(assignmentItemAnswer);
        when(userAssignmentRepository.save(any())).thenReturn(userAssignment);
        when(userAssignmentRepository.findByAssignmentAndUser(any(), any())).thenReturn(Optional.ofNullable(userAssignment));
        //THEN
        Assertions.assertThrows(NullPointerException.class, () -> userAssignmentService.checkUserAnswers(userAnswersRequest, assignmentId, userId));
    }
}
