package com.example.kafkaConsumerAndFirebaseTest.infra.repository;

import com.example.kafkaConsumerAndFirebaseTest.infra.datasource.Firebase;
import com.google.firebase.database.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class FirebaseRepository {

    private final Firebase firebase;

    @Autowired
    public FirebaseRepository(Firebase firebase) {
        this.firebase = firebase;
    }

    public void insertMessage(String message) {
        DatabaseReference reference = firebase.getDatabaseClient().getReference("sales");
        if (reference == null) {
            throw new RuntimeException();
        }
        reference.runTransaction(
                new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData currentData) {
                        Object salesValue = currentData.getValue();
                        if (salesValue == null) {
                            currentData.setValue(message);
                            return Transaction.success(currentData);
                        }

                        currentData.child(message).setValue(message);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData) {
                        if (error != null || committed == false) {
                            log.error("Transaction failed abnormally: error = {}, committed = {}, message = {}",
                                    error, committed, message);
                            return;
                        }
                        log.info("Transaction completed [{}]", message);
                    }
                }
        );
    }
}
