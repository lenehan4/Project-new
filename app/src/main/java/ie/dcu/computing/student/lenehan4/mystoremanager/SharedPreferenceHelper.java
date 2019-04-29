package ie.dcu.computing.student.lenehan4.mystoremanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;

import java.util.Calendar;

public class SharedPreferenceHelper {

    private static  SharedPreferenceHelper sharedPreferenceHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String EXTRA_MONTH = "ie.dcu.computing.student.lenehan4.mystoremanager.sharedprefencehelper.extra_month";
    String EXTRA_FCM = "ie.dcu.computing.student.lenehan4.mystoremanager.sharedprefencehelper.extra_fcm";
    String EXTRA_SAVE_COMPANY = "ie.dcu.computing.student.lenehan4.mystoremanager.sharedprefencehelper.extra_company";

    private  SharedPreferenceHelper(){


    }



    private SharedPreferenceHelper(Context context) {

        sharedPreferences = context.getSharedPreferences("Info", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferenceHelper getSharedPreferenceHelper(Context context) {
        if (sharedPreferenceHelper == null) {
            sharedPreferenceHelper = new SharedPreferenceHelper(context);
        }
        return sharedPreferenceHelper;
    }




    public void userSavedVote(){
        int month = Calendar.getInstance().get(Calendar.MONTH);
        editor.putString(EXTRA_MONTH, ""+month);
        editor.apply();
    }

    public int getWhenUserPutVote(){
        return Integer.parseInt(sharedPreferences.getString(EXTRA_MONTH,"0"));
    }


    public void saveCompany(String company){
        editor.putString(EXTRA_SAVE_COMPANY, company);
        editor.apply();
    }
    public String getCompany(){
        return sharedPreferences.getString(EXTRA_SAVE_COMPANY,"");

    }

    public void saveFcm(String fcm) {
        editor.putString(EXTRA_FCM, fcm);
        editor.apply();
    }


    public String getFCM() {
        return sharedPreferences.getString(EXTRA_FCM, "");
    }


}
