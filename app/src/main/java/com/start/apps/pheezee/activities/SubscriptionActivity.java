package com.start.apps.pheezee.activities;

import static com.start.apps.pheezee.activities.SortHistoryDate.context;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.start.apps.pheezee.pojos.BluetoothCommunication;
import com.start.apps.pheezee.pojos.ReportCommunicationData;
import com.start.apps.pheezee.retrofit.GetDataService;
import com.start.apps.pheezee.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import start.apps.pheezee.R;

public class SubscriptionActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    LinearLayout SubscriptionFistView,First500ReportSelect,First100ReportSelect;
    LinearLayout SubscriptionSecondView,Second500ReportSelect,Second100ReportSelect;

    EditText PatientName,PatinetPhone,PatientEmail;
    GetDataService getDataService;

    Button BuyNow;
    String pt_email, pt_name, pt_number,pt_amount;

    ImageView iv_back_app_info_kt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        SubscriptionFistView = findViewById(R.id.subscriptionfirstview);
        SubscriptionSecondView = findViewById(R.id.subscriptionsecondview);
        First500ReportSelect=findViewById(R.id.first_500_report_select);
        First100ReportSelect=findViewById(R.id.first_100_report_select);
        Second500ReportSelect=findViewById(R.id.second_500_report_select);
        Second100ReportSelect=findViewById(R.id.second_100_report_select);
        PatientName=findViewById(R.id.patient_name);
        PatientEmail=findViewById(R.id.Patient_Email);
        PatinetPhone=findViewById(R.id.Patient_Phone);
        BuyNow=findViewById(R.id.buy_now_button);
        iv_back_app_info_kt = findViewById(R.id.iv_back_app_info);

        iv_back_app_info_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        First500ReportSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               SubscriptionFistView.setVisibility(View.VISIBLE);
               SubscriptionSecondView.setVisibility(View.GONE);
            }
        });

        First100ReportSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubscriptionFistView.setVisibility(View.GONE);
                SubscriptionSecondView.setVisibility(View.VISIBLE);
            }
        });
        Second500ReportSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SubscriptionFistView.setVisibility(View.VISIBLE);
                SubscriptionSecondView.setVisibility(View.GONE);
            }
        });

        Second100ReportSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubscriptionFistView.setVisibility(View.GONE);
                SubscriptionSecondView.setVisibility(View.VISIBLE);
            }
        });


        Intent intent = getIntent();
        PatientName.setText(intent.getStringExtra("pt_name"));
        PatientEmail.setText(intent.getStringExtra("pt_email"));
        PatinetPhone.setText( intent.getStringExtra("pt_number"));

        BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pt_email = PatientEmail.getText().toString();
                pt_name = PatientName.getText().toString();
                pt_number = PatinetPhone.getText().toString();
                ReportCommunication data = new ReportCommunication(pt_email, pt_name, pt_number,pt_amount);
                Call<ReportCommunicationData> reportCommunicationDataCall = getDataService.report_email_con(data);
                reportCommunicationDataCall.enqueue(new Callback<ReportCommunicationData>() {
                    @Override
                    public void onResponse(Call<ReportCommunicationData> call, Response<ReportCommunicationData> response) {
                        if (response.code() == 200) {
                            ReportCommunicationData res = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReportCommunicationData> call, Throwable t) {

                    }
                });
                finish();

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("status", "true");
                editor.apply();
                Toast.makeText(SubscriptionActivity.this, "Request sent.", Toast.LENGTH_LONG).show();
            }
        });
    }
}