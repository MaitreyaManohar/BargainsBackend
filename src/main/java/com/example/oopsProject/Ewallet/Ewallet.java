package com.example.oopsProject.Ewallet;

import com.example.oopsProject.UserClass.UserClass;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class Ewallet {
    @Id
    @SequenceGenerator(
            name = "Ewallet_sequence",
            sequenceName = "Ewallet_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,generator = "Ewallet_sequence"
    )
    private long walletId;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "owner_id")
    private UserClass owner;
    private long balance;

    public Ewallet(){}

    public Ewallet(UserClass owner, long balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public long getWalletId() {
        return walletId;
    }

    public UserClass getOwner() {
        return owner;
    }

    public long getBalance() {
        return balance;
    }

    public void topUp(long amount){
        balance+=amount;
    }
    public long deduct(long amount){
        if(amount>balance){
            return -1;
        }
        else{
            balance-=amount;
            return balance;
        }
    }
}
