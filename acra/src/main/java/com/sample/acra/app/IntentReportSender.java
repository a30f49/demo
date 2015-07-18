package com.sample.acra.app;

import android.content.Context;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.acra.util.JSONReportBuilder;
import org.json.JSONObject;

/**
 * Created by vincent on 2015/7/18 0018.
 */
public class IntentReportSender implements ReportSender {
    public IntentReportSender(Context ctx) {
    }

    @Override
    public void send(Context context, CrashReportData crashReportData) throws ReportSenderException {
        if(crashReportData != null){
            try {
                JSONObject json = crashReportData.toJSON();


            } catch (JSONReportBuilder.JSONReportException e) {
                e.printStackTrace();
            }
        }
    }
}
