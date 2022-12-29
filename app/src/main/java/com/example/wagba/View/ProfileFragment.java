package com.example.wagba.View;

import static com.example.wagba.View.MainActivity.EMAIL;
import static com.example.wagba.ViewModel.HomeViewModel.update_user_data;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wagba.R;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView name2, email2;
    EditText name ,email, number;
    ImageView confirm;
    Integer User_id;
    String name1,num1,email1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        name = view.findViewById(R.id.profile_name);
        name2 = view.findViewById(R.id.Profile_name);
        email = view.findViewById(R.id.profile_email);
        email2 = view.findViewById(R.id.profile_email_1);
        number = view.findViewById(R.id.profile_number);
        confirm = view.findViewById(R.id.done_btn);

        set_profile_data(view.getContext());

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1=name.getText().toString();
                email1=email.getText().toString();
                num1=number.getText().toString();
                update_user_data(User_id, name1,num1 ,email1,view.getContext());
                set_profile_data(view.getContext());
            }
        });

        return view;
    }



    private void set_profile_data(Context context) {
        UserDatabase db = Room.databaseBuilder(context,
                UserDatabase.class, "user").allowMainThreadQueries().build();
        if (EMAIL!=null) {
            UserEntity user_room = db.userDao().getCurrentUser(EMAIL);
            User_id = user_room.getId();

            name.setText(user_room.getName());
            name2.setText(user_room.getName());
            email.setText(user_room.getEmail());
            email2.setText(user_room.getEmail());
            number.setText(user_room.getNumber());

        }else{
            FirebaseAuth auth;
            auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            name.setText(user.getDisplayName());
            name2.setText(user.getDisplayName());
            email.setText(user.getEmail());
            email2.setText(user.getEmail());
            number.setText(user.getPhoneNumber());

        }
    }
}

