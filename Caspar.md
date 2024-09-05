# (Done) Paper Work
| No. | 任務名稱                         | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | -------------------------------- | ----------- | ---------- | ---------- | ------------ |
| 1   | 檢查各專案中 DeviceModel 跟 DeviceID 的使用目的   | 0.5         | 2024/09/05 | 2024/09/05 | 2024/09/05   |
| 2   | RK26 Keyboard Sound 失效 | -           | - | - | -   |

- Links:</br>
[ModelName & DeviceID](https://github.com/casparchen1985/PublicRepos/blob/main/ModelAndDeviceIDUsage.md) &emsp; 
[P_RK26_I-59](https://jira.cipherlab.com.tw/browse/P_RK26_I-59)

- 目前的處理狀況:</br>
1. 因應 RS38H 預計是透過 Model Name 來跟 RS38 做區隔, 所以先掃過相關的程式碼, 了解一下大概都是在做些什麽.
2. 因從 Pre-Sales 端反應日本客戶告知 RK26 Keyboard Sound 沒有做用. 隨後發信跟 SIT 詢問, 有得到對應的 JiRA ticket</br>
接下來會往 KeyMappingManager 排查問題


# (Done) Building Up @ GROWI
| No. | 任務名稱                         | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | -------------------------------- | ----------- | ---------- | ---------- | ------------ |
| 1   | Create EnterpriseSettings page   | 0.5         | 2024/09/03 | 2024/09/03 | 2024/09/03   |
| 2   | Combine all documents into GROWI | 1           | 2024/09/04 | 2024/09/04 | 2024/09/04   |
| 3   | Share QFil Stroage Type and Add Sticky Note for Enterprise Settings     | 0.5         | 2024/09/05 | 2024/09/05 | 2024/09/05   |

- Links:</br>
[Enterprise Setting](http://192.168.8.100:12000/66d6d868e29b20ef8a071363) &emsp;
[Release Note](http://192.168.8.100:12000/66d7d7c6e29b20ef8a07a7b6)

- 目前的處理狀況:</br>
將原有的檔案 `機種與功能對照檔(.xlsx)`, `各功能測試及Property key紀錄檔(.docx)`, `版本記錄檔(.txt)` 內容整合進 GROWI</br>
並將相關檔案連結一併整理


# (Processing) App Rotation Issue
| No. | 任務名稱                   | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | -------------------------- | ----------- | ---------- | ---------- | ------------ |
| 1   | Meeting, Study and Prepare | 1           | 2024/08/27 | 2024/08/28 | 2024/08/28   |
| 2   | Fix issue                  | 3           | 2024/08/28 | 2024/08/30 | 2024/09/02   |
| 3   | Paper Work                 | 0.5         | 2024/09/02 | 2024/09/02 | -            |

- Links:</br>
[S_WMDS-622](https://jira.cipherlab.com.tw/browse/S_WMDS-622) &emsp;
[S_WMDS-625](https://jira.cipherlab.com.tw/browse/S_WMDS-625)

- 目前的處理狀況:</br>
待 Code Review 完成</br>
待 Weekly Meeting 討論, 若確定要啟用 GROWI,  將停止更新原本使用的各文件檔案, 並以 GROWI 為公告媒介提供給其他單位瀏覽

</br>
</br>
</br>
</br> 


# (Pending) RK25 enroll failed @ Thailand
| No. | 任務名稱                    | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | --------------------------- | ----------- | ---------- | ---------- | ------------ |
| 1   | Environment study / Prepare | -           | -          | -          | -            |
| 2   | Reproduce                   | 0.5         | 2024/08/15 | 2024/08/15 | 2024/08/15   |
| 3   | Study hostLog               | 2           | 2024/08/19 | 2024/08/20 | 2024/08/20   |

- 目前的處理狀況: &emsp; 目前沒有迫切需求, 降低優先權, 先處理其他任務.


# (Pending) Tools upgrade on EnterpriseSettings
| No. | 任務                                 | 工期 (Days) | 開始時間   | 完成時間   | 實際完成時間 |
| --- | ------------------------------------ | ----------- | ---------- | ---------- | ------------ |
| 1   | Gradle & Android Gradle Plugin       | 1.5         | 2024/08/05 | 2024/08/06 | 2024/08/06   |
| 2   | Apply Kotlin                         | 0.5         | 2024/08/06 | 2024/08/06 | 2024/08/06   |
| 3   | Verify functions                     | 4           | 2024/08/07 | 2024/08/09 | 2024/08/14   |
| 4   | Verify after code merged (Mercurial) | 1           | 2024/08/12 | -          | -            |

- 目前的處理狀況: &emsp; 等待最後通知