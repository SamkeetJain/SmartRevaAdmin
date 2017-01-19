package com.samkeet.smartrevaadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Blazzing Frost on 1/12/2017.
 */

public class Constants {

    public static class URLs {
        public static String BASE = "http://revacounselling.16mb.com/";
        public static String ALUMNI_BASE = "http://revacounselling.16mb.com/alumni/";
        public static String ALUMNI_BLOG = "ADM_alumni_discussion.php";
        public static String ALUMNI_EVENTS = "ADM_alumni_events.php";
        public static String ALUMNI_JOB_REFER = "ADM_alumni_job_refer.php";
        public static String ALUMNI_MEMBER_REQUEST = "ADM_alumni_member_request.php";
        public static String ALUMNI_VIEW_PROFILE = "ADM_alumni_view_profile.php";
        public static String ADMIN_LOGIN = "ADM_login.php";
        public static String PLACEMENT_DRIVE = "ADM_placemnts_drives.php";
        public static String PLACEMENT_UG = "ADM_placement_ug.php";
        public static String PLACEMENT_PG = "ADM_placement_pg.php";
        public static String PLACEMENT_BACKLOG = "ADM_placement_backlogs.php";
        public static String PLACEMENT_ACADEMIC_PROFILE = "ADM_placement_academic_profile.php";
        public static String PLACEMENT_DRIVE_MANAGER = "ADM_placement_drive_manager.php";

    }

    public static class SharedPreferenceData {

        public static SharedPreferences sharedPreferences = null;
        public static SharedPreferences.Editor editor = null;

        public static String SHAREDPREFERENCES = "SmartReva";
        public static String IS_LOGGED_IN = "isloggedin";
        public static String USER_ID = "user_ID";
        public static String TOKEN = "token";
        public static String IS_ALUMNI = "alumni";

        public static void initSharedPreferenceData(SharedPreferences sharedPreferences1) {
            sharedPreferences = sharedPreferences1;
            editor = sharedPreferences.edit();
        }

        public static boolean isSharedPreferenceInited() {
            if (sharedPreferences != null)
                return true;
            return false;
        }

        public static String getIsLoggedIn() {
            return sharedPreferences.getString(IS_LOGGED_IN, "NOT_AVALIBLE");
        }

        public static String getUserId() {
            return sharedPreferences.getString(USER_ID, "NOT_AVALIBLE");
        }

        public static String getTOKEN() {
            return sharedPreferences.getString(TOKEN, "NOT_AVALIBLE");
        }

        public static String getIsAlumni() {
            return sharedPreferences.getString(IS_ALUMNI, "NOT_AVALIBLE");
        }

        public static void setIsAlumni(String isAlumni) {
            editor.putString(IS_ALUMNI, "yes");
            editor.apply();
        }

        public static void setIsLoggedIn(String isLoggedIn) {
            editor.putString(IS_LOGGED_IN, "yes");
            editor.apply();
        }

        public static void setTOKEN(String token) {
            editor.putString(TOKEN, token);
            editor.apply();
        }

        public static void setUserId(String userId) {
            editor.putString(USER_ID, userId);
            editor.apply();
        }

        public static void clearData() {
            editor.clear();
            editor.apply();

        }
    }

    public static class Methods {

        public static boolean networkState(Context context, ConnectivityManager comman) {
            boolean wifi = comman.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
            boolean data = comman.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();

            if (wifi || data) {
                return true;
            } else {
                Toast toast = Toast.makeText(context, "No Internet Connnection!!! Try Again", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
        }

        public static boolean checkForSpecial(String s) {
            Pattern p = Pattern.compile("[^A-Za-z0-9 -.@]");
            Matcher m = p.matcher(s);
            boolean b = m.find();
            return b;
        }
    }

}
