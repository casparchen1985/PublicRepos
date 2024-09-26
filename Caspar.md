# (To Do) UHF Module Issue
| No. | 任務名稱                         | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | ------------------------------ | ----------- | ---------- | ---------- | ------------ |
| 1   | Fix Issue                      | 1           | 2024/09/20 | 2024/09/23 | 2024/09/23   |
| 2   | AIDL Structure                 | 2           | 2024/09/23 | 2024/09/24 | 2024/09/24   |
| 3   | Service binding for UHF Module | 2           | 2024/09/24 | 2024/09/26 | 2024/09/26   |
| 5   | Code Review                    | -           | -          | -          | -            |
| 6   | Paper Work                     | 0.5         | -          | -          | -            |

- Links:</br>
[A_SRFID_310-20](https://jira.cipherlab.com.tw/browse/A_SRFID_310-20)  

- 目前的處理狀況:  
透過 AIDL + Service Binding 方式已經可以使獨立的 UI app (Enterprise Settings) 單純只處理 UHF Module 相關的 UI 邏輯  
而 Service app 統一處理 Get/Set System Property 以及 Initial / Read / Save Settings.JSON 相關操作  
藉此讓不同層面的操作邏輯彼此獨立, 以提升程式維護的效率.  
Source Code 整理完之後會進行 Code Review
</br>
</br>

# (Done) RK26 Keyboard Sound 失效
| No. | 任務名稱                         | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | ------------------------------- | ----------- | ---------- | ---------- | ------------ |
| 1   | Study, Trace Code               | 1.5         | 2024/09/05 | 2024/09/06 | 2024/09/06   |
| 2   | Eclipse 環境設定與除錯            | 1           | 2024/09/09 | 2024/09/10 | 2024/09/10   |
| 3   | Fix issue                       | 1           | 2024/09/10 | 2024/09/10 | 2024/09/11   |
| 4   | Paper Work                      | 0.5         | 2024/09/11 | 2024/09/11 | 2024/09/11   |
| 5   | Build OS Image                  | 1           | 2024/09/11 | 2024/09/12 | 2024/09/12   |
| 6   | Verify Issue                    | 0.5         | 2024/09/23 | 2024/09/23 | 2024/09/23   |

- 目前的處理狀況:  
9/23 SIT PASS, 寄出 Software Release 信. 將 code push 到 Mercurial.
</br>
</br>
</br>
</br> 


# (Pending) RK25 enroll failed @ Thailand
- 目前的處理狀況: &emsp; 目前沒有迫切需求, 降低優先權, 先處理其他任務.
</br>
</br>

# (Pending) Tools upgrade on EnterpriseSettings
| No. | 任務                                 | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | ------------------------------------ | ----------- | ---------- | ---------- | ------------ |
| 1   | Verify after code merged (Mercurial) | 1           | 2024/08/12 | -          | -            |

- 目前的處理狀況: &emsp; 等待最後通知
