package com.blockchain.ipfs.ui.wallet.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.blockchain.ipfs.R;

import butterknife.BindView;

public class SetWalletFragment extends DialogFragment implements View.OnClickListener {

    private UserAuthCancelListener mUserAuthCancelListener;
    private UserAuthOKListener mUserAuthOKListener;

    @BindView(R.id.btn_quit)
    Button btn_quit;


    @BindView(R.id.btn_next)
    Button btn_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        setCancelable(false);
        View view = inflater.inflate(R.layout.fragment_confirm, container);

//        view.findViewById(R.id.btn_quit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mUserAuthCancelListener != null) {
//                    mUserAuthCancelListener.onUserAuthCancel();
//                }
//                dismissAllowingStateLoss();
//            }
//        });
//        view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mUserAuthOKListener != null) {
//                    mUserAuthOKListener.onUserAuthOK();
//                }
//                dismissAllowingStateLoss();
//            }
//        });


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_quit:
                dismiss();
                break;
            case R.id.btn_next:  // 去绑卡
//                Intent mIntent = new Intent(getActivity(), BindBankCardActivity.class);
//                getActivity().startActivity(mIntent);
                this.dismiss();
                break;
            default:
                break;
        }

    }

    public interface UserAuthCancelListener {
        public void onUserAuthCancel();
    }

    public interface UserAuthOKListener {
        public void onUserAuthOK();
    }

    //初始化mListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mUserAuthCancelListener = (UserAuthCancelListener) activity;

            mUserAuthOKListener = (UserAuthOKListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement BackListener");
        }
    }

}
