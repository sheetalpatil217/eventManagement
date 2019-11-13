package com.example.eventmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventmanagement.Pojo.Team;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.eventmanagement.QuestionAPI.JSON;

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener,MyReceiver.OTPReceiveListener {

    private static int RESOLVE_HINT = 1;
    private MyReceiver smsReceiver;;
    GoogleApiClient apiClient;
    TextView phoneNumberText;
    EditText inputCode;
    Button sendCode;
    ProgressBar progressBarCircle;
    String[] codes;
    Button signin;
    String obj,token,message;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Event Management");
        AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(this);

        // This code requires one time to get Hash keys do comment and share key
        Log.d("chella", "HashKey: " + appSignatureHashHelper.getAppSignatures().get(0));
        signin = findViewById(R.id.signin);
        signin.setEnabled(false);
        phoneNumberText = findViewById(R.id.phoneNumberText);
        sendCode = findViewById(R.id.sendCodeButton);
        inputCode = findViewById(R.id.inputCode);
        progressBarCircle = findViewById(R.id.progressBarCircle);
        progressBarCircle.setVisibility(View.INVISIBLE);

        phoneNumberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestHint();
            }
        });
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String phone =phoneNumberText.getText().toString().split("\\+")[1];
                String number =  "{\"phone\":\""+phone+"\"}";
                String param = gson.toJson(number);
                Log.d("test","json"+number);
                Log.d("test","json"+param);
                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, param);

                Request request = new Request.Builder()
                        .url("http://localhost:3000/sendcode")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("sheetal", "call fail"+e.getMessage());
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        Log.d("sheetal", "jsondata" + response);

                    }});
                startSMSListener();
                progressBarCircle.setIndeterminate(true);
                progressBarCircle.setVisibility(View.VISIBLE);
            }
        });

    }
    private void startSMSListener() {
        try {

            smsReceiver = new MyReceiver();
            smsReceiver.setOTPListener(MainActivity.this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(this);

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // API successfully started
                    Toast.makeText(MainActivity.this, "SMS Retriever starts", Toast.LENGTH_LONG).show();

                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail to start API
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void requestHint() {
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        apiClient = new GoogleApiClient.Builder(getBaseContext())
                .addApi(Auth.CREDENTIALS_API)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                apiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                phoneNumberText.setText(credential.getId());
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onOTPReceived(String otp) {
        Log.d("chella","Otp received");
        codes = otp.split(" ");
        Log.d("chella","Code Received: "+codes[5]);
        Toast.makeText(this, "Otp Received " + codes[5], Toast.LENGTH_LONG).show();
        inputCode.setText(codes[5]);
        progressBarCircle.setVisibility(View.INVISIBLE);
        signin.setEnabled(true);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String phone =phoneNumberText.getText().toString().split("\\+")[1];
                String number =  "[{\"phone\":\""+phone+"\"}]";
                Log.d("test","json"+number);
                String param = gson.toJson(number);

                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, param);

                Request request = new Request.Builder()
                        .url("http://localhost:3000/signin")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("sheetal", "call fail"+e.getMessage());
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        Log.d("sheetal", "jsondata" + response);
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            obj = jsonObject.getString("status");
                            message=jsonObject.getString("message");
                            if(message.equalsIgnoreCase("Success")) {
                                token = jsonObject.getString("token");
                                Intent intent = new Intent(getApplicationContext(),TeamList.class);
                                intent.putExtra("TOKEN",token);
                                startActivity(intent);
                            }
                        }catch (JSONException e) {
                            Log.d("chella","Exception in parsing the JSON ");
                        }
                    }});
            }
        });
    }

    @Override
    public void onOTPTimeOut() {
        Toast.makeText(this, "Time out, please resend", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOTPReceivedError(String error) {

    }
}


