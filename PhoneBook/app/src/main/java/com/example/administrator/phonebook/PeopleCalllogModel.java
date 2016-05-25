package com.example.administrator.phonebook;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wyan on 16/5/24.
 */
public class PeopleCalllogModel implements Serializable{
    ContactModel contact;
    ArrayList <CallLogCellModel> callLogs;
}
