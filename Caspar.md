# (Processing) Lock system status bar / Hide system naviation bar
| No. | 任務名稱                        | 工期 (Days) | 開始時間 | 完成時間 | 實際完成時間 |
| --- | ------------------------------- | ----------- | -------- | -------- | ------------ |
| 1   | Study requirement & Discussion  | -           | -        | -        | -            |
| 2   | Status Bar - Trace code         | 1           | 11/05    | 11/05    | 11/06        |
| 3   | Status Bar - Implement feature  | 同上        | -        | -        | -            |
| 4   | Navigation Bar - Solution study | -          | 11/06    | 11/08    |              |


- Links:</br>
[TSA-909 (RS51)](https://jira.cipherlab.com.tw/browse/TSA-909)  
[DUM-77](https://jira.cipherlab.com.tw/browse/DUM-77)

- 目前的處理狀況:  
11/11 取得 Ocer 專案上提供的方式, 同時在看 Android Souce code  </br></br>
11/12 自己試驗了建立對應的 Internal interface 藉以使用 Internal method 無果;  
接著試著用 Reflection 的方式呼叫但 OS 並沒有對應的反應(RS38)  </br></br>
11/13 看網路上的分享, 有人自己打包新版本OS 的 Framework.jar + Android.jar, 並嘗試著自己包 .jar 檔案用以存取 internal method.  
同時間請 Ocer 解釋 CipherLAB Home 是怎麼建置/使用 internal api 的, 聽完後因後續維護不便故決定放棄 自製 .jar 及 Reflection 作法.  
然而, 在 app 端直接呼叫 `Settings.Secure.getInt(getContentResolver(), "navigation_mode", 0)` 並未成功寫入, 就去找 Rick (BSP) 詢問, 經實驗得知 RS38 並沒有設定足夠權限, 所以請 Rick 協助修改權限後嘗試 Build private OS imagee  </br></br>
11/14 經過約半天的時間 RS38 打包了幾次都失敗,&emsp;`   NOT_FOUND://home/git/HDD2T/rs38t/merged_out/out/target/product/st6866a//super.img ERROR!!! ERROR!!! ERROR!!!`  
後來為節省時間, 嘗試用 RS36 來試驗/驗證設定值是否寫入, 結果是可以成功寫入設定值. 但是 Navigation Bar 在設定回 3 button 的模式時, 底色背景並未恢復.  
![vlcsnap-2024-11-15-09h45m58s548.png](/attachment/6736aa6f80680702ecbe4931)  
![01.jpg](/attachment/6736a8fb80680702ecbe42a2)  
傍晚搜尋到 [對岸的工程師分享文](https://blog.csdn.net/qq_23452385/article/details/133284710)  從中可以知道 System Overlay 的流程時序, 看能不能透過系統既有的機制與流程, 呼叫到正確的 function 使行為正常  
[點我看大圖](https://i-blog.csdnimg.cn/blog_migrate/0aee779088bfe881b9bcbef4fe6ed2f6.png#pic_center)  
流程截圖   
![02.png](/attachment/6736ab4180680702ecbe5094)  </br></br>

</br>
</br>

# (Done) Control the green indicator for the Camera/MIC on RS36
| No. | 任務名稱                       | 工期 (Days) | 開始時間 | 完成時間 | 實際完成時間 |
| --- | ------------------------------ | ----------- | -------- | -------- | ------------ |
| 1   | Study requirement & Discussion | 0.5         | 10/18    | 10/18    | 10/18        |
| 2   | Trace code                     | 同上        | -        | -        | -            |
| 3   | Implement feature              | 2           | 10/21    | 10/22    | 10/23        |
| 4   | Code Review                    | 0.5         | 10/24    | 10/25    | 10/25        |
| 5   | Build OS Image                 | -           | -        | -        | -            |
| 6   | Paper Work                     | 0.5         | 11/13    | 11/14    | 11/14        |

- Links:</br>
[NTSA-1367](https://jira.cipherlab.com.tw/browse/NTSA-1367)  
[NTSA-1374](https://jira.cipherlab.com.tw/browse/NTSA-1374)  
[P_RS36_E-767](https://jira.cipherlab.com.tw/browse/P_RS36_E-767)  

- 目前的處理狀況:  
11/11 SIT 因既有的 RK26 Dimming JiRA [SW_ES-10 (16 Aug)](https://jira.cipherlab.com.tw/browse/SW_ES-10) 判定此版 Fail.  
經反映此現象非本次 app 變動而造成的, 最後改為 Conditional Pass.  </br></br>
11/14 SW Released.
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
11/05 由於 RK95 Build server 空間問題, 所以 DailyBuild 只會有 RK95 user 版.  
因此需求優先度普通, 所以就打算等 RK95 formal image 出來(會有 CC image), 再搭配 apk 送測
</br>
</br>
