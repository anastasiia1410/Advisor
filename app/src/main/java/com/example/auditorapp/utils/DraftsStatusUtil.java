package com.example.auditorapp.utils;


import android.view.View;
import android.widget.TextView;

import com.example.auditorapp.entity.DraftStatus;
import com.example.auditorapp.entity.drafts.Drafts;

public class DraftsStatusUtil {

    public static void showDraftsStatus(Drafts drafts, TextView send, TextView sending, TextView sent){

        if(drafts.getStatus() == DraftStatus.ReadyToSend){
            send.setVisibility(View.VISIBLE);
            sent.setVisibility(View.GONE);
            sending.setVisibility(View.GONE);
        }
        if(drafts.getStatus() == DraftStatus.Sending){
            sending.setVisibility(View.VISIBLE);
            send.setVisibility(View.GONE);
            sent.setVisibility(View.GONE);
        }
        if(drafts.getStatus() == DraftStatus.Sent){
            sent.setVisibility(View.VISIBLE);
            sending.setVisibility(View.GONE);
            send.setVisibility(View.GONE);
        }

    }

}
