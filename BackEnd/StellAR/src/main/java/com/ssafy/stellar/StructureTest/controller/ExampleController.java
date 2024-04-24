package com.ssafy.stellar.StructureTest.controller;

// Java
import com.ssafy.stellar.StructureTest.service.TestService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@Slf4j
@RequestMapping("/")
public class ExampleController {

    private final TestService testService;

    public ExampleController(TestService testService) {
        this.testService = testService;
    }

    @Operation(summary = "문자열 반복", description = "파라미터로 받은 문자열을 2번 반복합니다.")
    @Parameter(name = "str", description = "2번 반복할 문자열")
    @GetMapping("/returnStr")
    public String returnStr(@RequestParam String str) {
        return str + "\n" + str;
    }

    @GetMapping("/example")
    public String example() {
        return "예시 API";
    }

    @Hidden
    @GetMapping("/ignore")
    public String ignore() {
        return "무시되는 API";
    }

    @Operation(summary = "사용자 추가")
    @PostMapping("/plus")
    public ResponseEntity<?> plusUser(@RequestParam Long id, @RequestParam String name, @RequestParam String gender) {
        try {
            testService.plusUser(id, name, gender);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}