package com.example.usernamelogin.NonMemberUser.Reservations;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usernamelogin.R;

public abstract class dialog_fornonmember_res_pending extends Dialog{

    private Context context;
    ImageView message, call;
    public dialog_fornonmember_res_pending(Context context) {
        super(context);
        this.context = context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState != null ? savedInstanceState : new Bundle());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.nonmeberuserreservationclick, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);


        message = findViewById(R.id.message_id);
        call = findViewById(R.id.call_id);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiverNumber = Reservations.mobilenumberofgyminres.trim();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"+ receiverNumber));
                context.startActivity(intent);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiverNumber = Reservations.mobilenumberofgyminres.trim();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:"+ receiverNumber));
                context.startActivity(intent);
            }
        });

    }
}
