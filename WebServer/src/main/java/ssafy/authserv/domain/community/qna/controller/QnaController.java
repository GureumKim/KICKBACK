package ssafy.authserv.domain.community.qna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.authserv.domain.community.qna.dto.requestdto.QnaRequestDto;
import ssafy.authserv.domain.community.qna.service.QnaService;

@RestController
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @PostMapping("/api/v1/qna")
    public String sendQna(@RequestBody QnaRequestDto qnaRequestDto) {
        qnaService.sendMessage(qnaRequestDto.getEmail(), qnaRequestDto.getContent());
        return "문의가 성공적으로 전송되었습니다.";
    }
}
