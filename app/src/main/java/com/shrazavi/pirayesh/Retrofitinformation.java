package com.shrazavi.pirayesh;


import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.Cash;
import com.shrazavi.pirayesh.DataClass.Date;
import com.shrazavi.pirayesh.DataClass.DayReserve;
import com.shrazavi.pirayesh.DataClass.Help;
import com.shrazavi.pirayesh.DataClass.Holiday;
import com.shrazavi.pirayesh.DataClass.Key;
import com.shrazavi.pirayesh.DataClass.Law;

import com.shrazavi.pirayesh.DataClass.Like;
import com.shrazavi.pirayesh.DataClass.MessageLogin;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Nobat;
import com.shrazavi.pirayesh.DataClass.Ostan;

import com.shrazavi.pirayesh.DataClass.Payment;
import com.shrazavi.pirayesh.DataClass.Price;
import com.shrazavi.pirayesh.DataClass.Rating;

import com.shrazavi.pirayesh.DataClass.Shahrestan;
import com.shrazavi.pirayesh.DataClass.Shift;
import com.shrazavi.pirayesh.DataClass.Ticket;
import com.shrazavi.pirayesh.DataClass.Transaction;
import com.shrazavi.pirayesh.DataClass.User;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Retrofitinformation {

    @FormUrlEncoded
    @POST("/addpost")
    Call<MessageSignup> addpost(@Header("Content") String contenttype,
                                @Field("username") String username,
                                @Field("img") String imgurl,
                                @Field("text") String text);

    @FormUrlEncoded
    @POST("/like")
    Call<MessageSignup> like(@Header("Content") String contenttype,
                             @Field("postid") String postid,
                             @Field("vakile") String vakile,
                             @Field("user") String user,
                             @Field("like") Boolean like,
                             @Field("id") String id);

    @FormUrlEncoded
    @POST("/rating")
    Call<MessageSignup> Rating(@Header("Content") String contenttype,
                               @Field("vakil") String vakil,
                               @Field("rate") int rate,
                               @Field("id") String id);

    @FormUrlEncoded
    @POST("/getrating")
    Call<Rating> getRating(@Field("username") String username);


    @POST("/gettarrif")
    Call<ArrayList<Payment>> getpayment();
//    @FormUrlEncoded
//    @POST("/getrtc")
//    Call<UserInfo> getrtc(@Field("username") String username);
//
//    @FormUrlEncoded
//    @POST("/getcall")
//    Call<CallLog> getcall(@Field("callid") String callid);

    @FormUrlEncoded
    @POST("/getlike")
    Call<Like> getlike(@Header("Content") String contenttype,
                       @Field("postid") String postid,
                       @Field("user") String user);
//
//    @FormUrlEncoded
//    @POST("/getpost")
//    Call<ArrayList<Post>> getpost(@Field("page") int page);

    @FormUrlEncoded
    @POST("/getpostlike")
    Call<ArrayList<Like>> getlikepost(@Field("postid") String postid);

    //    @FormUrlEncoded
//    @POST("/getcalllog")
//    Call<ArrayList<CallLog>> getcalllog(@Header("Content") String contenttype,
//                                        @Field("username") String username,
//                                        @Field("id") String id);
    @FormUrlEncoded
    @POST("/getlaw")
    Call<ArrayList<Law>> getlaw(@Field("type") String type);

    @FormUrlEncoded
    @POST("/gethelp")
    Call<ArrayList<Help>> gethelp(@Field("type") String type);

    @FormUrlEncoded
    @POST("/getuser")
    Call<User> getuser(@Header("Content") String contenttype,
                       @Field("userid") String userid,
                       @Field("id") String id);

    @FormUrlEncoded
    @POST("/getusernumber")
    Call<User> getusernumber(@Header("Content") String contenttype,
                             @Field("number") String userid,
                             @Field("id") String id);

    //    @POST("/sendcode")
//    Call<MessageSignup> sendcode(@Header("Content") String contenttype,
//                       @Field("data") String data);
    @FormUrlEncoded
    @POST("/getbarber")
    Call<Barber> getbarber(@Header("Content") String contenttype,
                           @Field("userid") String userid,
                           @Field("id") String id);

    @FormUrlEncoded
    @POST("/getaccount")
    Call<MessageSignup> getaccount(@Header("Content") String contenttype,
                                   @Field("userid") String userid,
                                   @Field("id") String id);

    @FormUrlEncoded
    @POST("/getbarbers")
    Call<ArrayList<Barber>> getbarbers(@Header("Content") String contenttype,
                                       @Field("ostan") String ostan,
                                       @Field("shahr") String shahr,
                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("/getallbarbers")
    Call<ArrayList<Barber>> getallbarbers(@Header("Content") String contenttype,
                                          @Field("id") String id);
//
//
//    @FormUrlEncoded
//    @POST("/getspecialty")
//    Call<ArrayList<Lawyer>> getspecialty(@Header("Content") String contenttype,
//                                         @Field("specialty") String specialty,
//                                         @Field("id") String id);

    @FormUrlEncoded
    @POST("/getdayreserve")
    Call<ArrayList<DayReserve>> getday(@Header("Content") String contenttype,
                                       @Field("username") String username,
                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("/getreserve")
    Call<ArrayList<Nobat>> getreserve(@Header("Content") String contenttype,
                                      @Field("year") int year,
                                      @Field("month") int month,
                                      @Field("day") int day,
                                      @Field("barberid") String barberid,
                                      @Field("id") String id);

    @FormUrlEncoded
    @POST("/getshift")
    Call<ArrayList<Shift>> getshift(@Header("Content") String contenttype,
                                    @Field("userid") String username,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("/getnobat")
    Call<ArrayList<Nobat>> getnobat(@Header("Content") String contenttype,
                                    @Field("barberid") String barberid,
                                    @Field("year") int year,
                                    @Field("month") int month,
                                    @Field("day") int day,
                                    @Field("time") String time,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("/getnobatuser")
    Call<ArrayList<Nobat>> getnobatuser(@Header("Content") String contenttype,
                                        @Field("userid") String barberid,
                                        @Field("name") String name,
                                        @Field("number") String number,
                                        @Field("id") String id);

    @POST("/getprice")
    Call<Price> getprice();

    @POST("/getdate")
    Call<Date> getdate();

    @POST("getostan.php")
    Call<ArrayList<Ostan>> getostan();

    @FormUrlEncoded
    @POST("getshahr.php")
    Call<ArrayList<Shahrestan>> getshahr(@Field("id") int id_ostan);

    @FormUrlEncoded
    @POST("getidshahr.php")
    Call<Shahrestan> getidshahr(@Field("name") String name_shahr);


    @FormUrlEncoded
    @POST("getnameostan.php")
    Call<Ostan> getnameostan(@Field("id") int id);

    @FormUrlEncoded
    @POST("/changepassword")
    Call<MessageSignup> changepassword(@Header("Content") String contenttype,
                                       @Field("username") String username,
                                       @Field("oldpass") String oldpass,
                                       @Field("newpass") String newpass,
                                       @Field("vu") String vu,
                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("/insertdayoff")
    Call<MessageSignup> insertdayoff(@Header("Content") String contenttype,
                                     @Field("userid") String userid,
                                     @Field("dayoff") int dayoff,
                                     @Field("id") String id);

    @FormUrlEncoded
    @POST("/checkuser")
    Call<MessageSignup> cheackuser(@Header("Content") String contenttype,
                                   @Field("melli") String melli,
                                   @Field("vu") String vu,
                                   @Field("username") String username);

    @FormUrlEncoded
    @POST("/signupvl")
    Call<MessageSignup> signupvl(@Header("Content") String contenttype,
                                 @Field("data") String data);

    @FormUrlEncoded
    @POST("/signupuser")
    Call<MessageSignup> signupuser(@Header("Content") String contenttype,
                                   @Field("data") String data);

    @FormUrlEncoded
    @POST("/upgradeprofilebr")
    Call<MessageSignup> upgradeprofilebr(@Header("Content") String contenttype,
                                         @Field("username") String username,
                                         @Field("name") String name,
                                         @Field("experience") String experience,
                                         @Field("title") String cart,
                                         @Field("email") String email,
                                         @Field("bio") String bio,
                                         @Field("id") String id);

    @FormUrlEncoded
    @POST("/upgradelocation")
    Call<MessageSignup> upgradelocation(@Header("Content") String contenttype,
                                        @Field("username") String username,
                                        @Field("ostan") String ostan,
                                        @Field("shahr") String shahr,
                                        @Field("address") String address,
                                        @Field("id") String id);

    @FormUrlEncoded
    @POST("/upgradetariff")
    Call<MessageSignup> upgradetariff(@Header("Content") String contenttype,
                                      @Field("username") String username,
                                      @Field("chattariff") int chattariff,
                                      @Field("voisetariff") int voisetariff,
                                      @Field("videotariff") int videotariff,
                                      @Field("id") String id);

    @FormUrlEncoded
    @POST("/upgradeblack")
    Call<MessageSignup> upgradeblack(@Header("Content") String contenttype,
                                     @Field("username") String username,
                                     @Field("blacklist") String blacklist,
                                     @Field("id") String id);

    @FormUrlEncoded
    @POST("/upgradeprofileuser")
    Call<MessageSignup> upgradeprofileuser(@Header("Content") String contenttype,
                                           @Field("username") String username,
                                           @Field("name") String name,
                                           @Field("email") String email,
                                           @Field("id") String id);

    @FormUrlEncoded
    @POST("/checkaccount")
    Call<Cash> checkaccount(@Header("Content") String contenttype,
                            @Field("username") String username,
                            @Field("ia") Boolean ia,
                            @Field("ic") Boolean ic,
                            @Field("uservl") String uservl);

    @FormUrlEncoded
    @POST("/increaseaccount")
    Call<Cash> increaseaccount(@Header("Content") String contenttype,
                               @Field("username") String username,
                               @Field("price") Long price,
                               @Field("code") String code,
                               @Field("detail") String detail,
                               @Field("id") String id);

    @FormUrlEncoded
    @POST("/settimebr")
    Call<MessageSignup> settimebr(@Header("Content") String contenttype,
                                  @Field("userid") String userid,
                                  @Field("shift") int shift,
                                  @Field("barbers") int barbers,
                                  @Field("timework") int timework,
                                  @Field("vip") int vip,
                                  @Field("holiday") int holiday,
                                  @Field("dayoff") int dayoff,
                                  @Field("visitdate") ArrayList<String> visitdate,
                                  @Field("activedate") String activedate,
                                  @Field("id") String id);

    @FormUrlEncoded
    @POST("/setname")
    Call<MessageSignup> setname(@Header("Content") String contenttype,
                                @Field("name") String name,
                                @Field("bu") String bu,
                                @Field("number") String number,
                                @Field("id") String id);
    @FormUrlEncoded
    @POST("/setlaw")
    Call<MessageSignup> setlaw(@Header("Content") String contenttype,
                                @Field("bu") String bu,
                                @Field("number") String number,
                                @Field("id") String id);
    @FormUrlEncoded
    @POST("/upgradetime")
    Call<MessageSignup> upgradetime(@Header("Content") String contenttype,
                                    @Field("username") String username,
                                    @Field("arraytime") ArrayList<String> arraytime,
                                    @Field("day") String day,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("/insertreserve")
    Call<MessageSignup> insertreserve(@Header("Content") String contenttype,
                                      @Field("userid") String userid,
                                      @Field("name") String name,
                                      @Field("number") String number,
                                      @Field("barberid") String barberid,
                                      @Field("year") int year,
                                      @Field("month") int month,
                                      @Field("day") int day,
                                      @Field("time") String time,
                                      @Field("vip") int vip,
                                      @Field("id") String id);

    @FormUrlEncoded
    @POST("/insertshift")
    Call<MessageSignup> insertshift(@Header("Content") String contenttype,
                                    @Field("userid") String userid,
                                    @Field("h1") int h1,
                                    @Field("h2") int h2,
                                    @Field("m1") int m1,
                                    @Field("m2") int m2,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("/insertvacation")
    Call<MessageSignup> insertvacation(@Header("Content") String contenttype,
                                       @Field("userid") String userid,
                                       @Field("yearv") int yearv,
                                       @Field("monthv") int monthv,
                                       @Field("datev") int datev,
                                       @Field("yearn") int yearn,
                                       @Field("monthn") int monthn,
                                       @Field("daten") int daten,
                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("/cancelvacation")
    Call<MessageSignup> cancelvacation(@Header("Content") String contenttype,
                                       @Field("userid") String userid,
                                       @Field("yearv") int yearv,
                                       @Field("monthv") int monthv,
                                       @Field("datev") int datev,
                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("/upgradeshift")
    Call<MessageSignup> upgradeshift(@Header("Content") String contenttype,
                                     @Field("userid") String userid,
                                     @Field("shiftid") String shiftid,
                                     @Field("h1") int h1,
                                     @Field("h2") int h2,
                                     @Field("m1") int m1,
                                     @Field("m2") int m2,
                                     @Field("id") String id);

    @FormUrlEncoded
    @POST("/deleteshift")
    Call<MessageSignup> deleteshift(@Header("Content") String contenttype,
                                    @Field("userid") String userid,
                                    @Field("shiftid") String shiftid,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("/deletenobat")
    Call<MessageSignup> deletenobat(@Header("Content") String contenttype,
                                    @Field("barberid") String barberid,
                                    @Field("number") String number,
                                    @Field("year") int year,
                                    @Field("month") int month,
                                    @Field("day") int day,
                                    @Field("time") String time,
                                    @Field("id") String id);
//    @FormUrlEncoded
//    @POST("/upgradeincome")
//    Call<Cash> upgradeincome(@Header("Content") String contenttype,
//                             @Field("username") String username,
//                             @Field("price") Long price,
//                             @Field("id") String id);

    @FormUrlEncoded
    @POST("/sendcode")
    Call<MessageSignup> sendcode(@Header("Content") String contenttype,
                                 @Field("data") String data,
                                 @Field("D1") String D1,
                                 @Field("D2") String D2,
                                 @Field("D3") String D3,
                                 @Field("D4") String D4,
                                 @Field("D5") String D5);

    @FormUrlEncoded
    @POST("/loginvl")
    Call<MessageLogin> loginvl(@Header("Content") String contenttype,
                               @Field("data") String data,
                               @Field("D1") String D1,
                               @Field("D2") String D2,
                               @Field("D3") String D3,
                               @Field("D4") String D4,
                               @Field("D5") String D5);

    @FormUrlEncoded
    @POST("/loginuser")
    Call<MessageLogin> loginuser(@Header("Content") String contenttype,
                                 @Field("data") String data,
                                 @Field("D1") String D1,
                                 @Field("D2") String D2,
                                 @Field("D3") String D3,
                                 @Field("D4") String D4,
                                 @Field("D5") String D5);
//
//    @FormUrlEncoded
//    @POST("/creatroom")
//    Call<Room> creatroom(@Header("Content") String contenttype,
//                         @Field("userA") String userA,
//                         @Field("userB") String userB,
//                         @Field("key") String key,
//                         @Field("id") String id);

    @FormUrlEncoded
    @POST("/getkey")
    Call<Key> getkey(@Header("Content") String contenttype,
                     @Field("roomid") String roomid,
                     @Field("id") String id);

    @FormUrlEncoded
    @POST("/getaccesschat")
    Call<MessageSignup> getaccesschat(@Header("Content") String contenttype,
                                      @Field("roomid") String roomid,
                                      @Field("id") String id);

    @FormUrlEncoded
    @POST("/insertticket")
    Call<MessageSignup> insertticket(@Header("Content") String contenttype,
                                     @Field("user") String user,
                                     @Field("bu") String bu,
                                     @Field("su") String su,
                                     @Field("type") String type,
                                     @Field("title") String title,
                                     @Field("desc") String desc,
                                     @Field("key") String key,
                                     @Field("id") String id);

    @FormUrlEncoded
    @POST("/insertreport")
    Call<MessageSignup> insertreport(@Header("Content") String contenttype,
                                     @Field("from") String from,
                                     @Field("to") String to,
                                     @Field("vu") String vu,
                                     @Field("desc") String desc,
                                     @Field("callid") String callid,
                                     @Field("id") String id);

    @FormUrlEncoded
    @POST("/getticket")
    Call<ArrayList<Ticket>> getticket(@Header("Content") String contenttype,
                                      @Field("user") String user,
                                      @Field("id") String id);

    @FormUrlEncoded
    @POST("/getholiday")
    Call<ArrayList<Holiday>> getholiday(@Header("Content") String contenttype,
                                        @Field("id") String id);
//    @FormUrlEncoded
//    @POST("/getroomvl")
//    Call<ArrayList<Room>> getroomvl(@Field("userB") String userB);
//
//    @FormUrlEncoded
//    @POST("/getroomuser")
//    Call<ArrayList<Room>> getroomuser(@Field("userA") String userA);

    @FormUrlEncoded
    @POST("/index.php")
    Call validation(@Field("number") String number);

    @FormUrlEncoded
    @POST("/addtransaction")
    Call<MessageSignup> AddTransaction(@Header("Content") String contenttype,
                                       @Field("user") String userid,
                                       @Field("name") String name,
                                       @Field("code") int code,
                                       @Field("price") String price,
                                       @Field("status") String status,
                                       @Field("detail") String detail,
                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("/uptransaction")
    Call<MessageSignup> UpTransaction(@Header("Content") String contenttype,
                                      @Field("id") String id,
                                      @Field("idtrans") String idtrans);

    @FormUrlEncoded
    @POST("/checktimer")
    Call<MessageSignup> checktimer(@Header("Content") String contenttype,
                                   @Field("time") long time,
                                   @Field("id") String id);

    @FormUrlEncoded
    @POST("/gettransaction")
    Call<ArrayList<Transaction>> getTransaction(@Header("Content") String contenttype,
                                                @Field("username") String username);


}


