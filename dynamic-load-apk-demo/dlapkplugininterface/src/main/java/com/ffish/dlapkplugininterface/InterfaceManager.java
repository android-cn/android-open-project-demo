package com.ffish.dlapkplugininterface;

/**
 * Created by FFish on 2015/2/12.
 */
public class InterfaceManager {
    private static InterfaceManager sInstance;
    private HostInterface mHostInterface;

    private InterfaceManager(){}

    public static InterfaceManager getInstance(){
        if (sInstance == null){
            sInstance = new InterfaceManager();
        }
        return sInstance;
    }

    public void setHostInterface(HostInterface hostInterface){
        mHostInterface = hostInterface;
    }

    public HostInterface getHostInterface(){
        return mHostInterface;
    }
}
