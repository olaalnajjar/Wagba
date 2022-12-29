package com.example.wagba.View;

import static com.example.wagba.View.Payment.Order_NO;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wagba.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TrackOrderFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TrackOrderFragment() {
        // Required empty public constructor
    }

    public static TrackOrderFragment newInstance(String param1, String param2) {
        TrackOrderFragment fragment = new TrackOrderFragment();
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
    ImageView img1, img2, img3,img4;
    View dot1,dot2,dot3,dot4;
    View line1,line2,line3;
    TextView title1, title2, title3, title4;
    TextView subtitle1, subtitle2, subtitle3;
    public static String Status="";

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_order, container, false);



        img1=view.findViewById(R.id.order_img_1);
        img2=view.findViewById(R.id.order_img_2);
        img3=view.findViewById(R.id.order_img_3);
        img4=view.findViewById(R.id.order_img_4);

        dot1=view.findViewById(R.id.view_dot_1);
        dot2=view.findViewById(R.id.view_dot_2);
        dot3=view.findViewById(R.id.view_dot_3);
        dot4=view.findViewById(R.id.view_dot_4);

        line1=view.findViewById(R.id.view_line_1);
        line2=view.findViewById(R.id.view_line_2);
        line3=view.findViewById(R.id.view_line_3);

        title1=view.findViewById(R.id.order_text_1);
        title2=view.findViewById(R.id.order_text_2);
        title3=view.findViewById(R.id.order_text_3);
        title4=view.findViewById(R.id.order_text_4);

        subtitle1=view.findViewById(R.id.sub_text_1);
        subtitle2=view.findViewById(R.id.sub_text_2);
        subtitle3=view.findViewById(R.id.sub_text_3);


        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("history_item/"+String.valueOf(Order_NO));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Status =snapshot.child("Status").getValue().toString();
                    set_tracking_data(view);
                    if("Order Delivered".equals(snapshot.child("Status").getValue().toString())){
                        myRef.child("order_status").setValue("Delivered");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    void set_tracking_data(View view){

        switch(Status) {
            case "Order Placed":
                set_order_state_1(view);
                break;
            case "Order Confirmed":
                set_order_state_2(view);


                break;
            case "Preparing Order":

                set_order_state_3(view);


                break;
            case "Order out for Delivery":
                set_order_state_4(view);


                break;
            case "Order Delivered":

                set_order_state_4(view);
                dot4.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_done,null));

                break;
            default:

                break;

        }


    }

    void set_order_state_1(View view){
        img1.setAlpha(1F);
        title1.setAlpha(1F);
        subtitle1.setAlpha(1F);
        dot1.setAlpha(1F);
        line1.setAlpha(1F);
        dot1.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_done,null));
        line1.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_processing,null));

    }
    void set_order_state_2(View view){
        set_order_state_1(view);
        img2.setAlpha(1F);
        title2.setAlpha(1F);
        subtitle2.setAlpha(1F);
        dot2.setAlpha(1F);
        dot2.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_done,null));
        line2.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_processing,null));
        line2.setAlpha(1F);
        line1.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_done,null));

    }
    void set_order_state_3(View view){
        set_order_state_2(view);
        img3.setAlpha(1F);
        title3.setAlpha(1F);
        subtitle3.setAlpha(1F);
        dot3.setAlpha(1F);
        dot3.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_done,null));
        line2.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_done,null));
        line3.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_processing,null));
        line3.setAlpha(1F);

    }
    void set_order_state_4(View view){
        set_order_state_3(view);
        img4.setAlpha(1F);
        title4.setAlpha(1);
        dot4.setAlpha(1F);
        dot4.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_processing,null));
        line3.setBackground(ResourcesCompat.getDrawable(view.getResources(),R.drawable.circle_done,null));
   }
}