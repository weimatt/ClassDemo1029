package com.example.classdemo1029;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonCreate, buttonEdit, buttonDelete, buttonFind;
    EditText editTextCreateTitle, editTextCreateAuthor, editTextEditTitle,
            editTextEditAuthor;
    EditText editTextDeleteAuthor, editTextFindAuthor;
    TextView textViewShowBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCreate = findViewById(R.id.buttonCreate);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonFind = findViewById(R.id.buttonFind);

        editTextCreateAuthor = findViewById(R.id.editTextCreateAuthor);
        editTextCreateTitle = findViewById(R.id.editTextCreateTitle);
        editTextEditAuthor = findViewById(R.id.editTextEditAuthor);
        editTextEditTitle = findViewById(R.id.editTextEditTitle);
        editTextDeleteAuthor = findViewById(R.id.editTextDeleteAuthor);
        editTextFindAuthor = findViewById(R.id.editTextFindAuthor);

        textViewShowBooks = findViewById(R.id.textViewShowBooks);

        buttonCreate.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonFind.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Books");

        if (buttonCreate == v){
            String createAuthor = editTextCreateAuthor.getText().toString();
            String createTitle = editTextCreateTitle.getText().toString();

            Book myBook = new Book(createAuthor,createTitle);
            myRef.push().setValue(myBook);

        } else if(buttonEdit == v){

            String editTitle = editTextEditTitle.getText().toString();
            String editAuthor = editTextEditAuthor.getText().toString();

            myRef.orderByChild("title").equalTo(editTitle).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            })

        } else if(buttonFind == v){
            String findAuthor = editTextFindAuthor.getText().toString();
            myRef.orderByChild("author").equalTo(findAuthor).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String findKey = dataSnapshot.getKey();
                    Book foundBook = dataSnapshot.getValue(Book.class);
                    String findTitle = foundBook.title;

                    textViewShowBooks.setText(findTitle);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (buttonDelete == v){
            String deleteAuthor = editTextDeleteAuthor.getText().toString();
            myRef.orderByChild("author").equalTo(deleteAuthor).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String deleteKey = dataSnapshot.getKey();
                    myRef.child(deleteKey).setValue(null);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
