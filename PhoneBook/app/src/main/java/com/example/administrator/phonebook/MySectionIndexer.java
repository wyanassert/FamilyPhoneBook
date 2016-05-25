package com.example.administrator.phonebook;

import android.util.Log;
import android.widget.SectionIndexer;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/5/23.
 */
public class MySectionIndexer implements SectionIndexer {
    private String[] mSections;
    private int[] mPositions;
    private int mCount;

    public MySectionIndexer(String[] sections, int[] counts) {
        if (sections == null || counts == null) {
            throw new NullPointerException();
        }
        if (sections.length != counts.length) {
            throw new IllegalArgumentException(
                    "The sections and counts arrays must have the same length");
        }
        this.mSections = sections;
        mPositions = new int[counts.length];
        int position = 0;
        for (int i = 0; i < counts.length; i++) {
            if(mSections[i] == null) {
                mSections[i] = "";
            } else {
                mSections[i] = mSections[i].trim();
            }

            mPositions[i] = position;
            position += counts[i];

            Log.i("MySectionIndexer", "counts[" + i + "]:" + counts[i]);
        }
        mCount = position;
    }

    @Override
    public Object[] getSections() {
        return mSections;
    }

    public String getSection(int section) {
        return mSections[section];
    }

    @Override
    public int getPositionForSection(int section) {
        if (section < 0 || section >= mSections.length) {
            return -1;
        }
        return mPositions[section];
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position < 0 || position >= mCount) {
            return -1;
        }
        int index = Arrays.binarySearch(mPositions, position);
        return index >= 0 ? index : -index - 2; //当index小于0时，返回-index-2，
    }
}
