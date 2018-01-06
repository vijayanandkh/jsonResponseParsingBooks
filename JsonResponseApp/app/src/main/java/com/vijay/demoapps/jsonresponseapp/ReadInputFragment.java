package com.vijay.demoapps.jsonresponseapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReadInputFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReadInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadInputFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String parameters;
    EditText editTextBooks;
    private OnFragmentInteractionListener mListener;

    public ReadInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadInputFragment newInstance(String param1, String param2) {
        ReadInputFragment fragment = new ReadInputFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read_input, container, false);;
        Button requestButton = (Button) view.findViewById(R.id.button_get_books);
        editTextBooks =(EditText) view.findViewById(R.id.editTextBooks);
        requestButton.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String parameter) {
        if (mListener != null) {
            mListener.onButtonClicked(parameter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        String value = editTextBooks.getText().toString();
        if(value!=null) {
            StringBuilder parameters = new StringBuilder();
            parameters.append(Constants.COUNT).append("=").append(value);
            parameters.append("&");
            parameters.append(Constants.OFFSET).append("=").append(Constants.OFFSET_VALUE);
            parameters.append("&");
            parameters.append(Constants.QUERRY).append("=").append(Constants.QUERRY_BOOKS);
            mListener.onButtonClicked(parameters.toString());
        } else {
            Toast.makeText((MainActivity)mListener, "Enter valid books count", Toast.LENGTH_SHORT).show();
        }
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
        void onButtonClicked(String parameter);
    }
}
