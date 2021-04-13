package com.example.project_1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.UnsupportedEncodingException;


public class test extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Button predict;
    private EditText esex, eage, ecurrent_smoker, ecigs_per_day, eBP_meds, ePre_stroke, ePre_hype, ediabetes, etot_chol, esys_BP, edia_BP, eBMI, eheartrate, eglucose;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String email;
    private Interpreter tflite;
    private TextView eresult;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        esex = (EditText) findViewById(R.id.esex);
        eage = (EditText) findViewById(R.id.eage);
        ecurrent_smoker = (EditText) findViewById(R.id.ecurrent_smoker);
        ecigs_per_day = (EditText) findViewById(R.id.ecigs_per_day);
        eBP_meds = (EditText) findViewById(R.id.eBP_meds);
        ePre_stroke = (EditText) findViewById(R.id.ePre_stroke);
        ePre_hype = (EditText) findViewById(R.id.ePre_hype);
        ediabetes = (EditText) findViewById(R.id.ediabetes);
        etot_chol = (EditText) findViewById(R.id.etot_chol);
        esys_BP = (EditText) findViewById(R.id.esys_BP);
        edia_BP = (EditText) findViewById(R.id.edia_BP);
        eBMI = (EditText) findViewById(R.id.eBMI);
        eheartrate = (EditText) findViewById(R.id.eheartrate);
        eglucose = (EditText) findViewById(R.id.eglucose);
        eresult = (TextView) findViewById(R.id.eresult);


        predict = (Button) findViewById(R.id.predict);

        predict.setOnClickListener(v -> {
            Context context = getApplicationContext();
            try {
                String sex = esex.getText().toString().trim();
                String age = eage.getText().toString().trim();
                String currentSmoker = ecurrent_smoker.getText().toString().trim();
                String cigsPerDay = ecigs_per_day.getText().toString().trim();
                String BPMeds = eBP_meds.getText().toString().trim();
                String prevalentStroke = ePre_stroke.getText().toString().trim();
                String prevalentHyp = ePre_hype.getText().toString().trim();
                String diabetes = ediabetes.getText().toString().trim();
                String totChol = etot_chol.getText().toString().trim();
                String sysBP = esys_BP.getText().toString().trim();
                String diaBP = edia_BP.getText().toString().trim();
                String BMI = eBMI.getText().toString().trim();
                String heartRate = eheartrate.getText().toString().trim();
                String glucose = eglucose.getText().toString().trim();
                postRequest(context, sex, age, currentSmoker, cigsPerDay, BPMeds, prevalentStroke, prevalentHyp, diabetes, totChol, sysBP, diaBP, BMI, heartRate, glucose);
                //Submit("https://ensemble-try1.herokuapp.com/predict", "xyz", context);
                validateinputs(sex, age, currentSmoker, cigsPerDay, BPMeds, prevalentStroke, prevalentHyp, diabetes, totChol, sysBP, diaBP, BMI, heartRate, glucose);
            } catch (Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }

            //startActivity(new Intent(test.this, result.class));
        });
    }


    private void postRequest(Context context, String sex, String age, String currentSmoker, String cigsPerDay, String BPMeds, String prevalentStroke, String prevalentHyp, String diabetes, String totChol, String sysBP, String diaBP, String BMI, String heartRate, String glucose) throws Exception {
        // replace with your host server URI
        String URL = "https://ensemble-try2.herokuapp.com/predict";

        JSONObject postDataParams = new JSONObject();
        postDataParams.put("sex", sex);
        postDataParams.put("age", age);
        postDataParams.put("currentSmoker", currentSmoker);
        postDataParams.put("cigsPerDay", cigsPerDay);
        postDataParams.put("BPMeds", BPMeds);
        postDataParams.put("prevalentStroke", prevalentStroke);
        postDataParams.put("prevalentHyp", prevalentHyp);
        postDataParams.put("diabetes", diabetes);
        postDataParams.put("totChol", totChol);
        postDataParams.put("sysBP", sysBP);
        postDataParams.put("diaBP", diaBP);
        postDataParams.put("BMI", BMI);
        postDataParams.put("heartRate", heartRate);
        postDataParams.put("glucose", glucose);
        Submit(URL, postDataParams.toString(), context);
    }

    private void validateinputs (String sex, String age, String currentSmoker, String cigsPerDay, String BPMeds, String prevalentStroke, String prevalentHyp, String diabetes, String totChol, String sysBP, String diaBP, String BMI, String heartRate, String glucose) {
        if (glucose.isEmpty()) {
            eglucose.setError("Please provide a valid glucose value");
            eglucose.requestFocus();
            return;
        }
        if (heartRate.isEmpty()) {
            eheartrate.setError("Please provide a valid heart rate");
            eheartrate.requestFocus();
            return;
        }
        if (BMI.isEmpty()) {
            eBMI.setError("Please provide a valid BMI");
            eBMI.requestFocus();
            return;
        }
        if (diaBP.isEmpty()) {
            edia_BP.setError("Please provide a valid diabolic BP value");
            edia_BP.requestFocus();
            return;
        }
        if (sysBP.isEmpty()) {
            esys_BP.setError("Please provide a valid systolic BP value");
            esys_BP.requestFocus();
            return;
        }
        if (totChol.isEmpty()) {
            etot_chol.setError("Please provide a valid total cholesterol value");
            etot_chol.requestFocus();
            return;
        }
        if (diabetes.isEmpty()) {
            ediabetes.setError("Please provide 1 if you have diabetes else 0");
            ediabetes.requestFocus();
            return;
        }
        if (prevalentStroke.isEmpty()) {
            ePre_stroke.setError("Please provide 1 if you have prevalent stroke else 0");
            ePre_stroke.requestFocus();
            return;
        }
        if (prevalentHyp.isEmpty()) {
            ePre_hype.setError("Please provide 1 if you have prevalent hypertension else 0");
            ePre_hype.requestFocus();
            return;
        }
        if (sex.isEmpty()) {
            esex.setError("Please provide 1 for male or 0 for female");
            esex.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            eage.setError("Please provide a valid age");
            eage.requestFocus();
            return;
        }
        if (currentSmoker.isEmpty()) {
            ecurrent_smoker.setError("Please provide 1 if you're a current smoker else 0");
            ecurrent_smoker.requestFocus();
            return;
        }
        if (cigsPerDay.isEmpty()) {
            ecigs_per_day.setError("Please provide count of smoked cigarettes per day");
            ecigs_per_day.requestFocus();
            return;
        }
        if (BPMeds.isEmpty()) {
            eBP_meds.setError("Please provide 1 if you're on BP medication else 0");
            eBP_meds.requestFocus();
            return;
        }
    }
    public void Submit(String URL, String data, Context context) {
        Log.d("-----", data);
        final String savedata = data;


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    Toast.makeText(test.this,"If result is 1 you have to go see your doctor!", Toast.LENGTH_LONG).show();

                    eresult.setText(objres.toString());

                    // do your work with response object

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                //Log.v("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
    }



        /*

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex = esex.getText().toString();
                String age = eage.getText().toString().trim();
                String current_smoker = ecurrent_smoker.getText().toString().trim();
                String cigs_per_day = ecigs_per_day.getText().toString().trim();
                String BP_meds = eBP_meds.getText().toString().trim();
                String Pre_stroke = ePre_stroke.getText().toString().trim();
                String Pre_hype = ePre_hype.getText().toString().trim();
                String diabetes = ediabetes.getText().toString().trim();
                String total_chol = etot_chol.getText().toString().trim();
                String sys_BP = esys_BP.getText().toString().trim();
                String dia_BP = edia_BP.getText().toString().trim();
                String BMI = eBMI.getText().toString().trim();
                String heartrate = eheartrate.getText().toString().trim();
                String glucose = eglucose.getText().toString().trim();

                validateinputs(sex,age,current_smoker,cigs_per_day,BP_meds,Pre_stroke,Pre_hype,diabetes,total_chol,sys_BP,dia_BP,BMI,heartrate,glucose);
                OkHttpClient client = new OkHttpClient();
                url = "https://ensemble-model.herokuapp.com"+sex+"/"+age+"/"+current_smoker+"/"+cigs_per_day+"/"+BP_meds+"/"+Pre_stroke+"/"+Pre_hype+"/"+diabetes+"/"+total_chol+"/"+sys_BP+"/"+dia_BP+"/"+BMI+"/"+heartrate+"/"+glucose;
                Request request = new Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()){
                            String myresponse = response.body().string();
                            test.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    result.setText(myresponse);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
*/



            }

