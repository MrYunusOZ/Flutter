package tr.com.yunusoz.flutter.funcs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FuncsPlugin */
public class FuncsPlugin implements MethodCallHandler {
  private Context context;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "funcs");
    channel.setMethodCallHandler(new FuncsPlugin(registrar));
  }

  private FuncsPlugin(Registrar registrar) {
    context = ((registrar.activity() != null) ? registrar.activity() : registrar.context());
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    try {
      switch (call.method) {
      case "Exit": {
        Activity act = (Activity) context;
        act.moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
      }
        break;
      case "MD5": {
        try {
          String str = call.argument("str");
          MessageDigest digest = MessageDigest.getInstance("MD5");
          digest.update(str.getBytes());
          byte messageDigest[] = digest.digest();
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < messageDigest.length; i++) {
            sb.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
          }
          result.success(sb.toString());
        } catch (NoSuchAlgorithmException e) {
          result.error("", e.getMessage(), null);
        }
      }
        break;
      case "PressTheHomeButton": {
        Intent startMainButton = new Intent(Intent.ACTION_MAIN);
        startMainButton.addCategory(Intent.CATEGORY_HOME);
        startMainButton.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMainButton);
      }
        break;
      case "RestartApp": {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
          Activity act = (Activity) context;
          act.recreate();
        }
      }
        break;
      case "SHA512": {
        try {
          String str = call.argument("str");
          MessageDigest digest = MessageDigest.getInstance("SHA-512");
          digest.update(str.getBytes());
          byte messageDigest[] = digest.digest();
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < messageDigest.length; i++) {
            sb.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
          }
          result.success(sb.toString());
        } catch (NoSuchAlgorithmException e) {
          result.error("", e.getMessage(), null);
        }
      }
        break;
      default:

        break;
      }
      result.success(null);
    } catch (IllegalArgumentException e) {
      result.notImplemented();
    }
  }
}
