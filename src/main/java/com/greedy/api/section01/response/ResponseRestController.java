package com.greedy.api.section01.response;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//컨트롤러 인데 ResponseBody가 같이 묶여있음. 결국 응답바디에 데이터를 전송해줄 수 있음
@RequestMapping("/response")
public class ResponseRestController {

    /*1. 문자열 응답*/
    @GetMapping("/hello")
    public String helloworld(){

        return "hello world!";
    }

    /*2. 기본 자료형 응답*/
    @GetMapping("/random")
    public  int getRandomNumber(){

        return  (int) (Math.random() * 10) +1;
    }

    /*3. Object 응답*/
    @GetMapping("/message")
    public Message getMessage(){

        return new Message(200, "메세지를 응답합니다.");
    }

    /*4. List 응답*/
    @GetMapping("/list")
    public List<String> getList(){

        return List.of(new String[] {"사과","바나나","복숭아"});
    }

    /*5. Map 응답*/
    @GetMapping("/map")
    public Map<Integer, String> getMap(){

        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(200,"정상응답"));
        messageList.add(new Message(404,"비 정상응답"));
        messageList.add(new Message(500,"개발자의 잘못입니다."));

        return messageList.stream().collect(Collectors.toMap(Message::getHttpStatusCode, Message::getMessage));
        //stream 은 map 과 동일
    }

    /*6. ImageFile 응답*/
    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {

        InputStream stream = new FileInputStream(new File("Users\\junhopark\\Desktop\\_dev\\spring\\04_spring-boot\\workspace\\chap04-boot-rest-api-lecture-source\\src\\main\\java\\com\\greedy\\api\\section01\\response\\sample.png"));
        //경로는 C드라이브 부터 시작해야함.

        return stream.readAllBytes();
    }

    /*7. ResponseEntitiy를 이용한 응답.*/
    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity(){

        return ResponseEntity.ok(new Message(123,"hello world"));
        //ok는 200을 응답함
    }


}
