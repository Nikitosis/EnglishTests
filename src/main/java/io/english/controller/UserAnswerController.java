package io.english.controller;

import io.english.entity.request.UserAnswersRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "user-answers")
public class UserAnswerController {

    @PostMapping
    public void checkUserAnswers(@RequestBody UserAnswersRequest userAnswersRequest){
        // TODO
    }
}
