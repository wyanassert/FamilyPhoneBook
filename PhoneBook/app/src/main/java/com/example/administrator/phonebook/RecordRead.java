package com.example.administrator.phonebook;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class RecordRead {
    private static ArrayList<CallLogCellModel> listCalllinglog;
    private static ArrayList <ArrayList <CallLogCellModel>> allLogArray;
    private static ArrayList<CallLogCellModel> mRecordList;
    public ArrayList<CallLogCellModel> readRecord(Context context) {
        if(listCalllinglog != null)
            return listCalllinglog;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            int perm = context.checkCallingOrSelfPermission("android.permission.READ_CALL_LOG");
            if (perm != PackageManager.PERMISSION_GRANTED)
            {
                return new ArrayList<CallLogCellModel>();
            }
            cursor = contentResolver.query(
                    // CallLog.Calls.CONTENT_URI, Columns, null,
                    // null,CallLog.Calls.DATE+" desc");
                    CallLog.Calls.CONTENT_URI, null, null, null,
                    CallLog.Calls.DATE + " desc");
            if (cursor == null)
                return null;
            mRecordList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.NUMBER));
                int type = cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.TYPE));
                long lDate = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DATE));
                long duration = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DURATION));
                int _new = cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.NEW));
//                Log.e("String", record.toString());
//                      int photoIdIndex = cursor.getColumnIndex(CACHED_PHOTO_ID);
//                      if (photoIdIndex >= 0) {
//                          record.cachePhotoId = cursor.getLong(photoIdIndex);
//                      }
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(lDate);
                boolean isCallIn, isHungUp;
                switch (type)
                {
                    case 1:
                        isCallIn = true;
                        isHungUp = false;
                        break;
                    case 2:
                        isCallIn = false;
                        isHungUp = false;
                        break;
                    case 3:
                        isCallIn = false;
                        isHungUp = true;
                        break;
                    default:
                        continue;
                }
                Log.e("Type", Integer.toString(type));
                CallLogCellModel model;
                model = new CallLogCellModel(name, number, calendar, (int)duration, isCallIn, isHungUp);
                mRecordList.add(model);
            }
            listCalllinglog = new ArrayList<>();
            allLogArray = new ArrayList<>();
            for (int i = 0; i < mRecordList.size(); i++)
            {
                int j = 0;
                for( ; j < allLogArray.size(); j++)
                {
                    ArrayList<CallLogCellModel> tmpArray = allLogArray.get(j);
                    String str1 = tmpArray.get(0).phoneNumber;
                    String str2 = mRecordList.get(i).phoneNumber;
                    Log.e(str1, str2);
                    Log.e("compare", Boolean.toString(str1.equals(str2)));
                    if(str1.equals(str2))
                    {
                        tmpArray.add(mRecordList.get(i));
                        break;
                    }
                }
                if(j == allLogArray.size())
                {
                    ArrayList <CallLogCellModel> tmpArray = new ArrayList<>();
                    tmpArray.add(mRecordList.get(i));
                    allLogArray.add(tmpArray);
                    listCalllinglog.add(mRecordList.get(i));
                }
            }
            return listCalllinglog;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public ArrayList<CallLogCellModel> getArrayIndex(int i)
    {
        if(allLogArray == null || i >= allLogArray.size())
            return null;
        return allLogArray.get(i);
    }

//    public class RecordEntity {
//        String name;
//        String number;
//        int type;   //1 打入， 2 打出， 3 未接
//        long lDate; //时间
//        Calendar callDate;
//        long duration; //时长
//        int _new;
//        @Override
//        public String toString() {
//            //callDate.setTimeInMillis(lDate);
//            return "RecordEntity [toString()=" + name+"," + number+"," + type+"," + transferLongToDate("yyyy-MM-dd HH:mm:ss", lDate) + "," + duration+"," + name+"," + "]";
//        }
//
//        private String transferLongToDate(String dateFormat,Long millSec){
//            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
//            Date date= new Date(millSec);
//            return sdf.format(date);
//        }
//    }
}
