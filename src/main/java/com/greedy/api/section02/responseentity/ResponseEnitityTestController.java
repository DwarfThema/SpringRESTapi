package com.greedy.api.section02.responseentity;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entity")
public class ResponseEnitityTestController {

    /*ResponseEntity
    * 결과 데이터와 http 상태 코드, http 응답 헤더를 직접 제어할 수 있는 클래스이다.
    * */

    private List<UserDTO> users;

    public ResponseEnitityTestController(){
        users = new ArrayList<>();

        users.add(new UserDTO(1, "준호","123","박준호", new java.util.Date()));
        users.add(new UserDTO(2, "대연","123","박대연", new java.util.Date()));
        users.add(new UserDTO(3, "지현","123","박지현", new java.util.Date()));
        users.add(new UserDTO(4, "재휘","123","박재휘", new java.util.Date()));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUser(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);

        //ResponseMessage responseMessage = new ResponseMessage(200, "조회성공!", responseMap);

        /*빌더를 이용한 방식*/
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200,"조회성공",responseMap));

        /*new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
        //ok 면 200번을 보냄*/
    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);

        Map<String, Object> responsMap = new HashMap<>();
        responsMap.put("user", foundUser);

        /*빌더를 이용한 방식*/
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "조회성공", responsMap));

    }

    @PostMapping("/users")
    public ResponseEntity<?> registUser(@RequestBody UserDTO newUser){

        System.out.println(newUser);

        int lastUserNo = users.get(users.size() -1 ).getNo();
        newUser.setNo(lastUserNo + 1);

        users.add(newUser);

        return ResponseEntity
                .created(URI.create("/entity/users/" + users.get(users.size() -1 ).getNo()))
        //create는 201번이다. create는 주소값을 arg로 받는다.
                .build();

    }

    @PutMapping("/users/{userNo}")
    //put은 업데이트(모디파이)이다.
    //patch 도 업데이트이다. 하지만 put은 여러개를 업데이트, patch는 일부분을 업데이트
    public ResponseEntity<?> modifyUser(@RequestBody UserDTO modifiyInfo, @PathVariable int userNo){

            UserDTO foundUser =
                    users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);
            foundUser.setId(modifiyInfo.getId());
            foundUser.setPwd(modifiyInfo.getPwd());
            foundUser.setName(modifiyInfo.getName());

            return ResponseEntity
                    .created(URI.create("/entity/users/" + userNo))
                    .build();
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){

        UserDTO foundUser =
                users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);
        users.remove(foundUser);

        return ResponseEntity
                .noContent()
                //204번 응답
                .build();
    }

}
