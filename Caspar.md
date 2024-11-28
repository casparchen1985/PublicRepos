# (Processing) Add E710 into UHF module list
| No. | 任務名稱         | 工期 (Days) | 開始時間 | 完成時間 | 實際完成時間 |
| --- | ---------------- | ----------- | -------- | -------- | ------------ |
| 1   | Implementation   | 4           | 11/19    | 11/20    | -            |
| 2   | Build OS image   | -           | -        | -        | -            |
| 3   | SIT Verification | -           | -        | -        | -            |
| 4   | Debug            | -           | -        | -        | -            |
| 5   | Release          | -           | -        | -        | -            |


- Links:</br>
[None]

- 目前的處理狀況:  
11/19 確定 UHF 裝置清單會由 RFID Service 統一維護並提供, 故原先在 Enterprise hard code 的部分就要拔掉.  
再新增取回裝置清單的操作, 並且與現有流程整合, 再加上 Exception 情境處理
</br></br>
11/20 有提供測試版 apk 讓 Roy, JC 試用, Roy 有反應 Enterprise 並未如預期正常顯示預設裝置 "R2000".</br></br>
11/21 原因是 新的 AIDL (統一由 Service 存/取相關資料) 跟 舊有機制 (同樣的功能/操作, 寫兩份各自放在 Service / UI)  有打架的地方,  
在調整過 Caching 的流程與做法, 目前沒有再發現新問題.</br></br>
11/22 目前正在處理 code merge 的衝突, 目標今天釋出 APK 與 RFID Service 一同包入 OS image (private 或 daily 未定)
</br>
</br>

# (Pending) Lock system status bar / Hide system navigation bar
| No. | 任務名稱                        | 工期 (Days) | 開始時間 | 完成時間 | 實際完成時間 |
| --- | ------------------------------- | ----------- | -------- | -------- | ------------ |
| 1   | Study requirement & Discussion  | -           | -        | -        | -            |
| 2   | Status Bar - Trace code         | 1           | 11/05    | 11/05    | 11/06        |
| 3   | Status Bar - Implement feature  | 同上        | -        | -        | -            |
| 4   | Navigation Bar - Solution study | 7          | 11/06    | 11/08    |  11/18        |


- Links:</br>
[TSA-909 (RS51)](https://jira.cipherlab.com.tw/browse/TSA-909)  
[DUM-77](https://jira.cipherlab.com.tw/browse/DUM-77)

- 目前的處理狀況:  
11/15 跟 Ocer 諮詢完之後大概有個方向, 計畫繞過 UI (Fragment) 直接呼叫底層控制元件, 但須了解底層流程才有辦法正確呼叫.  
而原先 RS38 無法透過 Setting.Secure.putInt() 方式去更改系統的手勢操作, 在 Rick (BSP) 幫忙修改後的 Private OS Image 上可以進行切換, 但仍無法完整/完美地呈現 Navigation Bar</br></br>
11/18 透過 AIDL 的方式呼叫 OverlayService \<IOverlayManager> 元件的  setEnabledExclusive() 方法達到跟系統相同的切換方式.  
已將目前現有的方案 Demo 給  Justin.Wang 看過, 他會再跟客戶回覆. 
</br>
</br>

# (Pending) Remove unnecessary heater status/information on RK95CC
| No. | 任務名稱                       | 工期 (Days) | 開始時間 | 完成時間 | 實際完成時間 |
| --- | ------------------------------ | ----------- | -------- | -------- | ------------ |
| 1   | Study requirement & Discussion | 0.5         | 10/14    | 10/14    | 10/14        |
| 2   | Trace code                     | 同上        | -        | -        | -            |
| 3   | Remove feature                 | 1           | 10/14    | 10/15    | 10/14        |
| 4   | Code Review                    | 0.5         | 10/24    | 10/25    | 10/25        |
| 5   | Paper Work                     | -           | -        | -        | -            |

- Links:</br>
[P_RK95_S_I-61](https://jira.cipherlab.com.tw/browse/P_RK95_S_I-61)  

- 目前的處理狀況:  
11/21 我用手邊非 5800 讀頭的 CC 機種驗證 OS 12, OS 9 的機種可以正常開啟 Heater 畫面與相關設定.  
目前在處理 E710 的需求, 等 E710 之後再確認要不要等 OS 9 的 dimming mode 一起進測
</br>
</br>
