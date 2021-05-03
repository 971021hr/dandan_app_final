package com.example.tantan.ui.menu_setting;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.LoginData;
import com.example.tantan.data.LoginResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPage extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailLoginButton;
    private ServiceApi service;
    TextView join_text;
    TextView pwd_email_text;

    final static int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("로그인/회원가입");

        //service = RetrofitClient.getClient().create(ServerApi.class);

        // ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("로그인");
        //actionBar.setDisplayHomeAsUpEnabled(true);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        mEmailLoginButton = (Button) findViewById(R.id.login_button);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        mEmailLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        join_text = (TextView) findViewById(R.id.join_text);
        pwd_email_text = (TextView) findViewById(R.id.findpw_text);

//        join_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent join_intent = new Intent(LoginPage.this, JoinPage.class);
//                startActivity(join_intent);
//            }
//        });
//        pwd_email_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent pwd_email_intent = new Intent(LoginPage.this, PassWordFindPage.class);
//                startActivity(pwd_email_intent);
//            }
//        });

    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            mEmailView.setError("비밀번호를 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mPasswordView;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            mEmailView.setError("이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new LoginData(email, password));
        }
    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(LoginPage.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginPage.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK)
                    finish();
                break;
        }
    }

    /*
    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {

                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.

                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("user_id", "androidTest");

                jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;

                BufferedReader reader = null;

                try{

                    //URL url = new URL("http://localhost:3000/users");

                    URL url = new URL(urls[0]);//url을 가져온다.

                    con = (HttpURLConnection) url.openConnection();

                    con.connect();//연결 수행

                    //입력 스트림 생성

                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.

                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳

                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수

                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.

                    while((line = reader.readLine()) != null){

                        buffer.append(line);

                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String… urls) 니까

                    return buffer.toString();

                    //아래는 예외처리 부분이다.

                } catch (MalformedURLException e){

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                } finally {

                    //종료가 되면 disconnect메소드를 호출한다.

                    if(con != null){

                        con.disconnect();

                    }

                    try {

                        //버퍼를 닫아준다.

                        if(reader != null){

                            reader.close();

                        }

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }//finally 부분

            } catch (Exception e) {

                e.printStackTrace();

            }

            return null;
        }


        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            join_text.setText(result);

            Toast.makeText(LoginPage.this, result, Toast.LENGTH_SHORT).show();
        }

    }
    /*

/*
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                Intent intent = new Intent();
                intent.putExtra("이름", "차현경");
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.join_text:
                Intent join_intent = new Intent(this, JoinPage.class);
                startActivity(join_intent);
                break;

            case R.id.findpw_text:
                Intent findpw_intent = new Intent(this, PassWordPage.class);
                startActivity(findpw_intent);
                break;
        }
    }*/

}