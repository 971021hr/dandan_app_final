package com.example.tantan.network;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class SendMail extends AppCompatActivity {
    String managerEmail = "이메일 받을 수 있는 아이디";            //단단 관리자 이메일(전송할 이메일)
    String managerPw = "비밀번호";                      //단단 관리자 이메일 비밀번호

    public void sendSecurityCode(Context context, String sendTo,String sendPw){
        try {
            GMailSender gMailSender = new GMailSender(managerEmail, managerPw);
            gMailSender.sendMail("단단에서 보낸 임시비밀번호", "임시비밀번호 : " + sendPw, sendTo);

        }catch (SendFailedException e){
            Toast.makeText(context, "이메일 형식이 잘못되었습니다.",Toast.LENGTH_SHORT).show();

        }catch (MessagingException e){
            Toast.makeText(context, "인터넷 연결을 확인해주세요.",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
