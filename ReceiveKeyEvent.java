    public static void ReceiveKeyEvent(Context context, KeyEvent ke, String keyCode, int keyMetaState, boolean delKey) {
        Log.i(GeneralString.TAG, "ReceiveKeyEvent, keyCode: " + keyCode + ", keyMetaState: " + keyMetaState);

        // 0: Normal , 1: Alpha , 2: Fn
        int keyModeStatus = GetKeyModeStatus();
        int keyFlag = 0;

        if (keyModeStatus == 1) {
            keyFlag = ke.getFlags() | FLAG_CIPHERLAB | FLAG_ALPHA;
        } else if (keyModeStatus == 2) {
            keyFlag = ke.getFlags() | FLAG_CIPHERLAB | FLAG_FN;
        } else {
            keyFlag = ke.getFlags() | FLAG_CIPHERLAB;
        }

        if (!isNumeric(keyCode)) {
            keyCode = "-1";
            Log.i(GeneralString.TAG, "It's not a numeric keyCode");
        }

        int keyCodeInt = Integer.parseInt(keyCode);
        KeyEvent newKeyEvent = new KeyEvent(
                ke.getDownTime(),
                ke.getEventTime(),
                ke.getAction(),
                keyCodeInt,
                ke.getRepeatCount(),
                keyMetaState,
                ke.getDeviceId(),
                ke.getScanCode(),
                keyFlag
        );

        // *** Set Notification Icon *** //
        SetNIcon(context);

        boolean needToCheckMore = false;

        switch (keyCodeInt) {
            case KeyEvent.KEYCODE_BACK:
                Log.i(GeneralString.TAG, "KeyEvent.KEYCODE_BACK");
                KeyEvent back_spaceKeyEvent_1 = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(), -1,
                        ke.getRepeatCount(), keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);
                sendReceiveKeyEventBroadcast(context, back_spaceKeyEvent_1);
                break;

            case KeyEvent.KEYCODE_HOME:
                // v0.0.0.5 Touch key (HOME)
                Log.i(GeneralString.TAG, "KeyEvent.KEYCODE_HOME");

                if (!Istouch_home(keyCode)) {
                    Log.i(GeneralString.TAG, "HOME key is disabled (2)");
                    KeyEvent home_KeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(), -1,
                            ke.getRepeatCount(), keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);
                    sendReceiveKeyEventBroadcast(context, home_KeyEvent);
                } else {
                    sendReceiveKeyEventBroadcast(context, newKeyEvent);
                }
                break;

            case KeyEvent.KEYCODE_DEL:
                // v0.0.16
                Log.i(GeneralString.TAG, "KeyEvent.KEYCODE_DEL");

                if (ke.getDeviceId() != -1) {
                    Log.i(GeneralString.TAG, "It's HW keyboard");

                    KeyEvent mKeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(), -1,
                            ke.getRepeatCount(), keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);
                    sendReceiveKeyEventBroadcast(context, mKeyEvent);

                    if (repeat == null && ke.getAction() == 0 && ke.getRepeatCount() == 0) {
                        SendInjectInputEvent(context, ke.getAction(), KeyEvent.KEYCODE_DEL, ke.getRepeatCount(),
                                keyMetaState, keyFlag);
                        repeat = new RepeatTimer(context, KeyEvent.KEYCODE_DEL, keyMetaState, keyFlag);
                        repeat.Start();
                    } else if (repeat != null && ke.getAction() == 1) {
                        repeat.Stop();
                        repeat = null;
                        SendInjectInputEvent(context, ke.getAction(), KeyEvent.KEYCODE_DEL, ke.getRepeatCount(),
                                keyMetaState, keyFlag);
                    }
                    return;

                } else {
                    sendReceiveKeyEventBroadcast(context, newKeyEvent);
                }
                break;

            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.i(GeneralString.TAG, "KeyEvent.KEYCODE_VOLUME_UP/DOWN: " + keyCodeInt);

                if (Integer.parseInt(sGetDataStr) == keyCodeInt) {
                    Log.d(GeneralString.TAG, "sGetDataStr is equal to keyCodeInt: " + keyCodeInt);
                    sendReceiveKeyEventBroadcast(context);
                    return;
                } else {
                    sendReceiveKeyEventBroadcast(context, newKeyEvent);
                }
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_DPAD_LEFT:
                Log.i(GeneralString.TAG, "KeyEvent.KEYCODE_DPAD_DOWN/UP/RIGHT/LEFT: " + keyCodeInt);

                if (ke.getDeviceId() != -1) {
                    Log.i(GeneralString.TAG, "It's HW keyboard");

                    KeyEvent mKeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(), -1,
                            ke.getRepeatCount(), keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);
                    sendReceiveKeyEventBroadcast(context, mKeyEvent);

                    if (repeat == null && ke.getAction() == 0 && ke.getRepeatCount() == 0) {
                        SendInjectInputEvent(context, ke.getAction(), Integer.parseInt(keyCode), ke.getRepeatCount(),
                                keyMetaState, keyFlag);
                        repeat = new RepeatTimer(context, Integer.parseInt(keyCode), keyMetaState, keyFlag);
                        repeat.Start();
                    } else if (repeat != null && ke.getAction() == 1) {
                        repeat.Stop();
                        repeat = null;
                        SendInjectInputEvent(context, ke.getAction(), Integer.parseInt(keyCode), ke.getRepeatCount(),
                                keyMetaState, keyFlag);
                    }
                    return;
                } else {
                    sendReceiveKeyEventBroadcast(context, newKeyEvent);
                }
                break;

            default:
                // go to more check
                needToCheckMore = true;
        }

        if (needToCheckMore) {
            Log.i(GeneralString.TAG, "OnNextCheck");

            if (delKey) {
                Log.i(GeneralString.TAG, "It's delete key");

                KeyEvent deleteKeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(),
                        KeyEvent.KEYCODE_DEL, ke.getRepeatCount(), ke.getMetaState(), ke.getDeviceId(), ke.getScanCode(),
                        keyFlag);
                sendReceiveKeyEventBroadcast(context, 2, deleteKeyEvent, newKeyEvent);
            } else if (HighlightKey) {
                Log.i(GeneralString.TAG, "It's highlight key: " + keyCodeInt);
                HighlightKey = false;
                KeyEvent shiftKeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(),
                        KeyEvent.KEYCODE_SHIFT_LEFT, ke.getRepeatCount(), ke.getMetaState(), ke.getDeviceId(),
                        ke.getScanCode(), keyFlag);
                sendReceiveKeyEventBroadcast(context, 2, shiftKeyEvent, newKeyEvent);
            } else if (keyCodeInt == KeyEvent.KEYCODE_SPACE &&
                    (keyMetaState == KeyEvent.META_CTRL_ON || keyMetaState == (KeyEvent.META_CTRL_ON | KeyEvent.META_SHIFT_ON))) {
                // Ctrl + Space || Ctrl + Shift + Space
                Log.i(GeneralString.TAG, "Ctrl + Space || Ctrl + Shift + Space: " + keyCode);

                // Android default ctrl+space : Handle keyboard language switching , so set repeat 1
                KeyEvent ctrl_spaceKeyEvent_1 = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(),
                        Integer.parseInt(keyCode), 1, keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);
                sendReceiveKeyEventBroadcast(context, ctrl_spaceKeyEvent_1);
            } else {
                // test 0.0.0.9-test20180816
                if (SpecialKeyEvent == 1) {
                    Log.i(GeneralString.TAG, "SpecialKeyEvent is ON");

                    boolean isScanKey = false;
                    for (int i = 0; i < filter_CustomKey.length; i++) {
                        if (keyCode.equals(filter_CustomKey[i])) {
                            newKeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(), -1, ke.getRepeatCount(), keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);

                            if (keyCode.equals("505") || keyCode.equals("507") || keyCode.equals("508") || keyCode.equals("545")) {
                                isScanKey = true;
                            }
                            break;
                        }
                    }

                    if (isScanKey) {
                        Log.i(GeneralString.TAG, "Send scanKey intent");

                        if ((ke.getAction() == KeyEvent.ACTION_DOWN) && (ke.getRepeatCount() == 0)) {
                            Log.d(GeneralString.TAG, "BUTTON ACTION_DOWN");
                            Intent downIntent = new Intent("android.intent.action.FUNC_BUTTON");
                            mContext.sendBroadcastAsUser(downIntent, android.os.Process.myUserHandle());

                        } else if (ke.getAction() == KeyEvent.ACTION_UP) {
                            Log.d(GeneralString.TAG, "BUTTON ACTION_UP");
                            Intent UPintent = new Intent("android.intent.action.FUNC_RELEASE_BUTTON");
                            mContext.sendBroadcastAsUser(UPintent, android.os.Process.myUserHandle());
                        }
                    }
                }

                // NTSA-296
                if (KeyEventRDP == 1) {
                    Log.i(GeneralString.TAG, "KeyEventRDP is ON");

                    if (keyMetaState == KeyEvent.META_CTRL_ON) {
                        Log.i(GeneralString.TAG, "KeyEvent.META_CTRL_ON");

                        KeyEvent skip_KeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(), -1,
                                ke.getRepeatCount(), keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);
                        sendReceiveKeyEventBroadcast(context, skip_KeyEvent);

                        if (ke.getAction() == KeyEvent.ACTION_DOWN) {
                            int win_scancode = WindowsScancode.GetScanCode(keyCodeInt);
                            Log.i(GeneralString.TAG, "ACTION_DOWN, WindowsScancode: " + win_scancode);

                            SendInjectInputEvent(context, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_CTRL_LEFT, 0, 0, FLAG_CIPHERLAB);
                            SendInjectInputEvent_scancode(context, KeyEvent.ACTION_DOWN, keyCodeInt, ke.getRepeatCount(), KeyEvent.META_CTRL_ON | KeyEvent.META_CTRL_LEFT_ON, win_scancode, FLAG_CIPHERLAB);
                            SendInjectInputEvent_scancode(context, KeyEvent.ACTION_UP, keyCodeInt, ke.getRepeatCount(), KeyEvent.META_CTRL_ON | KeyEvent.META_CTRL_LEFT_ON, win_scancode, FLAG_CIPHERLAB);
                            SendInjectInputEvent(context, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_CTRL_LEFT, 0, 0, FLAG_CIPHERLAB);
                        }
                    } else if (keyMetaState == KeyEvent.META_ALT_ON) {
                        Log.i(GeneralString.TAG, "KeyEvent.META_ALT_ON");

                        KeyEvent skip_KeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(), -1,
                                ke.getRepeatCount(), keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);
                        sendReceiveKeyEventBroadcast(context, skip_KeyEvent);

                        if (ke.getAction() == KeyEvent.ACTION_DOWN) {
                            int win_scancode = WindowsScancode.GetScanCode(Integer.parseInt(keyCode));
                            Log.i(GeneralString.TAG, "ACTION_DOWN, WindowsScancode: " + win_scancode);

                            SendInjectInputEvent(context, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ALT_LEFT, 0, 0, FLAG_CIPHERLAB);
                            SendInjectInputEvent_scancode(context, KeyEvent.ACTION_DOWN, Integer.parseInt(keyCode), ke.getRepeatCount(), 0, win_scancode, FLAG_CIPHERLAB);
                            SendInjectInputEvent_scancode(context, KeyEvent.ACTION_UP, Integer.parseInt(keyCode), ke.getRepeatCount(), 0, win_scancode, FLAG_CIPHERLAB);
                            SendInjectInputEvent(context, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ALT_LEFT, 0, 0, FLAG_CIPHERLAB);
                        }
                    } else if (keyMetaState == KeyEvent.META_SHIFT_ON) {
                        Log.i(GeneralString.TAG, "KeyEvent.META_SHIFT_ON");

                        KeyEvent skip_KeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(), -1,
                                ke.getRepeatCount(), keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);
                        sendReceiveKeyEventBroadcast(context, skip_KeyEvent);

                        if (ke.getAction() == KeyEvent.ACTION_DOWN) {
                            int win_scancode = WindowsScancode.GetScanCode(Integer.parseInt(keyCode));
                            Log.i(GeneralString.TAG, "ACTION_DOWN, WindowsScancode: " + win_scancode);

                            SendInjectInputEvent(context, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT, 0, 0, FLAG_CIPHERLAB);
                            SendInjectInputEvent_scancode(context, KeyEvent.ACTION_DOWN, Integer.parseInt(keyCode), ke.getRepeatCount(), KeyEvent.META_SHIFT_ON | KeyEvent.META_SHIFT_LEFT_ON, win_scancode, FLAG_CIPHERLAB);
                            SendInjectInputEvent_scancode(context, KeyEvent.ACTION_UP, Integer.parseInt(keyCode), ke.getRepeatCount(), KeyEvent.META_SHIFT_ON | KeyEvent.META_SHIFT_LEFT_ON, win_scancode, FLAG_CIPHERLAB);
                            SendInjectInputEvent(context, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SHIFT_LEFT, 0, 0, FLAG_CIPHERLAB);
                        }
                    } else {
                        Log.i(GeneralString.TAG, "Other scenario");
                        sendReceiveKeyEventBroadcast(context, newKeyEvent);
                    }

                } else if (ke.getKeyCode() == 545 || ke.getKeyCode() == 548) {
                    Log.i(GeneralString.TAG, "KeyCode is 545 or 548");

                    KeyEvent mKeyEvent = new KeyEvent(ke.getDownTime(), ke.getEventTime(), ke.getAction(), -1,
                            ke.getRepeatCount(), keyMetaState, ke.getDeviceId(), ke.getScanCode(), keyFlag);
                    sendReceiveKeyEventBroadcast(context, mKeyEvent);

                    if (repeat == null && ke.getAction() == 0 && ke.getRepeatCount() == 0) {
                        SendInjectInputEvent(context, ke.getAction(), keyCodeInt, ke.getRepeatCount(), keyMetaState, keyFlag);
                        repeat = new RepeatTimer(context, keyCodeInt, keyMetaState, keyFlag);
                        repeat.Start();
                        Log.d(GeneralString.TAG, " repeat.Start()");
                    } else if (repeat != null && ke.getAction() == 1) {
                        Log.d(GeneralString.TAG, "repeat.Stop()");
                        repeat.Stop();
                        repeat = null;
                        SendInjectInputEvent(context, ke.getAction(), keyCodeInt, ke.getRepeatCount(), keyMetaState, keyFlag);
                    }
                    // v0.0.0.25
                    SendIntentToGun(newKeyEvent);
                } else {
                    Log.i(GeneralString.TAG, "Normal scenario");

                    if (repeat != null && ke.getAction() == 1) {
                        Log.d(GeneralString.TAG, "repeat.Stop()");
                        repeat.Stop();
                        repeat = null;
                        SendInjectInputEvent(context, ke.getAction(), keyCodeInt, ke.getRepeatCount(), keyMetaState, keyFlag);
                    }
                    sendReceiveKeyEventBroadcast(context, newKeyEvent);
                }
            }
        }

        Log.i(GeneralString.TAG, "Action: " + ke.getAction() + ", RepeatCount: " + ke.getRepeatCount() + ", metaState: " + keyMetaState + ", FLAG: " + keyFlag + ", ScanCode: " + ke.getScanCode());
    }








    /**
     * @param context Service context
     */
    private static void sendReceiveKeyEventBroadcast(Context context) {
        sendReceiveKeyEventBroadcast(context, 1, null, null);
    }

    /**
     * @param context      Service context
     * @param dataKeyEvent KeyEvent
     */
    private static void sendReceiveKeyEventBroadcast(Context context, @NonNull KeyEvent dataKeyEvent) {
        sendReceiveKeyEventBroadcast(context, 1, dataKeyEvent, null);
    }

    /**
     * @param context       Service context
     * @param eventQuantity How many Parcelable data you want to send out.
     * @param dataKeyEvent  if you have multiple KeyEvents, put modifier KeyEvent (like deleteKeyEvent or shiftKeyEvent) here first.
     * @param data1KeyEvent if you only have 1 KeyEvent, just put null here.
     */
    private static void sendReceiveKeyEventBroadcast(Context context, @NonNull Integer eventQuantity, KeyEvent dataKeyEvent, KeyEvent data1KeyEvent) {
        Intent RTintent = new Intent(GeneralString.Intent_RECEIVE_KEY_EVENT);
        Bundle bundle = new Bundle();
        bundle.putInt(GeneralString.KEYCODE_NUMBER, eventQuantity);
        bundle.putParcelable(GeneralString.KEYCODE_DATA, dataKeyEvent);
        if (data1KeyEvent != null) bundle.putParcelable(GeneralString.KEYCODE_DATA1, data1KeyEvent);
        RTintent.putExtras(bundle);
        context.sendBroadcast(RTintent);
    }
