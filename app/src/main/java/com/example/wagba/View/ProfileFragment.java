package com.example.wagba.View;

import static com.example.wagba.View.MainActivity.EMAIL;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wagba.R;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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

    TextView name, name2,email,number, email2;
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

        set_profile_data(view.getContext());


        return view;
    }

    private void set_profile_data(Context context) {
        UserDatabase db = Room.databaseBuilder(context,
                UserDatabase.class, "user").allowMainThreadQueries().build();
        if (EMAIL!=null) {
            UserEntity user_room = db.userDao().getCurrentUser(EMAIL);

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