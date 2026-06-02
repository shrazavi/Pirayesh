package com.shrazavi.pirayesh;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;


import com.shrazavi.pirayesh.Util.MyReceiver;

import java.io.File;

public class G extends Application {
    private static G app;
    public static Context context;
    public static Typeface face;
    public static SharedPreferences preferences;
    public static final Handler HANDLER1 = new Handler();
    public static int account = 0;
    public static int income = 0;
    //    private Socket socket;
    public Handler handler;

    private String username = "";
    private String roomId = "";
    private String otherUserId = "";
    //        public static String nodeurl = "https://158.255.74.118";
    public static String nodeurl = "http://192.168.1.5:8080";
    //public static String nodeurl = "http://www.shrazavi.ir:443";
//    public static String wss = "http://158.255.74.118:8000/wss";
//    public static String phpurl = "http://www.shrazavi.ir:443";
    public static String phpurl = "http://192.168.1.5/social/";
    String userid, vu;
    public static LayoutInflater inflater;
    public static Boolean isconected;
    public static final String DIR_RECORDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pirayesh/Recorder";
    public static final String DIR_VIDOE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pirayesh/Video";
    public static final String DIR_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pirayesh/Image";
    public static final String DIR_DOCUMENT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pirayesh/Document";
    public static final String DIR_AUDIO = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pirayesh/Audio";
    public static final String DIR_RECORDERV30 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/Pirayesh/Recorder";
    public static final String DIR_VIDOEV30 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/Pirayesh";
    public static final String DIR_IMAGEV30 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Pirayesh";
    public static final String DIR_DOCUMENTV30 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Pirayesh";
    public static final String DIR_AUDIOV30 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/Pirayesh/Audio";
//    {
//        try {
//            socket = IO.socket(G.nodeurl);//http://192.168.1.103:8000
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
//        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        super.onCreate();

        face = Typeface.createFromAsset(getAssets(), "fonts/iransans.ttf");
        app = this;
        app.setTheme(R.style.Theme_mainwomen);
        handler = new Handler();
        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        userid = preferences.getString("username", "not");
//        vu = preferences.getString("vu", "noty");
////        socket.connect();
////        Log.e("sdk", Build.VERSION.SDK_INT+"");
//        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        JSONObject connected = new JSONObject();
//        try {
//            connected.put("from", userid);
//            connected.put("message", "Connected");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//        JSONObject nickname = new JSONObject();
//        try {
//            nickname.put("username", userid);
//            nickname.put("vu", vu);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        G.getInstance();
//        socket.emit("nickname", userid);
//        socket.emit("connected", connected);
//        socket.on("connected", handlerConnected);
//        socket.on("incomingcall", handlercall);


        new File(DIR_RECORDER).mkdirs();
        new File(DIR_DOCUMENT).mkdirs();
        new File(DIR_VIDOE).mkdirs();
        new File(DIR_IMAGE).mkdirs();
        new File(DIR_AUDIO).mkdirs();

        if (Build.VERSION.SDK_INT >= 30) {
            new File(DIR_RECORDERV30).mkdirs();
            new File(DIR_DOCUMENTV30).mkdirs();
            new File(DIR_VIDOEV30).mkdirs();
            new File(DIR_IMAGEV30).mkdirs();
            new File(DIR_AUDIOV30).mkdirs();
        }
        if (isConnected()) {
            isconected = true;

        } else {
            isconected = false;
        }


    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }


    public static G getInstance() {
        return app;
    }

    public boolean isConnected() {
        ConnectivityManager connect = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connect != null) {
            NetworkInfo[] information = connect.getAllNetworkInfo();
            if (information != null) {
                for (int x = 0; x < information.length; x++) {
                    if (information[x].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setConnectivityListener(MyReceiver.ConnectivityReceiverListener listener) {
        MyReceiver.connectivityReceiverListener = listener;
    }
    //    public Emitter.Listener handlerConnected = new Emitter.Listener() {
//
//        @Override
//        public void call(final Object... args) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject jsonObject = (JSONObject) args[0];
//                    String connect = "";
//
//                    try {
//                        connect = jsonObject.getString("message").toString();
//
//
//                        ActivityMain.txtconnection.setText(connect);
//
////                            txtIsTyping.setText("");
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//        }
//    };


//    public Emitter.Listener handlercall = new Emitter.Listener() {
//
//        @Override
//        public void call(final Object... args) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject jsonObject = (JSONObject) args[0];
//                    String connect = "";
//                    String room = "";
//                    String id = "";
//                    String type = "";
//                    String from = "";
//                    String to = "";
//                    int timer = 0;
//                    try {
////                        Log.e("ejrashodam","gggggggggg");
//                        id = jsonObject.getString("id").toString();
//                        room = jsonObject.getString("room").toString();
//                        type = jsonObject.getString("type").toString();
//                        from = jsonObject.getString("from").toString();
//                        to = jsonObject.getString("to").toString();
//                        timer = Integer.parseInt(jsonObject.getString("timer"));
//
//                        Intent intent = new Intent(G.this, ActivityCall.class);
////                                    intent.putExtra("imgurl", imgurl);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("id", id);
//                        intent.putExtra("from", from);
//                        intent.putExtra("to", to);
//                        intent.putExtra("room", room);
//                        intent.putExtra("type", type);
//                        intent.putExtra("timer", timer);
//                        intent.putExtra("call", "in");
//                        G.this.startActivity(intent);
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//        }
//    };

}
