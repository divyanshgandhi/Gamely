package com.stimuligaming.gamely;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stimuligaming.gamely.adapters.MessageAdapter;
import com.stimuligaming.gamely.helperClasses.Message;

import java.util.ArrayList;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.SendMessageOptions;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "message";
    private static final String APPID = "216e4b0515cc4b47a07360ac87e4ec92";
    ImageView backBtn, profileImg, gameBtn, videoCallBtn, sendBtn;
    TextView name;
    EditText messageText;
    String nameStr, uid, imageUrl;
    Context context;
    private Boolean loginStatus;

    RecyclerView messageList;
    ArrayList<Message> messages;
    MessageAdapter mAdapter;

    RtmClient mRtmClient;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        backBtn = findViewById(R.id.message_back);
        profileImg = findViewById(R.id.profile_picture);
        name = findViewById(R.id.receiver_name);
        gameBtn = findViewById(R.id.game_session_button);
        videoCallBtn = findViewById(R.id.video_call_btn);
        sendBtn = findViewById(R.id.send_message_button);
        messageText = findViewById(R.id.message_content);
        messageList = findViewById(R.id.messages_list);

        messages = new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.message_appbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();
        user = FirebaseAuth.getInstance().getCurrentUser();
        loginStatus = false;
        initialize();
        agoraInit();

    }

    private void initialize(){
        Intent intent = getIntent();
        nameStr = intent.getStringExtra("name");
        uid = intent.getStringExtra("uid");
        imageUrl = intent.getStringExtra("image");

        messageList.setLayoutManager(new LinearLayoutManager(this));
        messageList.setHasFixedSize(false);
        mAdapter = new MessageAdapter(this, messages);
        messageList.setAdapter(mAdapter);

        name.setText(nameStr);
        if(imageUrl.equals("default")){
            profileImg.setImageResource(R.drawable.ic_profile);
        }else{
            Glide.with(getApplicationContext()).load(imageUrl).into(profileImg);
        }

        videoCallBtn.setOnClickListener(click ->{
            Intent intent1 = new Intent(this, VideoCallActivity.class);
            startActivity(intent1);
        });

        sendBtn.setOnClickListener(click ->{
            formMessage();
        });

        backBtn.setOnClickListener(click ->{
            finish();
        });
    }

    private void formMessage(){
        String message = messageText.getText().toString();
        if(!message.equals("")){
            sendPeerMessage(uid, message);
        }else{
            //TODO: Add textspace shake animation
        }
    }

    public void sendPeerMessage(String dst, String content) {
        final RtmMessage message = mRtmClient.createMessage();
        message.setText(content);
        SendMessageOptions option = new SendMessageOptions();
        option.enableOfflineMessaging = true;

        mRtmClient.sendMessageToPeer(dst, message, option, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                runOnUiThread(() -> showToast("Message sent"));
                addSentMessage(content);
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.i("AgoraError", errorInfo.toString());
                runOnUiThread(() -> showToast("Message sending failed"));
            }
        });
    }

    public void agoraInit() {
        try {
            mRtmClient = RtmClient.createInstance(context, APPID,
                    new RtmClientListener() {
                        @Override
                        public void onConnectionStateChanged(int state, int reason) {
                            Log.d(TAG, "Connection state changes to "
                                    + state + " reason: " + reason);
                        }

                        @Override
                        public void onMessageReceived(RtmMessage rtmMessage, String peerId) {
                            String msg = rtmMessage.getText();
                            Log.d(TAG, "Message received " + " from " + peerId + msg);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("Message received " + " from " + peerId + msg);
                                    if(peerId.equals(uid)){
                                        addReceivedMessage(msg);
                                    }
                                }
                            });
                            //addReceivedMessage(msg);
                        }

                        @Override
                        public void onTokenExpired() {

                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, "RTM SDK initialization fatal error!");
            throw new RuntimeException("You need to check the RTM initialization process.");
        }

        mRtmClient.login(null, user.getUid(), new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                loginStatus = true;
                Log.d(TAG, "login success!");
            }
            @Override
            public void onFailure(ErrorInfo errorInfo) {
                loginStatus = false;
                Log.d(TAG, "login failure!");
            }
        });
    }

    private void addSentMessage(String message){
        Message sentMessage = new Message(message, "default", false);
        messages.add(sentMessage);
        mAdapter = new MessageAdapter(this, messages);
        messageList.setAdapter(mAdapter);
    }

    private void addReceivedMessage(String message){
        Message receivedMessage = new Message(message, "default", true);
        messages.add(receivedMessage);
        mAdapter = new MessageAdapter(this, messages);
        messageList.setAdapter(mAdapter);
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
