mDeviceModel = SystemProperties.get("ro.product.model");  
mDeviceVersion = SystemProperties.get("ro.build.version.release");  
deviceID = SystemProperties.get("sys.device.id");  


# Enterprise - ColdChain
null

# Enterprise - Service
A. mDeviceModel, deviceID =>
1. onCreate()  
   (a) OTAFilter
   ```
   if(mDeviceModel.contains("35")&&mDeviceVersion.contains("10")){
     OTAFilter.addAction(Intent.ACTION_TIME_TICK);
   }
   ```

   (b) PowerManager
   ```
   if (mDeviceModel.contains("95")&&deviceID.substring(12,13).equals("1")) {
     if (!heaterfile.isFile()) {
       readJson.init();
     } else {
       readJson.readHeaterJson();
     }
   }
   ```

  2. sendIntent(), rebootSaveMode(), DimmingMode()  
     SystemProperties.set()  
     sendBroadcast(SendIntent)  
  3. get_properties()
  4. OTAReceiver<BroadcastReceiver>
  
      (a) Thread.sleep(3000);

      (b)
     ```
     if(intent.getAction().equals(ENTERPRISE_EXPORT)) {
       if (mDeviceModel.contains("95") && deviceID.substring(12,13).equals("1"))
         readJson.init();
     }
     
     if (intent.getAction().equals(ENTERPRISE_IMPORT)) {
       if(mDeviceModel.contains("95")&&deviceID.substring(12,13).equals("1")){
         readJson.readHeaterJson();
         initHeater();
       }}
   
    if(intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
      if(mDeviceModel.contains("35") && mDeviceVersion.contains("10"))
        SetNFCPowerStatus(false);
    }
 

# Enterprise Settings
A. mDeviceModel.contains("38") =>  
  1. Select Preference Layout
  2. setUpUI()
  3. Add/Remove  
    (a) Doze Mode  
    (b) UHF Module  
    (c) Reboot to Safe Mode  
    (d) App Rotation Entry 
  4. dim_prefs.unregisterOnSharedPreferenceChangeListener()

B. deviceID => 
  1. Select Preference Layout
  ```
  else if (mDeviceModel.contains("95")) {
    if (deviceID.substring(12, 13).equals("1")) { 
      // heater
      addPreferencesFromResource(R.xml.preference_rk95cc);
    } else {
      addPreferencesFromResource(R.xml.preference_rk95);
    }
  }
  ```

  3. setUpUI()  
  ```
  else if (mDeviceModel.contains("95")) {
    if (deviceID.substring(12, 13).equals("1")) {
      // heater
      heaterPreference = (TwoTargetPreference) findPreference("toggle_heater");
    }}
  ```

  4. about()  
  ```
  if (mDeviceModel.contains("95") && deviceID.substring(12, 13).equals("1")) {
    GetFWVersion();
    mVersionPreference.setSummary("FW Version : " + ver + "\n" + "Enterprise Settings : " + versionName + "\n" + "Enterprise Services : " + versionName_service + "\n");
  } else {
      mVersionPreference.setSummary("Enterprise Settings : " + versionName + "\n" + "Enterprise Services : " + versionName_service + "\n");
    }}
  ```

# LogGen
A. mDeviceModel =>  
  1. deviceInfo()  
    eviceInfo.setSummary(".......");

  2. onPreferenceClick()  
     zip_path = "/" + filename + "_" + mDeviceModel + "_" + serial_number + "_" + filetime + ".zip";

# BatteryNotification
A. mDeviceModel, deviceID =>  
  1. onCreate()  
    ```
    if(mDeviceModel.contains("51") && mDeviceVersion.contains("8"))
      RS50_battery();
    else if(mDeviceModel.contains("51") && mDeviceVersion.equals("11")) {
      readJson();
      RS51R_battery();
    } else if(mDeviceModel.contains("95")&&deviceID.substring(12,13).equals("1"))
      RK95CC_battery();
    ```

  2. MediaPlayerNotificationShow <BroadcastReceiver>  
    (a) intent.getAction().equals(Intent.ACTION_SCREEN_ON)  
      Same as onCreate()  
  
    (b) intent.getAction().equals("enterprise.mode.action.BATTERY_NOTIFICATION")  
    ```
    if(mDeviceModel.contains("51") && mDeviceVersion.contains("8"))
      RS50_battery();
    else if(mDeviceModel.contains("51") && mDeviceVersion.equals("11"))
      RS51R_battery();
    ```

# SDC Activation Tool - FileTool
null

# SDC Activation Tool - FAC / USER
A. mDeviceModel =>  
  1. onCreate()
  ```
  if(mDeviceModel.contains("51")||mDeviceModel.contains("35")){
    dir.mkdir();
    per_file( dir );
  }
  ```

  2. check_deviceID()  
    Thread.sleep( 1000 );  
  4. check_lic()  
    opy( RS51_adcpath + "/z97shdftf56eg8.asdc", Environment.getExternalStorageDirectory().getPath() + "/z97shdftf56eg8.asdc" );

B. device_ID =>  
  1. SDCReceiver<BroadcastReceiver>  
    (a) onReceive()
      ```  
      if(intent.getAction().equals( Intent.ACTION_BOOT_COMPLETED))
      Thread.sleep(1000);
      ```
