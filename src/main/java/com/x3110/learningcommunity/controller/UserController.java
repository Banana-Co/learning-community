package com.x3110.learningcommunity.controller;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.UserRepository;
import com.x3110.learningcommunity.model.Vo.ChangeAvatarVo;
import com.x3110.learningcommunity.model.Vo.ChangePswdVo;
import com.x3110.learningcommunity.model.User;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.result.ResultCode;
import com.x3110.learningcommunity.result.ResultFactory;
import com.x3110.learningcommunity.service.MailService;
import com.x3110.learningcommunity.service.UserService;
import com.x3110.learningcommunity.util.Md5SaltTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
//@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    Autowired
    UserRepository userRepository;

//    @CrossOrigin
//    @RequestMapping(value="addUser", method = RequestMethod.POST)
//    public int addUser(@RequestBody User user) {
//        return userService.addUser(user);
//    }

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

    @RequestMapping(value = "sendPin", method = RequestMethod.GET)
    public Result sendPin(@RequestParam String emailAddress){
        List<User> users = userRepository.getByEmailAddress(emailAddress);
        if(users != null)return ResultFactory.buildFailResult(ResultCode.EMAILOCCUPIED);
        return mailService.sendPin(emailAddress);
    }


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
            user1.setEmailAddress(registerUser.getEmailAddress());
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

    @RequestMapping(value = "changepswd", method = RequestMethod.POST)
    public Result changePassword(@RequestBody ChangePswdVo changePswdVo){
        User user = userService.getUserByUsername(changePswdVo.getUsername());
        String encryptedPwd = null;
        try{
            if(!Md5SaltTool.validPassword(changePswdVo.getOldPswd(),user.getPassword())){
                return ResultFactory.buildFailResult(ResultCode.FAIL);
            }
            if(!rexCheckPassword(changePswdVo.getNewPswd())){
                return  ResultFactory.buildFailResult(ResultCode.INVALID_PASSWORD);
            }
            encryptedPwd = Md5SaltTool.getEncryptedPwd(changePswdVo.getNewPswd());
            user.setPassword(encryptedPwd);
            userService.changePswd(user);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return ResultFactory.buildSuccessResult("修改密码成功");
    }
    /**
     *向前端返回注册时间
     * @return String
     */
    @RequestMapping(value = "time/{username}", method = RequestMethod.GET)
    public String getTime(@PathVariable (value = "username")String username) {
        return userService.getUserByUsername(username).getCreatedDate().toString();
    }

    @RequestMapping(value = "/getUser/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable (value = "username") String username){
        return userService.getUserByUsername(username);
    }

    @RequestMapping(value = "uploadAvatar", method = RequestMethod.POST)
    public Result uploadAvater(@RequestBody ChangeAvatarVo changeAvaterVo){
        User user = userService.getUserByUsername(changeAvaterVo.getUsername());
        if(user == null) return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        user.setAvatarUrl(changeAvaterVo.getAvatarUrl());
        userService.uploadAvater(user);
        return ResultFactory.buildSuccessResult("头像上传成功");
    }
//    @RequestMapping(value = "time", method = RequestMethod.POST)
//    public String getTime(@RequestBody String username) {
//        return userService.getUserByUsername(username).getCreatedDate().toString();
//    }

    @RequestMapping(value = "deleteUser/username={username}", method = RequestMethod.POST)
    public DeleteResult deleteUser(@PathVariable (value = "username") String username){
        System.out.println(userService.getUserByUsername(username).getId());
        return userService.deleteUser(username);
    }

    @RequestMapping(value = "readNotifi", method = RequestMethod.GET)
    public Result readNotification(@RequestParam String username, @RequestParam int notiNo){
        return userService.readNotification(username, notiNo);
    }
}
