package com.example.oopsProject.UserClass;

import com.example.oopsProject.Ewallet.EWalletRepository;
import com.example.oopsProject.Ewallet.Ewallet;
import com.example.oopsProject.Mail.EmailDetails;
import com.example.oopsProject.Mail.EmailService;
import com.example.oopsProject.OutputClasses.UserOutput;
import com.example.oopsProject.Security.passwordencoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends EmailService {

    private final passwordencoder encoder;
    private final UserRepository userRepository;
    private final EWalletRepository eWalletRepository;
@Autowired
    public UserService(UserRepository userRepository,EWalletRepository eWalletRepository,passwordencoder encoder) {
        this.eWalletRepository = eWalletRepository;
        this.userRepository = userRepository;
        this.encoder =  encoder;
    }

    public List<UserOutput> getUsers(long id) {
        UserClass getuser = userRepository.findById(id).get();
        if(getuser.getRole().equals(Role.ADMIN) && getuser.isLoggedin()){
        List<UserOutput> userOutputs = new ArrayList<>();
        for(UserClass user:userRepository.findAll()){
            UserOutput userOutput = new UserOutput(user.getName(),user.getEmail(),user.getPhoneNo(),user.getEwallet(),user.getRole(),user.getAddress());
            userOutputs.add(userOutput);
        }
            return userOutputs;
        }
        else throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized User");
    }

    public UserOutput getUser(long id){
    UserClass user = userRepository.findById(id).get();
        UserOutput userOutput = new UserOutput(user.getName(),user.getEmail(),user.getPhoneNo(),user.getEwallet(),user.getRole(),user.getAddress());

    return userOutput;
    }
    public addUserClass addUser(addUserClass userClass) {

        UserClass user = new UserClass(userClass.getName(),userClass.getEmail(),userClass.getPhoneNo(),userClass.getAddress(),userClass.getRole(),encoder.encode(userClass.getPassword()));
        if(userClass.getRole().equals(Role.MANAGER)) user.setApproved(false);
        Ewallet ewallet = new Ewallet(user,userClass.getBalance());
        user.setEwallet(ewallet);
        userRepository.save(user);
        eWalletRepository.save(ewallet);
        return new addUserClass(user);

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

    public addUserClass login(String email,String password) {
        Optional<UserClass> user = userRepository.findByEmail(email);
        if(user.isPresent()==false) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Account does not exist! Please create an account! ");
        else if(user.get().getRole().equals(Role.MANAGER) && user.get().isApproved()==false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Application has not been approved!");
        }
        else if(user.get().getPassword().equals(encoder.encode(password)) && user!=null){
            user.get().setLoggedin(true);
            userRepository.save(user.get());
            System.out.println("OK");
            return new addUserClass(user.get());

        }
        else{
            System.out.println("NOT OK");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User Does not exist");
        }
    }

    public ResponseEntity<?> removeUser(long senderid,String email) {
        UserClass sender = userRepository.findById(senderid).get();
        UserClass toDelete = userRepository.findByEmail(email).get();
        if(sender.getRole().equals(Role.ADMIN) && sender.isLoggedin()){
            if(toDelete.getRole().equals(Role.MANAGER) && toDelete.isApproved()==false){
                sendSimpleMail(new EmailDetails(email,"Dear User,\n Your bargains application for manager has been rejected. Sorry! ","Application to Bargains"));
                userRepository.deleteByEmail(email);
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            }
            else{
                sendSimpleMail(new EmailDetails(email,"Dear User,\n Your bargains account has been removed by the admin. ","Bargains Account Removal"));
            userRepository.deleteByEmail(email);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);}
        }
        else return new ResponseEntity<>("BADREQUEST", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> modifyUser(addUserClass userClass) {
        UserClass user = userRepository.findById(userClass.getId()).get();
        System.out.println(user.getName());
        System.out.println(user.isLoggedin());
        if (user.isLoggedin()) {
            user.setPhoneNo(userClass.getPhoneNo());
            user.setAddress(userClass.getAddress());
            user.setName(userClass.getName());
            userRepository.save(user);
            return new ResponseEntity<>("SUCCESSFULLY MODIFIED USER", HttpStatus.OK);
        } else return new ResponseEntity<>("FAILED TO MODIFY", HttpStatus.BAD_REQUEST);

    }

    @Transactional
    public List<UserOutput> approveManagers(long requesterid) {
        UserClass admin = userRepository.findById(requesterid).get();
        if((admin!=null && admin.getRole().equals(Role.ADMIN))&& admin.isLoggedin()){
        List<Optional<UserClass>> users = userRepository.findByApprovedAndRole(false,Role.MANAGER);
        List<UserOutput> userOutputs = new ArrayList<>();
        for(Optional<UserClass> userClass : users){
            UserClass user = userClass.get();
            userOutputs.add(new UserOutput(user));
        }
        return userOutputs;
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized Access!!");

    }

    public ResponseEntity<?> approveManager(long id,long requesterid) {
        UserClass admin = userRepository.findById(requesterid).get();
        if(admin.isLoggedin() && admin.getRole().equals(Role.ADMIN)){
        UserClass userClass = userRepository.findById(id).get();
        if(userClass.isApproved()==false && userClass.getRole().equals(Role.MANAGER)){
        userClass.setApproved(true);
        userRepository.save(userClass);
        sendSimpleMail(new EmailDetails(userClass.getEmail(),"Dear "+
                userClass.getName()+
                ",\n Your request has been approved! ","Approval in Bargains"));
        return new ResponseEntity<>("SUCCESSFULLY APPROVED",HttpStatus.OK);}
        else return new ResponseEntity<>("Error while trying to approve",HttpStatus.BAD_REQUEST);}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Admin privelages not accessible");
}
    @Transactional
    public List<UserOutput> approvedManagers(long id) {
        UserClass admin = userRepository.findById(id).get();
        if(admin.isLoggedin() && admin.getRole().equals(Role.ADMIN)){
        List<Optional<UserClass>> users = userRepository.findByApprovedAndRole(true,Role.MANAGER);
        List<UserOutput> userOutputs = new ArrayList<>();
        for(Optional<UserClass> userClass : users){
            UserClass user = userClass.get();
            userOutputs.add(new UserOutput(user));
        }
        return userOutputs;}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad request");
    }

    public UserOutput adminGetUser(long id, long requesterId) {
        UserClass sender = userRepository.findById(requesterId).get();
        if(sender.getRole().equals(Role.ADMIN) && sender.isLoggedin()){
        UserClass userClass = userRepository.findById(id).get();
        return new UserOutput(userClass);}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized Access");
    }

    public ResponseEntity<?> logout(long id) {
    try{
        UserClass user = userRepository.findById(id).get();
        if(user.isLoggedin()){
            user.setLoggedin(false);
            userRepository.save(user);
            return new ResponseEntity<>("Successfully Logged Out!",HttpStatus.OK);
        }
        else return new ResponseEntity<>("You are already logged out!",HttpStatus.BAD_REQUEST);
    }
    catch(Exception e){
        return new ResponseEntity<>("Fatal Error!",HttpStatus.BAD_REQUEST);
    }
}

    public UserOutput getuserinfo(long id) {
        UserClass user = userRepository.findById(id).get();
        if(user.isLoggedin()){
            return new UserOutput(user);
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unauthorized Access");
    }

    public List<UserOutput> getCustomers(long requesterId) {
        UserClass admin = userRepository.findById(requesterId).get();
        if(admin.isLoggedin() && admin.getRole().equals(Role.ADMIN)){
            List<Optional<UserClass>> users = userRepository.findByRole(Role.CUSTOMER);
            List<UserOutput> userOutputs = new ArrayList<>();
            for(Optional<UserClass> userClass : users){
                UserClass user = userClass.get();
                userOutputs.add(new UserOutput(user));
            }
            return userOutputs;}
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad request");
    }
}
