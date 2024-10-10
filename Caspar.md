# (To Do) RK95CC Remove unnecessary heater status/information on UI
| No. | 任務名稱                         | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | ------------------------------ | ----------- | ---------- | ---------- | ------------ |
| 1   | Study requirement & Discussion | -           | -          | -          | -            |
| 2   | Trace code                     | -           | -          | -          | -            |
| 3   | Implement feature              | -           | -          | -          | -            |
| 4   | Code Review                    | -           | -          | -          | -            |
| 5   | Paper Work                     | -           | -          | -          | -            |

- Links:</br>
[P_RK95_S_I-61](https://jira.cipherlab.com.tw/browse/P_RK95_S_I-61)  

- 目前的處理狀況:  
10/11 開始 trace srouce code
</br>
</br>

# (Processing) UHF Module Issue
| No. | 任務名稱                         | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | ------------------------------ | ----------- | ---------- | ---------- | ------------ |
| 1   | Fix Issue                      | 1           | 2024/09/20 | 2024/09/23 | 2024/09/23   |
| 2   | AIDL Structure                 | 2           | 2024/09/23 | 2024/09/24 | 2024/09/24   |
| 3   | Service binding for UHF Module | 2           | 2024/09/24 | 2024/09/26 | 2024/09/26   |
| 4   | Code Review                    | 0.5         | 2024/09/30 | 2024/09/30 | 2024/09/30   |
| 5   | Paper Work                     | 0.5         | 同上        | -          | -            |
| 6   | Build OS Image                 | 1           | -          | -          | -            |
| 7   | Verify Issue                   | 0.5         | -          | -          | -            |

- Links:</br>
[A_SRFID_310-20](https://jira.cipherlab.com.tw/browse/A_SRFID_310-20)  
[A_SRFID_310-24](https://jira.cipherlab.com.tw/browse/A_SRFID_310-24)  

- 目前的處理狀況:  
09/30 Code Review 完成, Build Signed APK, 送測申請單填寫完成, 目前等 UHF RFID Service / EZConfig 等相關搭配軟體完工後 build 出 OS Image 再送測
</br>
</br>

# (Processing) "Disable Special KeyEvent" doesn't work
| No. | 任務名稱                         | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | ------------------------------- | ----------- | ---------- | ---------- | ------------ |
| 1   | Migrate to Android Studio       | 0.5         | 2024/09/30 | 2024/09/30 | 2024/09/30   |
| 2   | Trace code                      | 4           | 2024/09/30 | 2024/10/07 | 2024/10/08   |
| 3   | Fix issue                       | 1           | 2024/10/09 | 2024/10/09 | 2024/10/09   |
| 4   | Paper Work                      | 0.5         | 同上        | -          | -            |
| 5   | Build OS Image                  | 1           | -          | -          | -            |
| 6   | Verify Issue                    | 0.5         | -          | -          | -            |

- 目前的處理狀況:
在 KeyMappingManager 上修正與 Disable Special KeyEvent 啓閉狀態有相關的邏輯,
把 Alpha Mode / Fn Mode 及後續組合鍵的行為攔截下來, 並在攔截後取消 Alpha/Fn/Shift 狀態, 使鍵盤恢復到一般數字鍵盤模式
本次修正行為已與 Hank (SW PM) 同步完, 接下來會再跟 Kale (Support) 同步資訊, 最後再包進 OS Image 送測
</br>
</br>
