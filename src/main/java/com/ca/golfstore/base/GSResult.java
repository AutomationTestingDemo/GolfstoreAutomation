package com.ca.golfstore.base;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class GSResult {
     
	private boolean uiSuccess = false;
    private String uiOutputMsg = null;
    private boolean dbSucess = false;
     private LinkedHashMap<String, String> actualMap = null;
     private LinkedHashMap<String, String> expectedMap = null;
     private Map<String, String> actualUIMap = null;
     private Map<String, String> expectedUIMap = null;
     private List<LinkedHashMap<String, String>> actualUIMapList = null;
     private List<LinkedHashMap<String, String>> expectedUIMapList = null;

     public GSResult() {
     }

     public GSResult(boolean uiSuccess, String uiOutputMsg, boolean dbSucess,
             LinkedHashMap<String, String> actualMap,
             LinkedHashMap<String, String> expectedMap , LinkedHashMap<String, String> expectedUIMap,LinkedHashMap<String, String> actualUIMap) {
        super();
        this.uiSuccess = uiSuccess;
        this.uiOutputMsg = uiOutputMsg;
        this.dbSucess = dbSucess;
        this.actualMap = actualMap;
        this.expectedMap = expectedMap;
        this.expectedUIMap = expectedUIMap;
        this.actualUIMap = actualUIMap;
  }
  

  public GSResult(boolean uiSuccess, String uiOutputMsg, boolean dbSucess,
             LinkedHashMap<String, String> actualMap,
             LinkedHashMap<String, String> expectedMap , LinkedHashMap<String, String> expectedUIMap,LinkedHashMap<String, String> actualUIMap,
             List<LinkedHashMap<String, String>> actualUIMapList,List<LinkedHashMap<String, String>> expectedUIMapList) {
        super();
        this.uiSuccess = uiSuccess;
        this.uiOutputMsg = uiOutputMsg;
        this.dbSucess = dbSucess;
        this.actualMap = actualMap;
        this.expectedMap = expectedMap;
        this.expectedUIMap = expectedUIMap;
        this.actualUIMap = actualUIMap;
        this.actualUIMapList=actualUIMapList;
        this.expectedUIMapList=expectedUIMapList;
  }
     public LinkedHashMap<String, String> getActualMap() {
           return actualMap;
     }

     public void setActualMap(LinkedHashMap<String, String> actualMap) {
           this.actualMap = actualMap;
     }

     public LinkedHashMap<String, String> getExpectedMap() {
           return expectedMap;
     }

     public void setExpectedMap(LinkedHashMap<String, String> expectedMap) {
           this.expectedMap = expectedMap;
     }
     
     public List<LinkedHashMap<String, String>> getActualUIMapList() {
         return actualUIMapList;
   }

   public void setActualUIMapList(
              List<LinkedHashMap<String, String>> actualUIMapList) {
         this.actualUIMapList = actualUIMapList;
   }

   public List<LinkedHashMap<String, String>> getExpectedUIMapList() {
         return expectedUIMapList;
   }

   public void setExpectedUIMapList(
              List<LinkedHashMap<String, String>> expectedUIMapList) {
         this.expectedUIMapList = expectedUIMapList;
   }

   public boolean isUiSuccess() {
         return uiSuccess;
   }

   public void setUiSuccess(boolean uiSuccess) {
         this.uiSuccess = uiSuccess;
   }

   public String getUiOutputMsg() {
         return uiOutputMsg;
   }

   public void setUiOutputMsg(String uiOutputMsg) {
         this.uiOutputMsg = uiOutputMsg;
   }

   public boolean isDbSucess() {
         return dbSucess;
   }

   public void setDbSucess(boolean dbSucess) {
         this.dbSucess = dbSucess;
   }

   
   public Map<String, String> getActualUIMap() {
         return actualUIMap;
   }

   public void setActualUIMap(Map<String, String> actualUIMap) {
         this.actualUIMap = actualUIMap;
   }

   public Map<String, String> getExpectedUIMap() {
         return expectedUIMap;
   }

   public void setExpectedUIMap(Map<String, String> expectedUIMap) {
         this.expectedUIMap = expectedUIMap;
   }

   @Override
   public String toString() {
         return "RAResult [uiSuccess=" + uiSuccess + ", uiOutputMsg="
                   + uiOutputMsg + ", dbSucess=" + dbSucess + ", actualMap="
                   + actualMap + ", expectedMap=" + expectedMap + "]";
   }


}