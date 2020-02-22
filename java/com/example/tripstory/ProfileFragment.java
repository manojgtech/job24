package com.example.tripstory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
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

        //mActivity = (DashBoardActivity) getActivity();
      //  mActivity.setAboutDataListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_dashboard, container, false);


DashBoardActivity dac=(DashBoardActivity)getActivity();
  final Bundle b= dac.sendData();


         if(b!=null) {
             String bio = b.getString("bio");
             String email = b.getString("email");
             String name = b.getString("name");
             String mob = b.getString("mobile");
             String dob = b.getString("dob");
             String img = b.getString("image");
             String linkd = b.getString("linkedin");
             String addrs = b.getString("address");
             //String bio=this.getArguments().getString("bio");

             TextView txtname = (TextView) root.findViewById(R.id.biotxt);


             txtname.setText(bio);
             CircleImageView imgv = root.findViewById(R.id.dp_image);
             TextView uname = root.findViewById(R.id.text_home);
             TextView dobtxt = root.findViewById(R.id.dobtxt);
             TextView mobtxt = root.findViewById(R.id.phonetxt);
             TextView emailtxt = root.findViewById(R.id.emailtxt);
             TextView linktxt = root.findViewById(R.id.linkdxt);
             TextView adrstxt = root.findViewById(R.id.txtaddrs);
             Picasso
                     .with(getContext())
                     .load(img)
                     .into(imgv);
             uname.setText(name);
             dobtxt.setText(dob);
             mobtxt.setText(mob);
             emailtxt.setText(email);
             linktxt.setText(linkd);
             adrstxt.setText(addrs);
         }


         //logout button
      ImageButton logoutbtn= root.findViewById(R.id.logoutbtn);
         logoutbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 SharedPreferences sharedPreferences
                         = getActivity().getSharedPreferences("MySharedPref",
                         MODE_PRIVATE);
                 boolean fblogin=sharedPreferences.getBoolean("FB",false);
                 SharedPreferences.Editor editor = sharedPreferences.edit();

                 editor.clear();
                 editor.commit();
                 getActivity().finish();
                 if(fblogin){
                 new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                         .Callback() {
                     @Override
                     public void onCompleted(GraphResponse graphResponse) {


                         LoginManager.getInstance().logOut();


                     }
                 }).executeAsync();
                 }
                 Intent logoutin=new Intent(getActivity(),MainActivity.class);
                 startActivity(logoutin);
             }
         });

         //edit profile

        ImageButton editprobtn=root.findViewById(R.id.edit_profile);
        editprobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_intent=new Intent(getActivity(), Jobprofile.class);
                edit_intent.putExtras(b);
                startActivity(edit_intent);
            }
        });

        return root;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public void setInteractionListener(OnFragmentInteractionListener mListener){
        this.mListener = mListener;
    }
}
