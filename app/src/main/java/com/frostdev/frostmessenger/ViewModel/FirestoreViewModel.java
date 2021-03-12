package com.frostdev.frostmessenger.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.frostdev.frostmessenger.TaskModel.CreateUserTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static com.frostdev.frostmessenger.FrostMessenger.EMAIL;
import static com.frostdev.frostmessenger.FrostMessenger.USERNAME;
import static com.frostdev.frostmessenger.FrostMessenger.USERS;

public class FirestoreViewModel extends AndroidViewModel {
    public MutableLiveData<CreateUserTask> createUserLiveData;
    private FirebaseFirestore firestore;

    public FirestoreViewModel(@NonNull Application application) {
        super(application);
        firestore = FirebaseFirestore.getInstance();
        createUserLiveData = new MutableLiveData<>();
    }

    public void createUser(CreateUserTask createUserTask) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;

        HashMap<String, Object> userMap = new HashMap<>();

        String uid = firebaseUser.getUid();

        userMap.put(USERNAME, createUserTask.getUsername());
        userMap.put(EMAIL, createUserTask.getEmail());

        DocumentReference userRef = firestore.document(USERS + '/' + uid);

        userRef.set(userMap).addOnCompleteListener(task -> {
            createUserTask.setSuccessful(task.isSuccessful());
            createUserTask.setException(task.getException());
            createUserTask.setChecked(false);
            createUserLiveData.postValue(createUserTask);
        });
    }
}
