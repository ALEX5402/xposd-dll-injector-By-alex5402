package com.xposed.opensource;

import android.content.Context;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedLogic implements IXposedHookLoadPackage {

        @Override
        public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
           if(lpparam.packageName.equals("com.innersloth.spacemafia")){
               try{

                   System.loadLibrary("example"); // injection done
                   XposedBridge.log("injection done");
               }catch (Exception e){
                   e.printStackTrace();
                   XposedBridge.log("Lib injection failed");
                   XposedBridge.log(e);
               }
           }
           else
           {
               XposedBridge.log("Process is not running");
           }

           if(lpparam.packageName.equals("com.tencent.ig")){
               try{

                   System.loadLibrary("example2"); // injection done
                   XposedBridge.log("injection done");
               }catch (Exception e){
                   e.printStackTrace();
                   XposedBridge.log("Lib injectio failed");
                   XposedBridge.log(e);
               }

           }
           //  Hook the context of the apk for the toast if apk crash then remove this code
               XposedHelpers.findAndHookMethod("android.content.ContextWrapper", lpparam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    // Access the Context object and modify its behavior
                    Context context = (Context) param.args[0];
                    Toast.makeText(context, "Injection Started", Toast.LENGTH_SHORT).show();

                }
            });
        }



}
