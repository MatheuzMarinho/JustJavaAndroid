package com.example.android.justjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Hellow on 10/11/2016.
 */
public class BootReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, ServicoTeste.class);
        context.startService(myIntent);
    }
}
