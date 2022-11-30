package com.example.oopsProject.Ewallet;


import com.example.oopsProject.Security.passwordencoder;
import com.example.oopsProject.UserClass.TopUpClass;
import com.example.oopsProject.UserClass.UserClass;
import com.example.oopsProject.UserClass.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EWalletService {
    private final EWalletRepository eWalletRepository;
    private final UserRepository userRepository;
    public EWalletService(EWalletRepository eWalletRepository,UserRepository userRepository) {
        this.userRepository = userRepository;
        this.eWalletRepository = eWalletRepository;
    }

    public ResponseEntity<?> topUp(TopUpClass topUpClass){
        UserClass user = userRepository.findById(topUpClass.getUser_id()).get();
        if(user.getPassword().equals(passwordencoder.encode(topUpClass.getPassword()))){
        Ewallet eWallet = eWalletRepository.findByowner(userRepository.findById(topUpClass.getUser_id()).get()).get();
        System.out.println("THIS IS THE EWALLET AMOUNT BEFORE"+eWallet.getBalance());
        eWallet.topUp(topUpClass.getBalance());
        eWalletRepository.save(eWallet);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);}
        else return new ResponseEntity<>("WRONG PASSWORD",HttpStatus.OK);
    }

    public Ewallet getBalance(long id) {
        Ewallet ewallet = eWalletRepository.findByowner(userRepository.findById(id).get()).get();
        return ewallet;
    }
}
