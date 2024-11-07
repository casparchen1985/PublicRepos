# (Pending) Control the green indicator for the Camera/MIC on RS36
| No. | 任務名稱                       | 工期 (Days) | 開始時間 | 完成時間 | 實際完成時間 |
| --- | ------------------------------ | ----------- | -------- | -------- | ------------ |
| 1   | Study requirement & Discussion | 0.5         | 10/18    | 10/18    | 10/18        |
| 2   | Trace code                     | 同上        | -        | -        | -            |
| 3   | Implement feature              | 2           | 10/21    | 10/22    | 10/23        |
| 4   | Code Review                    | 0.5         | 10/24    | 10/25    |              |
| 5   | Build OS Image                 | -           | -        | -        | -            |
| 6   | Paper Work                     | -           | -        | -        | -            |

- Links:</br>
[NTSA-1367](https://jira.cipherlab.com.tw/browse/NTSA-1367)  
[NTSA-1374](https://jira.cipherlab.com.tw/browse/NTSA-1374)  
[P_RS36_E-767](https://jira.cipherlab.com.tw/browse/P_RS36_E-767)  

- 目前的處理狀況:  
10/25 周五完成 code review 後的修改, 等 Dimming Mode release 再接著處理後續  
</br>
</br>

# (Processing) Implement the Dimming Mode function on RK95 series
| No. | 任務名稱          | 工期 (Days) | 開始時間 | 完成時間 | 實際完成時間 |
| --- | ----------------- | ----------- | -------- | -------- | ------------ |
| 1   | Trace code        | 0.5         | 10/15    | 10/15    | 10/15        |
| 2   | Implement feature | 2           | 10/15    | 10/17    | 10/17        |
| 3   | Code Review       | 1           | 10/24    | 10/25    | 10/25        |
| 4   | Build OS Image    | ?           | -        | -        | -            |
| 5   | Verify & Fix issue| 5           | 10/28    | 11/01    |              |
| 6   | Paper Work        | -           | -        | -        | -            |

- Links:</br>
[P_RK95_S_E-204](https://jira.cipherlab.com.tw/browse/P_RK95_S_E-204)  
[P_RK95_E-1020](https://jira.cipherlab.com.tw/browse/P_RK95_E-1020)  
[SW_ES-10](https://jira.cipherlab.com.tw/browse/SW_ES-10)  


- 目前的處理狀況:  
10/25 因為功能做在 service 端故需要驗證, 而開發階段又沒有 staging build server 可以產出 os image, 故必須將 apk 包進 daily build image 中才有辦法確認功能, 因而被包進 BSP 送 SIT 測試的版本中.  </br></br>
10/28 RK95cc v5130 有 [SW_CLUTY-134](https://jira.cipherlab.com.tw/browse/SW_CLUTY-134), 已通知 SIT 此為 known issue 已修復在新版 apk.  </br></br>
10/30 稍早自測的時候發現系統設定內的 screen off time 會顯示 null, 查過 Log 和 詢問 BSP 本和後交叉比對後得知 EnterpriseService 取回系統設定值(121s)的時間早於寫入 ChiperLAB 預設值(60s), 因而產生覆寫了不正常的時間回系統, 導致 ChiperLAB 預設值不再寫入, UI上也就無法顯示對應的的項目與資訊.  </br></br>
10/31 為了避免上述問題, 便在 service 內新增 screen_off_time 檢查, 並且合併 UI 原本的 dimmingTime < screen_off_time 檢查, 加到 setDimmingTime() 的流程中.     
然而為配合由 service 統一檢查, 新增了 OperationResponse/ OperationResult 進行封裝, 將處理後的各種狀況/結果有效地回傳給 EnterpriseSettings (UI), 以便在UI上顯示正確的資訊
</br>
</br>

# (Pending) Remove unnecessary heater status/information on RK95CC
| No. | 任務名稱                       | 工期 (Days) | 開始時間 | 完成時間 | 實際完成時間 |
| --- | ------------------------------ | ----------- | -------- | -------- | ------------ |
| 1   | Study requirement & Discussion | 0.5         | 10/14    | 10/14    | 10/14        |
| 2   | Trace code                     | 同上        | -        | -        | -            |
| 3   | Remove feature                 | 1           | 10/14    | 10/15    | 10/14        |
| 4   | Code Review                    | 0.5         | 10/24    | 10/25    | -            |
| 5   | Paper Work                     | -           | -        | -        | -            |

- Links:</br>
[P_RK95_S_I-61](https://jira.cipherlab.com.tw/browse/P_RK95_S_I-61)  

- 目前的處理狀況:  
10/28 用 PM 提供的 CC with 5800 驗證完畢
</br>
</br>

# (Pendings) "Disable Special KeyEvent" doesn't work on RK26 @JP
| No. | 任務名稱                  | 工期 (Days) | 開始時間 | 完成時間 | 實際完成時間 |
| --- | ------------------------- | ----------- | -------- | -------- | ------------ |
| 1   | Migrate to Android Studio | 0.5         | 09/30    | 09/30    | 09/30        |
| 2   | Trace code                | 4           | 09/30    | 10/07    | 10/08        |
| 3   | Fix issue                 | 1           | 10/09    | 10/09    | 10/09        |
| 4   | Code Review               | 0.5         | 10/24    | 10/25    | 10/25        |
| 5   | Build OS Image            | -           | -        | -        | -            |
| 6   | Paper Work                | -           | -        | -        | -            |
| 7   | Verify Issue              | -           | -        | -        | -            |

- 目前的處理狀況:  
10/25 Code review 修改完成, 等上面需求處理完再包進 image
</br>
</br>
