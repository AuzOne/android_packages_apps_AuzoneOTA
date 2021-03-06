package com.chummy.auzoneroms.updates.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.AsyncTask;
import android.util.Log;

import com.chummy.auzoneroms.updates.activities.AddonActivity;
import com.chummy.auzoneroms.updates.utils.Constants;

public class DownloadAddonProgress extends AsyncTask<Long, Integer, Void> implements Constants {
    public final String TAG = this.getClass().getSimpleName();

    private DownloadManager mDownloadManager;
    private int mViewId;
    private boolean mIsRunning = true;

    public DownloadAddonProgress(Context context, DownloadManager downloadManager, int id) {
        mDownloadManager = downloadManager;
        mViewId = id;
    }

    @Override
    protected void onCancelled() {
        mIsRunning = false;
    }

    @Override
    protected Void doInBackground(Long... params) {
        int previousValue = 0;
        long mDownloadId = params[0];
        while (mIsRunning) {
            DownloadManager.Query q = new DownloadManager.Query();
            q.setFilterById(mDownloadId);

            Cursor cursor = mDownloadManager.query(q);
            cursor.moveToFirst();
            try {
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL ||
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_FAILED) {
                    mIsRunning = false;
                }

                final int bytesDownloaded = cursor.getInt(cursor
                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                final int bytesInTotal = cursor.getInt(cursor
                        .getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                final int progressPercent = (int) ((bytesDownloaded * 100l) / bytesInTotal);

                if (progressPercent != previousValue) {
                    // Only publish every 1%, to reduce the amount of work being done.
                    publishProgress(progressPercent, bytesDownloaded, bytesInTotal);
                    previousValue = progressPercent;
                }
            } catch (CursorIndexOutOfBoundsException e) {
                Log.e(TAG, " " + e.getMessage());
                mIsRunning = false;
            } catch (ArithmeticException e) {
                Log.e(TAG, " " + e.getMessage());
                mIsRunning = false;
            }
            cursor.close();
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        if (DEBUGGING)
            Log.d(TAG, "Updating Progress - " + progress[0] + "%");
        if (mIsRunning) {
            AddonActivity.AddonsArrayAdapter.updateProgress(mViewId, progress[0], false);
        } else {
            AddonActivity.AddonsArrayAdapter.updateProgress(mViewId, 0, true);
        }
    }
}
