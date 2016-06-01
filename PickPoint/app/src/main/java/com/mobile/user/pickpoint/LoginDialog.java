package com.mobile.user.pickpoint;

import android.app.Activity;

import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


/**
 * Created by user on 13.05.16.
 */
public class LoginDialog extends DialogFragment implements OnEditorActionListener {


    // specify interface, realize in MainActivity
    public interface EditAuthDialogListener {
        void OnFinishEditDialog(String login, Boolean correct);

    }

    private EditText loginEdit;

    // empty constractor
    public LoginDialog() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.activity_login, container);
        loginEdit = (EditText) v.findViewById(R.id.idText);
        //v.findViewById(R.id.loginButton).setOnClickListener(this);
        //v.findViewById(R.id.cancelButton).setOnClickListener(this);
        getDialog().setTitle("Login!");

        getDialog().setCancelable(false);


        //dismiss back tap
        getDialog().setCanceledOnTouchOutside(false);

        // Show soft keyboard automatically
        loginEdit.requestFocus();
        loginEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        loginEdit.setSingleLine();
        loginEdit.setOnEditorActionListener(this);

        return v;
    }

    public boolean checkLogin(String login) {
        if(login.contentEquals("login") ) {
            Log.i("CHECK LOGIN", "EQUALS");
            return true;
        }

       return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        // checks "Done" buttom
        String login = null;
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Done pressed!  Do something here.

            Log.i("ON EDITOR ACTION", "Done tapped");
            login = loginEdit.getText().toString();

            if(checkLogin(login)) {
                // Return input text to activity
                EditAuthDialogListener activity = (EditAuthDialogListener) getActivity();
                activity.OnFinishEditDialog(login, true);
                this.dismiss();
            }
            return true;
        }
        return false;
    }




        /*
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            getDialog().setTitle("Login!");
            //getDialog().setCancelable(false);
            getDialog().setCanceledOnTouchOutside(false);

            View v = inflater.inflate(R.layout.activity_login, container);
            loginEdit = (EditText) v.findViewById(R.id.idText);
            v.findViewById(R.id.loginButton).setOnClickListener(this);
            v.findViewById(R.id.cancelButton).setOnClickListener(this);


            return v;
        }
        */

       /* public static interface OnCompleteListener {
            public abstract void onComplete(String time);
        }

        private OnCompleteListener mListener;

        // make sure the Activity implemented it
        public void onAttach(Activity activity) {
            try {
                this.mListener = (OnCompleteListener)activity;
            }
            catch (final ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
            }
        }
        */
    /*
        public void onClick(View v) {
            Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());

            String content = loginEdit.te;

         //   mListener.onComplete();

           //// Bundle args = new Bundle();
            //setArguments(args);
            //dismiss();

        }

        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            Log.d(LOG_TAG, "Dialog 1: onDismiss");

        }

        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            Log.d(LOG_TAG, "Dialog 1: onCancel");
        }
        */
    }



