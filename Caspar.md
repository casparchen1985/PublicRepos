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

- 目前的處理狀況:  
09/30 Code Review 完成, Build Signed APK, 送測申請單填寫完成, 目前等 UHF RFID Service 完工後一起包 OS Image 再進測  
</br>
</br>

# (Processing) "Disable Special KeyEvent" doesn't work
| No. | 任務名稱                         | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | ------------------------------- | ----------- | ---------- | ---------- | ------------ |
| 1   | Migrate to Android Studio       | 0.5         | 2024/09/30 | 2024/09/30 | 2024/09/30   |
| 2   | Trace code                      | 3           | 2024/09/30 | 2024/10/07 | -            |
| 3   | Fix issue                       | 1           | -          | -          | -            |
| 4   | Paper Work                      | 0.5         | -          | -          | -            |
| 5   | Build OS Image                  | 1           | -          | -          | -            |
| 6   | Verify Issue                    | 0.5         | -          | -          | -            |

- 目前的處理狀況:  
仍在一個擁有 800 行 source code 的 BroadcastReceiver 中努力, 目前規劃是從中找出 Blue/Orange key 的 flag 來加以利用  
</br>
</br>
