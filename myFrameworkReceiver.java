    private final BroadcastReceiver myFrameworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(GeneralString.TAG, "myFrameworkReceiver, onReceive");

            if (intent.getAction().equals(GeneralString.Intent_SEND_KEY_EVENT)) {
                Log.i(GeneralString.TAG, "Intent_SEND_KEY_EVENT");
                // add v0.0.0.31
                // chuying add fix jira:NTSA-768
                String enabledAccessibilityServices = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                Log.i(GeneralString.TAG, "enabledAccessibilityServices -> " + enabledAccessibilityServices);

                if (mDeviceVersion.contains("11")) {
                    if (enabledAccessibilityServices == null || enabledAccessibilityServices.isEmpty()) {
                        access_flag = false;
                    } else {
                        access_flag = true;
                    }
                }
                Log.i(GeneralString.TAG, "access_flag -> " + access_flag);

                // add v0.0.0.9a ++
                // get TouchID and RingtoneID
                if (touchID == 0 || RingtoneID == 0) {
                    // GetIDType();
                    InitDefaultInputDevice();
                }

                String sDataStr = "";
                Bundle Getbundle = intent.getExtras();
                // 取得KeyEvent Object
                KeyEvent kv = (KeyEvent) Getbundle.getParcelable(GeneralString.KEYCODE_DATA);

                sDataStr = String.valueOf(kv.getKeyCode());
                sGetDataStr = String.valueOf(kv.getKeyCode());

                final boolean Key_down = kv.getAction() == KeyEvent.ACTION_DOWN;

                Log.i(GeneralString.TAG, "KEYCODE_DATA = " + sDataStr + ", (" + kv.getAction() + ") , device id = " + kv.getDeviceId());

                // if device id =-1 and device id not default bypass (v0.0.0.20)
                if (kv.getDeviceId() == -1 || !IsDefaultInputDevice(kv.getDeviceId())) {
                    // chuying add
                    if (access_flag) {
                        Log.i(GeneralString.TAG, "Android R and AccessibilityServices Start");
                        // fix In App Lock, soft key disable not work issue
                        Boolean key_enable = true;
                        String Key_code;
                        if (Integer.parseInt(sDataStr) == KeyEvent.KEYCODE_BACK) {
                            key_enable = mSettings.getBoolean("SOFT_KEY_ENABLE_BACK", true);
                            Key_code = mSettings.getString("SOFT_KEY_VALUE_BACK", "0");
                            if (key_enable) {
                                if (!sDataStr.equals(Key_code)) {
                                    CPMapping(Key_code, context, kv, kv.getMetaState());
                                } else {
                                    Intent RTintent = new Intent(GeneralString.Intent_RECEIVE_KEY_EVENT);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(GeneralString.KEYCODE_NUMBER, 1);
                                    bundle.putParcelable(GeneralString.KEYCODE_DATA, null);
                                    RTintent.putExtras(bundle);
                                    context.sendBroadcast(RTintent);
                                    return;
                                }
                            }
                        } else if (Integer.parseInt(sDataStr) == KeyEvent.KEYCODE_HOME) {
                            key_enable = mSettings.getBoolean("SOFT_KEY_ENABLE_HOME", true);
                            Key_code = mSettings.getString("SOFT_KEY_VALUE_HOME", "0");
                            Log.i(GeneralString.TAG, "Key_code = " + Key_code);
                            if (key_enable) {
                                if (!sDataStr.equals(Key_code)) {
                                    CPMapping(Key_code, context, kv, kv.getMetaState());
                                } else {
                                    Intent RTintent = new Intent(GeneralString.Intent_RECEIVE_KEY_EVENT);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(GeneralString.KEYCODE_NUMBER, 1);
                                    bundle.putParcelable(GeneralString.KEYCODE_DATA, null);
                                    RTintent.putExtras(bundle);
                                    context.sendBroadcast(RTintent);
                                    return;
                                }
                            }
                        } else if (Integer.parseInt(sDataStr) == KeyEvent.KEYCODE_APP_SWITCH) {
                            key_enable = mSettings.getBoolean("SOFT_KEY_ENABLE_APP_SWITCH", true);
                        }

                        if (!key_enable) {
                            CPMapping("0", context, kv, 0);
                        }

                    } else if (sDataStr.equals("503") || sDataStr.equals("504") || sDataStr.equals("506")) {
                        Log.i(GeneralString.TAG, "KEYCODE_DATA = " + sDataStr + ", (" + kv.getAction() + ") , device id = " + kv.getDeviceId() + " and bypass");
                    } else {
                        Intent RTintent = new Intent(GeneralString.Intent_RECEIVE_KEY_EVENT);
                        Bundle bundle = new Bundle();
                        bundle.putInt(GeneralString.KEYCODE_NUMBER, 1);
                        bundle.putParcelable(GeneralString.KEYCODE_DATA, null);
                        RTintent.putExtras(bundle);
                        context.sendBroadcast(RTintent);
                        return;
                    }
                }

                // add v0.0.0.9 ++
                /*
                 * Three Scan key and V+ , V- not support Button Ringtones Scan : DeviceId = 7 ;
                 * 6 Left and Right Scan : DeviceId = 8 ; 7 V+ : DeviceId = 8 ; 7 V- : DeviceId
                 * = 6 ; 5 other key : DeviceId = 7 ; 6 Touch key : DeviceId = 5 ; 3
                 */
                if (kv.getDeviceId() == RingtoneID && kv.getKeyCode() != 505) {
                    if (kv.getAction() == KeyEvent.ACTION_DOWN) {
                        if (DeviceInfo.GetModel().equals(DeviceInfo.mRK26)) {
                            Log.i(GeneralString.TAG, "RingtoneID ACTION_DOWN");
                            PlaySound(mContext, sDataStr);
                        } else {
                            PlaySound(mContext);
                        }
                    }
                }

                // HotKey
                if (Key_down) {
                    boolean re_value = HotKey(context, sDataStr);
                    if (re_value) {
                        ReceiveKeyEvent(context, kv, "-1", kv.getMetaState(), false);
                        return;
                    }
                }

                // PAE-123 : ChangeEXMappingKey (New Key Table)
                if (!TestMode) {
                    // v0.0.0.5 Touch key (HOME)
                    if (!Istouch_home(sDataStr)) {
                        ReceiveKeyEvent(context, kv, "-1", kv.getMetaState(), false);
                        Log.i(GeneralString.TAG, "Touch home key disable (1)");
                        return;
                    }

                    // bypass 4,3,187
                    Bypass = IsBypass(sDataStr, kv.getDeviceId());
                    if (Bypass) {
                        Intent RTintent = new Intent(GeneralString.Intent_RECEIVE_KEY_EVENT);
                        Bundle bundle = new Bundle();
                        bundle.putInt(GeneralString.KEYCODE_NUMBER, 1);
                        bundle.putParcelable(GeneralString.KEYCODE_DATA, null);
                        RTintent.putExtras(bundle);
                        context.sendBroadcast(RTintent);
                        return;
                    }

                    // v0.0.0.9 ++
                    Boolean TK = IsTouchKey(sDataStr, kv.getDeviceId());
                    if (TK) {
                        String mKEY_VALUE = null;
                        String mKEY_ENABLE = null;
                        String result_keycode;
                        Boolean Back_Key = false, Home_key = false;
                        if (Integer.parseInt(sDataStr) == KeyEvent.KEYCODE_BACK) {
                            mKEY_VALUE = "SOFT_KEY_VALUE_BACK";
                            mKEY_ENABLE = "SOFT_KEY_ENABLE_BACK";
                            Back_Key = true;
                        } else if (Integer.parseInt(sDataStr) == KeyEvent.KEYCODE_HOME) {
                            mKEY_VALUE = "SOFT_KEY_VALUE_HOME";
                            mKEY_ENABLE = "SOFT_KEY_ENABLE_HOME";
                            Home_key = true;
                        } else if (Integer.parseInt(sDataStr) == KeyEvent.KEYCODE_APP_SWITCH) {
                            mKEY_VALUE = "SOFT_KEY_VALUE_APP_SWITCH";
                            mKEY_ENABLE = "SOFT_KEY_ENABLE_APP_SWITCH";
                        }

                        // 取得SharedPreferences鍵值，第一個參數是key，第二個是預設值，若沒有值的話
                        String Key_code = mSettings.getString(mKEY_VALUE, "0");
                        Boolean key_enable = mSettings.getBoolean(mKEY_ENABLE, true);

                        result_keycode = Key_code;
                        Log.i(GeneralString.TAG, "Button Assignment >> PREF_KEY_VALUE_ARRAY = " + mKEY_VALUE
                                + " , Key_code = " + Key_code);
                        Log.i(GeneralString.TAG, "Button Assignment >> PREF_KEY_ENABLE_ARRAY = " + mKEY_ENABLE
                                + " , Key_enable = " + key_enable);

                        if (!key_enable) {
                            result_keycode = "0";
                            Log.e(GeneralString.TAG, "Button Assignment >> This key is disable. ");
                        }

                        if (Back_Key && result_keycode.equals(String.valueOf(KeyEvent.KEYCODE_BACK))) {
                            Intent RTintent = new Intent(GeneralString.Intent_RECEIVE_KEY_EVENT);
                            Bundle bundle = new Bundle();
                            bundle.putInt(GeneralString.KEYCODE_NUMBER, 1);
                            bundle.putParcelable(GeneralString.KEYCODE_DATA, null);
                            RTintent.putExtras(bundle);
                            context.sendBroadcast(RTintent);
                            return;
                        }
                        // 因長壓soft Home key會開啟google search，故未轉換時跑原來的流程
                        else if (Home_key && result_keycode.equals(String.valueOf(KeyEvent.KEYCODE_HOME))) {
                            Intent RTintent = new Intent(GeneralString.Intent_RECEIVE_KEY_EVENT);
                            Bundle bundle = new Bundle();
                            bundle.putInt(GeneralString.KEYCODE_NUMBER, 1);
                            bundle.putParcelable(GeneralString.KEYCODE_DATA, null);
                            RTintent.putExtras(bundle);
                            context.sendBroadcast(RTintent);
                            return;
                        }

                        // 在Button Assignment中被換成 KEYCODE_BACK或KEYCODE_ESCAPE
                        if (!sDataStr.equals(Key_code)) {
                            // Log.e(GeneralString.TAG, "Button Assignment = " +
                            // Key_code );
                            if (Key_code.equals(Integer.toString(KeyEvent.KEYCODE_BACK)))
                                ButtonAssignment_BACK = true;
                        }

                        CPMapping(result_keycode, context, kv, kv.getMetaState());
                        return;
                    }
                }

                // China RK26 controlled by driver and no need to transform
                if (!TestMode && keytype == 29 && DeviceInfo.GetModel().equals(DeviceInfo.mRK25)) {
                    sDataStr = SpecialKeyChina(sDataStr);
                    Log.d(GeneralString.TAG, "KEYCODE_DATA Change China Key= " + sDataStr);
                } else if (!TestMode && keytype == 32 && DeviceInfo.GetModel().equals(DeviceInfo.mRK25)) {
                    // EU RK26 controlled by driver and no need to transform
                    sDataStr = SpecialKeyEU(sDataStr);
                    Log.d(GeneralString.TAG, "KEYCODE_DATA Change EU Key= " + sDataStr);
                }

                if (TestMode) {
                    // Test Mode Enable
                    Log.i(GeneralString.TAG, "onReceive -> Intent_SEND_KEY_EVENT (TestMode ON)");
                    ReceiveKeyEvent(context, kv, sDataStr, kv.getMetaState(), false);
                } else {
                    // Test Mode Disable

                    if (State_Lock) {
                        // Key Lock

                        if (sDataStr.equals(Key_Scan) || sDataStr.equals(Key_ScanLeft) || sDataStr.equals(Key_ScanRight)) {
                            // if key is scan key bypass
                            String MappingValue = GetMappingKeyValue(sDataStr, 0);
                            Log.i(GeneralString.TAG, "MappingValue = " + MappingValue);

                            // Button Assignment is no change scan key bypass
                            if (sDataStr.equals(MappingValue)) {
                                ReceiveKeyEvent(context, kv, sDataStr, kv.getMetaState(), false);
                            } else {
                                ReceiveKeyEvent(context, kv, "-1", kv.getMetaState(), false);
                            }

                        } else if (sDataStr.equals(Key_ScanPistol)) {
                            // if key is scan pistol key bypass
                            ReceiveKeyEvent(context, kv, sDataStr, kv.getMetaState(), false);
                        } else {
                            // Mode 0: Alpha, Mode 1: Fn, Mode 2: Shift, Mode 3: Ctrl, Mode 4= Alt
                            int CPKeyMode = IsCPKeyMode(Integer.valueOf(sDataStr));

                            // Shift + Backspace (China,EU)
                            if (CPKeyMode == 2 && Key_down) {
                                if (State_ShiftKeyLock == 0) {
                                    State_ShiftKeyLock = 1;
                                } else if (State_ShiftKeyLock == 1) {
                                    State_ShiftKeyLock = 2;
                                } else {
                                    State_ShiftKeyLock = 0;
                                }
                            } else if (State_ShiftKeyLock > 0 && sDataStr.equals(Key_Backspace) && Key_down) {

                                // Lock + Shift + Backspace = Unlock (China,EU)
                                if (State_ShiftKeyLock == 1) {
                                    State_ShiftKeyLock = 0;
                                } else if (State_ShiftKeyLock == 2) {
                                    State_ShiftKeyLock = 0;
                                }

                                State_Lock = false;
                                Log.i(GeneralString.TAG, "Key Unlock!!! (China,EU)");
                            }

                            ReceiveKeyEvent(context, kv, "-1", kv.getMetaState(), false);
                        }

                    } else {
                        // Key Unlock

                        // 判斷是否為許可的Key
                        boolean IsValid = KeyValid(sDataStr);
                        if (IsValid) {
                            // 按下 ALPHA , FN , Shift , Ctrl , Alt Key , 並記錄其狀態
                            // Mode 0: Alpha, Mode 1: Fn, Mode 2: Shift, Mode 3: Ctrl, Mode 4= Alt
                            int CPKeyMode = IsCPKeyMode(Integer.valueOf(sDataStr));

                            if (CPKeyMode == 0 && Key_down) {
                                // ALPHA KEY (China,EU)
                                // v0.0.0.33
                                if (isAlphaKeyEnable()) {
                                    Log.i(GeneralString.TAG, "[ALPHA KEY] isAlphaKeyEnable = " + isAlphaKeyEnable() + ", SwitchAlphaKey = " + SwitchAlphaKey);
                                    // v0.0.0.12

                                    if (SwitchAlphaKey == 0) {
                                        // State_Alpha = 0, 1

                                        if (State_Alpha == 0) {
                                            State_Alpha = 1;

                                            if (State_Fn > 0) {
                                                RunKeyStatus = RunKeyFnAlpha;
                                            } else {
                                                RunKeyStatus = RunKeyAlpha;
                                            }
                                        } else if (State_Alpha == 1) {
                                            State_Alpha = 0;

                                            if (State_Fn > 0) {
                                                RunKeyStatus = RunKeyFn;
                                            } else {
                                                RunKeyStatus = RunKeyNormal;
                                            }
                                        }

                                    } else if (SwitchAlphaKey == 1) {
                                        // State_Alpha = 0, 1, 2

                                        if (State_Alpha == 0) {
                                            State_Alpha = 1;

                                            if (State_Fn > 0) {
                                                RunKeyStatus = RunKeyFnAlpha;
                                            } else {
                                                RunKeyStatus = RunKeyAlpha;
                                            }
                                        } else if (State_Alpha == 1) {
                                            State_Alpha = 2;
                                        } else if (State_Alpha == 2) {
                                            State_Alpha = 0;

                                            if (State_Fn > 0)
                                                RunKeyStatus = RunKeyFn;
                                            else
                                                RunKeyStatus = RunKeyNormal;
                                        }
                                    }
                                    Log.i(GeneralString.TAG, "[ALPHA KEY] State_Alpha = " + State_Alpha);

                                } else {
                                    Log.i(GeneralString.TAG, "isAlphaKeyEnable = false");
                                    State_Alpha = 0;
                                    if (State_Fn > 0)
                                        RunKeyStatus = RunKeyFn;
                                    else
                                        RunKeyStatus = RunKeyNormal;
                                }
                            } else if (CPKeyMode == 1 && keytype == 29 && Key_down) {
                                // FN KEY (China)

                                if (State_Fn == 0) {
                                    State_Fn = 1;
                                    if (State_Alpha > 0)
                                        RunKeyStatus = RunKeyAlphaFn;
                                    else
                                        RunKeyStatus = RunKeyFn;
                                } else if (State_Fn == 1) {
                                    State_Fn = 2;
                                    if (State_Alpha > 0)
                                        RunKeyStatus = RunKeyAlphaFn;
                                    else
                                        RunKeyStatus = RunKeyFn;
                                } else if (State_Fn == 2) {
                                    State_Fn = 0;
                                    if (State_Alpha > 0)
                                        RunKeyStatus = RunKeyAlpha;
                                    else
                                        RunKeyStatus = RunKeyNormal;
                                }

                                Log.i(GeneralString.TAG, "[FN KEY] State_Fn = " + State_Fn);
                            } else if (CPKeyMode == 2 && Key_down) {
                                // SHIFT KEY (China,EU) in real Key

                                if (State_Shift == 0) {
                                    State_Shift = 1;
                                } else if (State_Shift == 1) {
                                    State_Shift = 2;
                                } else if (State_Shift == 2) {
                                    State_Shift = 0;
                                }

                                // FN + SHIFT = SHIFT (China)
                                if (keytype == 29 && State_Fn == 1) {
                                    State_Fn = 0;
                                    ChangeKeyModeStatus();
                                }

                                Log.i(GeneralString.TAG, "[SHIFT KEY(China,EU)] State_Shift = " + State_Shift);
                            }

                            // -------------------------------------------------//
                            // 1.如果為 Alpha, Fn, Shift, Ctrl, Alt 則Keycode = 原值
                            // 2.分三個 Mode : ALPHA , FN , Normal
                            // 其中 Normal Mode 又分別判斷 Shift, Ctrl, Alt 狀態做變化
                            // -------------------------------------------------//
                            if (CPKeyMode > -1) {
                                // Alpha, Fn, Shift, Ctrl, Alt

                                if (CPKeyMode == 0) {
                                    if (isAlphaKeyEnable()) {
                                        ReceiveKeyEvent(context, kv, sDataStr, kv.getMetaState(), false);
                                    } else {
                                        String MappingValue = GetMappingKeyValue(sDataStr, 0);
                                        Log.i(GeneralString.TAG, "CPKeyMode>-1 isAlphaKeyEnable = " + isAlphaKeyEnable());
                                        Log.i(GeneralString.TAG, "MappingValue = " + MappingValue);
                                        CPMapping(MappingValue, context, kv, kv.getMetaState());
                                    }
                                } else {
                                    ReceiveKeyEvent(context, kv, sDataStr, kv.getMetaState(), false);
                                }
                            } else {
                                // When CPKeyMode == -1 , not matched with any CipherLAB Mode Key

                                // 0: Normal, 1: Alpha, 2: Fn
                                int KEYSTATUS = GetKeyModeStatus();
                                Log.i(GeneralString.TAG, "KEYSTATUS = " + KEYSTATUS);

                                // v0.0.0.12
                                if (KEYSTATUS == 1 && State_Alpha > 0) {
                                    // *** ALPHA Mode *** //

                                    // ALPHA Key + SHIFT Key + Other Key (China,EU)
                                    if (State_Shift > 0 || State_Alpha == 2) {
                                        Log.i(GeneralString.TAG, "ALPHA Key + SHIFT Key + Other Key");

                                        if (keytype == 29 &&
                                                ((Integer.valueOf(sDataStr) >= KeyEvent.KEYCODE_0 && Integer.valueOf(sDataStr) <= KeyEvent.KEYCODE_9) ||
                                                        Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_MINUS ||
                                                        Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_PERIOD)) {
                                            // 29 keys + special condition

                                            // Single effect of Shift key
                                            if (!Key_down && State_Shift == 1) {
                                                State_Shift = 0;
                                            }

                                            int mIndexLength = 0;
                                            if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_0) {
                                                mIndexLength = 9;
                                            } else if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_MINUS) {
                                                mIndexLength = 10;
                                            } else if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_PERIOD) {
                                                mIndexLength = 11;
                                            } else {
                                                mIndexLength = Integer.valueOf(sDataStr) - 8;
                                            }
                                            KeyChina_AlphaShift(kv, sDataStr, context, mIndexLength);

                                        } else if (keytype == 32 &&
                                                ((Integer.valueOf(sDataStr) >= KeyEvent.KEYCODE_0 && Integer.valueOf(sDataStr) <= KeyEvent.KEYCODE_9) ||
                                                        Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_COMMA ||
                                                        Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_PERIOD)) {
                                            // 32 keys + special conditions

                                            // Single effect of Shift key
                                            if (!Key_down && State_Shift == 1) {
                                                State_Shift = 0;
                                            }

                                            int mIndexLength = 0;
                                            if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_0) {
                                                mIndexLength = 9;
                                            } else if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_COMMA) {
                                                mIndexLength = 10;
                                            } else if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_PERIOD) {
                                                mIndexLength = 11;
                                            } else {
                                                mIndexLength = Integer.valueOf(sDataStr) - 8;
                                            }
                                            KeyEU_AlphaShift(kv, sDataStr, context, mIndexLength);

                                        } else {

                                            int index = GetDatabaseIndex("_AlphaShiftMode");
                                            String ReturnKey = QueryKey(keytype, sDataStr, index);
                                            Log.i(GeneralString.TAG, "ReturnKey = " + ReturnKey);

                                            if (!Key_down && State_Shift == 1) {
                                                State_Shift = 0;
                                            }

                                            // v0.0.0.14 (TSA-852)
                                            // When the physical Home key is locked,
                                            // then also lock the Shift+Home and Alpha+Home. (EU keypad)
                                            boolean key_enable = mSettings.getBoolean("KEY_ENABLE_HOME", true);
                                            if (ReturnKey.equals("3") && !key_enable) {
                                                Log.i(GeneralString.TAG, "KEY_ENABLE_HOME is disabled");
                                                ReceiveKey("0", context, kv, false);
                                            } else
                                                ReceiveKey(ReturnKey, context, kv, false);
                                        }
                                    } else {
                                        // ALPHA Key + Other Key (China,EU)
                                        Log.i(GeneralString.TAG, "ALPHA Key + Other Key");

                                        if (!Key_down) {
                                            // FN + ALPHA + KEY -> ALPHA Mode
                                            if (State_Fn == 1) {
                                                State_Fn = 0;
                                            }

                                            ChangeKeyModeStatus();
                                        }

                                        if (keytype == 29 &&
                                                ((Integer.valueOf(sDataStr) >= KeyEvent.KEYCODE_0 && Integer.valueOf(sDataStr) <= KeyEvent.KEYCODE_9) ||
                                                        Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_MINUS ||
                                                        Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_PERIOD)) {
                                            int mIndexLength = 0;
                                            if (Integer.valueOf(sDataStr) == 7) {
                                                mIndexLength = 9;
                                            } else if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_MINUS) {
                                                mIndexLength = 10;
                                            } else if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_PERIOD) {
                                                mIndexLength = 11;
                                            } else {
                                                mIndexLength = Integer.valueOf(sDataStr) - 8;
                                            }
                                            KeyChina_Alpha(kv, sDataStr, context, mIndexLength);

                                        } else if (keytype == 32 &&
                                                ((Integer.valueOf(sDataStr) >= KeyEvent.KEYCODE_0 && Integer.valueOf(sDataStr) <= KeyEvent.KEYCODE_9) ||
                                                        Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_COMMA ||
                                                        Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_PERIOD)) {

                                            int mIndexLength = 0;
                                            if (Integer.valueOf(sDataStr) == 7) {
                                                mIndexLength = 9;
                                            } else if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_COMMA) {
                                                mIndexLength = 10;
                                            } else if (Integer.valueOf(sDataStr) == KeyEvent.KEYCODE_PERIOD) {
                                                mIndexLength = 11;
                                            } else {
                                                mIndexLength = Integer.valueOf(sDataStr) - 8;
                                            }
                                            KeyEU_Alpha(kv, sDataStr, context, mIndexLength);

                                        } else {
                                            int index = GetDatabaseIndex("_AlphaMode");
                                            String ReturnKey = QueryKey(keytype, sDataStr, index);
                                            Log.i(GeneralString.TAG, "ReturnKey = " + ReturnKey);

                                            // v0.0.0.13
                                            String MappingValue = GetMappingKeyValue(ReturnKey, 2);
                                            Log.i(GeneralString.TAG, "MappingValue = " + MappingValue);

                                            if (MappingValue.equals(ReturnKey)) {
                                                // v0.0.0.14 (TSA-852) When the physical Home key is locked, then
                                                // Shift+Home key and Alpha+Home key are locked too.(EU keypad)
                                                boolean key_enable = mSettings.getBoolean("KEY_ENABLE_HOME", true);

                                                if (ReturnKey.equals("3") && !key_enable) {
                                                    Log.i(GeneralString.TAG, "KEY_ENABLE_HOME is disabled");
                                                    ReceiveKeyEvent(context, kv, "0", kv.getMetaState(), false);
                                                } else {
                                                    ReceiveKeyEvent(context, kv, ReturnKey, kv.getMetaState(), false);
                                                }
                                            } else {
                                                CPMapping(MappingValue, context, kv, kv.getMetaState());
                                            }
                                        }
                                    }
                                } else if (KEYSTATUS == 2 && State_Fn > 0 && keytype != 32) {
                                    // *** Fn Mode *** //

                                    // FN Key + SHIFT Key + Other Key (China)
                                    if (State_Shift > 0) {
                                        Log.i(GeneralString.TAG, "FN Key + SHIFT Key + Other Key (China)");

                                        if (!Key_down) {
                                            if (State_Fn == 1) {
                                                State_Fn = 0;
                                            }

                                            if (State_Shift == 1) {
                                                State_Shift = 0;
                                            }

                                            ChangeKeyModeStatus();
                                        }

                                        int index = GetDatabaseIndex("_FnMode");
                                        String ReturnKey = QueryKey(keytype, sDataStr, index);
                                        Log.i(GeneralString.TAG, "ReturnKey = " + ReturnKey);

                                        String MappingValue = GetMappingKeyValue(ReturnKey, 1);
                                        Log.i(GeneralString.TAG, "MappingValue = " + MappingValue);

                                        if (MappingValue.equals(ReturnKey)) {
                                            ReturnKey = "Shift+" + ReturnKey;
                                            ReceiveKey(ReturnKey, context, kv, false);
                                            Log.i(GeneralString.TAG, "Shift+ReturnKey = " + ReturnKey);
                                        } else {
                                            // Button Assignment Mapping
                                            CPMapping(MappingValue, context, kv, KeyEvent.META_SHIFT_ON);
                                        }
                                    } else {
                                        // FN Key + Other Key (China)
                                        Log.i(GeneralString.TAG, "FN Key + Other Key (China)");

                                        if (!Key_down) {
                                            if (State_Fn == 1) {
                                                State_Fn = 0;
                                            }

                                            // SHIFT + FN + Other Key
                                            if (State_Shift == 1) {
                                                State_Shift = 0;
                                            }

                                            ChangeKeyModeStatus();
                                        }

                                        int index = GetDatabaseIndex("_FnMode");
                                        String ReturnKey = QueryKey(keytype, sDataStr, index);
                                        Log.i(GeneralString.TAG, "ReturnKey = " + ReturnKey);

                                        // FN
                                        String MappingValue = GetMappingKeyValue(ReturnKey, 1);
                                        Log.i(GeneralString.TAG, "MappingValue = " + MappingValue);

                                        if (MappingValue.equals(ReturnKey)) {
                                            ReceiveKeyEvent(context, kv, ReturnKey, kv.getMetaState(), false);
                                        } else {
                                            CPMapping(MappingValue, context, kv, kv.getMetaState());
                                        }
                                    }
                                } else {
                                    // *** Normal Mode *** //

                                    // SHIFT Key + Other Key (China , EU)
                                    if (State_Shift > 0) {
                                        Log.i(GeneralString.TAG, "SHIFT Key + Other Key");

                                        if (!Key_down && State_Shift == 1) {
                                            State_Shift = 0;
                                        }

                                        int index = GetDatabaseIndex("_ShiftMode");
                                        String ReturnKey = QueryKey(keytype, sDataStr, index);
                                        Log.i(GeneralString.TAG, "ReturnKey = " + ReturnKey);

                                        // Backspace + Shift = KeyLock (China , EU)
                                        if (ReturnKey.equals(Key_Lock)) {
                                            // v0.0.0.11 (TSA-596)
                                            String mKEY_VALUE = "SHIFT_KEY_VALUE_BACKSPACE";
                                            String mKEY_ENABLE = "SHIFT_KEY_ENABLE_BACKSPACE";
                                            String result_keycode;

                                            // 取得SharedPreferences鍵值，第一個參數是key，第二個是預設值，若沒有值的話
                                            String Key_code = mSettings.getString(mKEY_VALUE, "0");
                                            boolean key_enable = mSettings.getBoolean(mKEY_ENABLE, true);

                                            Log.d(GeneralString.TAG, "Button Assignment >>\nPREF_KEY_VALUE_ARRAY = "
                                                    + mKEY_VALUE + " , Key_code = " + Key_code + "\nPREF_KEY_ENABLE_ARRAY = "
                                                    + mKEY_ENABLE + " , Key_enable = " + key_enable);
                                            result_keycode = Key_code;

                                            if (!key_enable) {
                                                result_keycode = "0";
                                                Log.i(GeneralString.TAG, "Button Assignment >> This key (Keypad Lock) is disabled.");
                                            }

                                            if (result_keycode.equals(Key_Lock)) {
                                                KeyLockStatus(Key_down, context, kv);
                                            } else {
                                                CPMapping(result_keycode, context, kv, kv.getMetaState());
                                                return;
                                            }
                                        } else if (ReturnKey.equals("3")) {
                                            // v0.0.0.14 (TSA-852) When the physical Home key is locked, then Shift+Home key
                                            // and Alpha+Home key are locked too.(EU keypad)
                                            boolean key_enable = mSettings.getBoolean("KEY_ENABLE_HOME", true);
                                            Log.i(GeneralString.TAG, "KEY_ENABLE_HOME = " + key_enable);
                                            ReceiveKey(key_enable ? ReturnKey : "0", context, kv, false);
                                        } else
                                            ReceiveKey(ReturnKey, context, kv, false);
                                    } else {
                                        // Normal + Key
                                        Log.i(GeneralString.TAG, "Normal Mode +  Key");

                                        int index = GetDatabaseIndex("_NormalMode");
                                        String ReturnKey = QueryKey(keytype, sDataStr, index);
                                        Log.i(GeneralString.TAG, "ReturnKey = " + ReturnKey);

                                        String MappingValue = GetMappingKeyValue(sDataStr, 0);

                                        Log.i(GeneralString.TAG, "MappingValue = " + MappingValue);

                                        if (MappingValue.equals(ReturnKey)) {
                                            ReceiveKeyEvent(context, kv, ReturnKey, kv.getMetaState(), false);
                                        } else {
                                            CPMapping(MappingValue, context, kv, kv.getMetaState());
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.w(GeneralString.TAG, "KeyEvent invalid. (keycode = " + sDataStr + ")");
                            ReceiveKeyEvent(context, kv, sDataStr, kv.getMetaState(), false);
                        }
                    }

                    Log.d(GeneralString.TAG, "FN=" + State_Fn + " , ALPHA=" + State_Alpha + " , SHIFT=" + State_Shift
                            + " , CTRL=" + State_Ctrl + " , ALT=" + State_Alt + " , LOCK=" + State_Lock);
                }
            } else if (intent.getAction().equals(GeneralString.Intent_Queueing_SEND_KEY_EVENT)) {
                Log.i(GeneralString.TAG, "Intent_Queueing_SEND_KEY_EVENT");

                // 取得KeyEvent Object
                String sDataStr = "";
                Bundle Getbundle = intent.getExtras();
                KeyEvent kv = (KeyEvent) Getbundle.getParcelable(GeneralString.KEYCODE_DATA);

                sDataStr = String.valueOf(kv.getKeyCode());
                sGetDataStr = String.valueOf(kv.getKeyCode());

                final boolean Key_down = kv.getAction() == KeyEvent.ACTION_DOWN;

                Log.i(GeneralString.TAG, "[Queueing] KEYCODE_DATA = " + sDataStr + ", (" + kv.getAction()
                        + ") , device id = " + kv.getDeviceId());

                if (keytype == 29) {
                    // China
                    sDataStr = SpecialKeyChina(sDataStr);
                    Log.d(GeneralString.TAG, "[Queueing] KEYCODE_DATA Change China Key= " + sDataStr);
                } else if (keytype == 32) {
                    // EU
                    sDataStr = SpecialKeyEU(sDataStr);
                    Log.d(GeneralString.TAG, "[Queueing] KEYCODE_DATA Change EU Key= " + sDataStr);
                }

                // v0.0.0.23
                boolean wakeup = GetWakeUpValue(sDataStr);
                Log.d(GeneralString.TAG, "[Queueing] GetWakeUpValue = " + wakeup);

                Intent RTintent = new Intent(GeneralString.Intent_Queueing_RECEIVE_KEY_EVENT);
                Bundle bundle = new Bundle();
                bundle.putBoolean(GeneralString.KEYCODE_WAKEUP, wakeup);
                KeyEvent WakeUpKeyEvent = kv;
                bundle.putParcelable(GeneralString.KEYCODE_DATA, WakeUpKeyEvent);
                RTintent.putExtras(bundle);
                context.sendBroadcast(RTintent);
            }
        }
    };
