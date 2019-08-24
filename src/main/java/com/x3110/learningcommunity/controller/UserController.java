package com.x3110.learningcommunity.controller;

import com.x3110.learningcommunity.model.User;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.result.ResultCode;
import com.x3110.learningcommunity.result.ResultFactory;
import com.x3110.learningcommunity.service.UserService;
import com.x3110.learningcommunity.util.Md5SaltTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @CrossOrigin
    @RequestMapping(value="addUser", method = RequestMethod.POST)
    public int addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @CrossOrigin
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json; charset = UTF-8")
    public Result login(@RequestBody User loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String message = String.format("登录失败，详细信息[%s]", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        User user = userService.getUserByUsername(loginUser.getUsername());

        try{
            if(user == null){
                return ResultFactory.buildFailResult(ResultCode.NotExist);
            }else if(!Md5SaltTool.validPassword(loginUser.getPassword(),user.getPassword())){
                return ResultFactory.buildFailResult(ResultCode.FAIL);
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return ResultFactory.buildSuccessResult("登录成功");
    }

    @CrossOrigin
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result registr(@RequestBody User registerUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String message = String.format("注册失败，详细信息[%s]", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        User user = userService.getUserByUsername(registerUser.getUsername());

        if(user != null){
            return ResultFactory.buildFailResult(ResultCode.HaveExist);
        }else if(registerUser.getUsername().equals("") || registerUser.getPassword().equals("")){
            return ResultFactory.buildFailResult(ResultCode.FAIL);
        }

        if(!rexCheckPassword(registerUser.getPassword()))
            return ResultFactory.buildFailResult(ResultCode.INVALID_PASSWORD);

        User user1 = new User();
        String encryptedPwd = null;
        try{
            encryptedPwd = Md5SaltTool.getEncryptedPwd(registerUser.getPassword());
            user1.setUsername(registerUser.getUsername());
            user1.setPassword(encryptedPwd);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        userService.addUser(user1);
        return ResultFactory.buildSuccessResult("注册成功");
    }

    /**
     * 正则表达式验证密码
     * @param input
     * @return
     */
    public static boolean rexCheckPassword(String input) {
        // 6-20 位，字母、数字、字符
        String regStr = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）――+|{}【】‘；：”“'。，、？]){6,20}$";
        return input.matches(regStr);
    }

}
