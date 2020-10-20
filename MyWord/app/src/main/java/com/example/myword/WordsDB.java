package com.example.myword;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myword.dummy.Words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordsDB {
    static Integer idd;
    private static WordsDBHelper mDbHelper=null;
    private static WordsDB instance=new WordsDB();
    public static WordsDB getWordsDB(){
        return WordsDB.instance;
    }
    private WordsDB() {

        if (mDbHelper == null) {
            mDbHelper = new WordsDBHelper(WordsApplication.getContext());
            System.out.println("WordsDB");

        }
    }
//    public void close() {
//        if (mDbHelper != null)
//            mDbHelper.close();
//    }

    //获得单个单词的全部信息
    public Words.WordDescription getSingleWord(String id) {
        System.out.println("another"+id);
        String selectSql = "select * from words where _id = ? ";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectSql,new String[]{id});
        Words.WordDescription item =null;
        while(cursor.moveToNext())
        {
            item=new Words.WordDescription(cursor.getString(0).toString(),cursor.getString(1).toString(),
                    cursor.getString(2).toString(),cursor.getString(3).toString());
        }
        db.close();
        return item;

    }
    //得到全部单词列表
    public ArrayList<Map<String, String>> getAllWords() {
        ArrayList<Map<String,String>> items=new ArrayList<Map<String,String>>();
        String selectSql = "select * from words";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectSql,null);
        if(cursor.getCount()==0)
            WordsDB.idd=0;
        else{
        while(cursor.moveToNext())
        {
            Map<String,String> item=new HashMap<String,String>();
            item.put(Words.Word._ID,cursor.getString(0).toString());
            item.put(Words.Word.COLUMN_NAME_WORD,cursor.getString(1).toString());
            items.add(item);
        }
        cursor.moveToLast();
        WordsDB.idd=Integer.parseInt(cursor.getString(0));}
        db.close();
        return items;
    }
    //将游标转化为单词列表
    private ArrayList<Map<String, String>> ConvertCursor2WordList(Cursor cursor) {
        ArrayList<Map<String,String>> items=new ArrayList<Map<String,String>>();
        while(cursor.moveToNext()) {
            Map<String, String> item = new HashMap<String, String>();
            item.put(Words.Word._ID,cursor.getString(0).toString());
            item.put(Words.Word.COLUMN_NAME_WORD,cursor.getString(1).toString());
            items.add(item);
        }
        return items;

    }

    public void Insert(String strWord, String strMeaning, String strSample) {

        String insertSql ="insert into words(_id,word,meaning,sample) values (?,?,?,?)";
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(insertSql,new String[]{WordsDB.idd+1+"",strWord,strMeaning,strSample});
        db.close();

    }
    public void Delete(String strId) {
        String deleteSql = "delete from words where _id= ?";
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(deleteSql,new String[]{strId});
        db.close();
    }
    public void Update(String strId, String strWord, String strMeaning, String strSample) {
        String updateSql = "update words set word= ? , meaning= ? ,sample=? where _id=?";
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(updateSql,new String[]{strWord,strMeaning,strSample,strId});
        db.close();
    }
    public ArrayList<Map<String, String>> Search(String strWordSearch) {
        //String searchSql="select _id,word from words where word=?";
        String sql="select * from words where word like ? order by word desc";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,new String[]{"%"+strWordSearch+"%"});
        return  ConvertCursor2WordList(cursor);
    }



}
