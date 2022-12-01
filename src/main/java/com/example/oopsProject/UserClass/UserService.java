package com.example.oopsProject.UserClass;

import com.example.oopsProject.Cart.Cart;
import com.example.oopsProject.Ewallet.EWalletRepository;
import com.example.oopsProject.Ewallet.Ewallet;
import com.example.oopsProject.OutputClasses.UserOutput;
import com.example.oopsProject.Security.passwordencoder;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final passwordencoder encoder;
    private final UserRepository userRepository;
    private final EWalletRepository eWalletRepository;
@Autowired
    public UserService(UserRepository userRepository,EWalletRepository eWalletRepository,passwordencoder encoder) {
        this.eWalletRepository = eWalletRepository;
        this.userRepository = userRepository;
        this.encoder =  encoder;
    }

    public List<UserOutput> getUsers() {
        List<UserOutput> userOutputs = new ArrayList<>();
        for(UserClass user:userRepository.findAll()){
            UserOutput userOutput = new UserOutput(user.getName(),user.getEmail(),user.getPhoneNo(),user.getEwallet(),user.getRole(),user.getAddress());
            userOutputs.add(userOutput);
        }
        return userOutputs;
    }

    public UserOutput getUser(long id){
    UserClass user = userRepository.findById(id).get();
        UserOutput userOutput = new UserOutput(user.getName(),user.getEmail(),user.getPhoneNo(),user.getEwallet(),user.getRole(),user.getAddress());

    return userOutput;
    }
    public String addUser(addUserClass userClass) {
        UserClass user = new UserClass(userClass.getName(),userClass.getEmail(),userClass.getPhoneNo(),userClass.getAddress(),userClass.getRole(),encoder.encode(userClass.getPassword()));
        Ewallet ewallet = new Ewallet(user,userClass.getBalance());
        user.setEwallet(ewallet);
        userRepository.save(user);
        eWalletRepository.save(ewallet);
        return "SUCCESS";

    }

    public UserClass findByEmail(String email){
    return userRepository.findByEmail(email).get();
    }

    public void deleteUser(long id) {
    userRepository.deleteById(id);
    }

    public List<Optional<UserClass>> getUserByName(String name) {
    return userRepository.findByName(name);
    }

    public Optional<UserClass> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
    }

    public UserClass updateUser(long id, String name) {
    UserClass userClass = userRepository.findById(id).get();
    userClass.setName(name);
    return userRepository.save(userClass);
    }
    public String changePwd(long id,String newPwd){
        UserClass userClass = userRepository.findById(id).get();
        if(userClass != null){
            userClass.setPassword(passwordencoder.encode(newPwd));
        }
        userRepository.save(userClass);
        return "Password changed";
    }

    public String changePwd(long id,String oldPwd,String newPwd){
        UserClass userClass = userRepository.findById(id).get();
        if(userClass.getPassword().equals(passwordencoder.encode(oldPwd)) && userClass != null){
            userClass.setPassword(passwordencoder.encode(newPwd));
            userRepository.save(userClass);
            return "Password changed";
        }
        return "Error occured";
    }

    public UserClass findById(long userid) {
        return userRepository.findById(userid).get();
    }

    public String login(String email,String password) {
        UserClass user = userRepository.findByEmail(email).get();
        if(user.getPassword().equals(encoder.encode(password)) && user!=null){
            System.out.println("OK");
            return String.valueOf(user.getId());

        }
        else{
            System.out.println("NOT OK");
            return "null";
        }
    }

    public ResponseEntity<?> removeUser(long senderid,long userid) {
        if(userRepository.findById(senderid).get().getRole().equals(Role.ADMIN)){
            userRepository.deleteById(userid);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
        else return new ResponseEntity<>("BADREQUEST", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> modifyUser(addUserClass userClass) {
        UserClass user = userRepository.findById(userClass.getId()).get();
        if (user != null) {
            user.setPhoneNo(userClass.getPhoneNo());
            user.setAddress(userClass.getAddress());
            user.setName(userClass.getName());
            userRepository.save(user);
            return new ResponseEntity<>("SUCCESSFULLY MODIFIED USER", HttpStatus.OK);
        } else return new ResponseEntity<>("FAILED TO MODIFY", HttpStatus.BAD_REQUEST);

    }
}
