package server.mainproject.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.mainproject.exception.BusinessLogicException;
import server.mainproject.exception.ExceptionCode;
import server.mainproject.member.dto.MemberDto;
import server.mainproject.member.entity.Member;
import server.mainproject.member.mapper.MemberMapper;
import server.mainproject.member.repository.MemberRepository;
import server.mainproject.member.service.MemberService;
import server.mainproject.response.SingleResponse;
import server.mainproject.utils.URICreator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/members")
@Validated
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;
    private final MemberRepository memberRepository;


    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostDtoToMember(requestBody);

        if (memberRepository.existsByEmail(requestBody.getEmail())) {
            String errorMessage = "이메일 중복! 다른 이메일을 사용해주세요!";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }

        Member createdMember = memberService.createMember(member);

        URI uri = URICreator.createUri("/members", createdMember.getMemberId());

        return ResponseEntity.created(uri).build();
    }


    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Valid @RequestBody MemberDto.Patch requestBody,
                                      @PathVariable("member-id") Long memberId) {

//        long loginMemberId = getLoginMemberId();
//        if (loginMemberId != memberId) {
//            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
//        }

        Member member = mapper.memberPatchDtoToMember(requestBody);
        member.setMemberId(memberId);

        Member findedMember = memberService.updateMember(member);
        MemberDto.Response response = mapper.memberToMemberResponseDto(findedMember);
        response.setStatus("success");

        return new ResponseEntity<>(
                new SingleResponse<>(response),
                HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") Long memberId) {
        Member member = memberService.findMember(memberId);
        MemberDto.Response response= mapper.memberToMemberResponseDto(member);
        response.setStatus("success");
        return new ResponseEntity<>(
                new SingleResponse<>(response),
                HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity getMembers() {
        List<Member> members = memberService.findMembers();
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.membersToMemberReponseDtos(members)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMembers(@PathVariable("member-id") Long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/findingPW")
    public ResponseEntity<Void> sendingVerifyCode(@RequestParam("email") String email) {
        try {
            // 인증번호 발송
            memberService.sendVerificationCode(email);

            // 인증번호 발송 성공 시 비밀번호 재설정 페이지로 이동
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            // 인증번호 발송 실패 시 에러 응답
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/verify-code")
    public String checkingVerifyCode(@RequestParam("email") String email,
                             @RequestParam("verificationCode") String verificationCode) {
        try {
            // 인증번호 확인
            memberService.verifyCode(email, verificationCode);

            return "password-reset"; // 인증번호 검증 성공 시 비밀번호 재설정 페이지로 이동
        } catch (IllegalArgumentException e) {
            // 인증번호 검증 실패 시 에러 메시지와 함께 인증번호 확인 페이지로 이동
            return "redirect:/verification-code?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }


}

