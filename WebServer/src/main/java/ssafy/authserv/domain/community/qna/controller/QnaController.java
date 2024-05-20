package ssafy.authserv.domain.community.qna.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.authserv.domain.community.qna.dto.requestdto.QnaRequestDto;
import ssafy.authserv.domain.community.qna.service.QnaService;

@Tag(name="문의", description = "이메일로 문의사항을 보낼 수 있습니다.")
@RestController
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @Operation(summary = "이메일 문의 전송")
    @PostMapping("/api/v1/qna")
    public String sendQna(@RequestBody QnaRequestDto qnaRequestDto) {
        qnaService.sendMessage(qnaRequestDto.getEmail(), qnaRequestDto.getContent());
        return "문의가 성공적으로 전송되었습니다.";
    }
}
