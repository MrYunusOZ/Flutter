import 'dart:async';

import 'package:flutter/services.dart';

class Funcs {
  static const MethodChannel _channel = const MethodChannel('funcs');

static void Exit() async {
    await _channel.invokeMethod('Exit');
  }

  static Future<String> MD5(String str) async {
    if (str == "") {
      return "";
    } else {
      String md5 = await _channel.invokeMethod('MD5', {"str": str});
      return md5;
    }
  }

  static void PressTheHomeButton() async {
    await _channel.invokeMethod('PressTheHomeButton');
  }

  static void RestartApp() async {
    await _channel.invokeMethod('RestartApp');
  }

  static Future<String> SHA512(String str) async {
    if (str == "") {
      return "";
    } else {
      String sha512 = await _channel.invokeMethod('SHA512', {"str": str});
      return sha512;
    }
  }
}
