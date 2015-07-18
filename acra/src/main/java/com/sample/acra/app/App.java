package com.sample.acra.app;

import android.app.Application;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by vincent on 2015/7/18 0018.

 @ReportsCrashes(formKey = "", // will not be used
 formUri = "http://yourserver.com/yourscript",
 formUriBasicAuthLogin = "yourlogin", // optional
 formUriBasicAuthPassword = "y0uRpa$$w0rd", // optional
 httpMethod = org.acra.sender.HttpSender.Method.PUT,
 mode = ReportingInteractionMode.TOAST,
 resToastText = R.string.crash_toast_text)
 *
  *
 * Default fields included in email reports are
 ReportField.USER_COMMENT
 ReportField.ANDROID_VERSION
 ReportField.APP_VERSION_NAME
 ReportField.BRAND
 ReportField.PHONE_MODEL
 ReportField.CUSTOM_DATA
 ReportField.STACK_TRACE
 */

@ReportsCrashes(
    formUri = "http://yourserver.com/yourscript",
    httpMethod = org.acra.sender.HttpSender.Method.POST,
    reportType = org.acra.sender.HttpSender.Type.JSON,
    customReportContent = {
        ReportField.PACKAGE_NAME,
        ReportField.APP_VERSION_CODE,
        ReportField.APP_VERSION_NAME,
        //ReportField.PHONE_MODEL,
        ReportField.CUSTOM_DATA,
        ReportField.STACK_TRACE,
        ReportField.LOGCAT
    }
    )
public class App extends Application {
    @Override
    public void onCreate() {
        ACRA.init(this);
        IntentReportSender yourSender = new IntentReportSender(getApplicationContext());
        ACRA.getErrorReporter().setReportSender(yourSender);

        super.onCreate();
    }
}
