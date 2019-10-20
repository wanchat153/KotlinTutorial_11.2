package com.example.kotlintutorial_11_2

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.ListView
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

private const val TAG = "DownloadData"

class DownloadData(private val callBack: DownloaderCallBack) : AsyncTask<String, Void, String>() {

    interface DownloaderCallBack {
        fun onDataAvailable(data: List<FeedEntry>)
    }

    override fun onPostExecute(result: String) {

        val parseApplications = ParseApplications()
        if(result.isNotEmpty()) {
            parseApplications.parse(result)
        }

        callBack.onDataAvailable(parseApplications.applications)
    }

//                val arrayAdapter = ArrayAdapter<FeedEntry>(propContext, R.layout.list_item, parseApplications.applications)
//                propListView.adapter = arrayAdapter

    }

    override fun doInBackground(vararg url: String): String {
        Log.d(TAG, "doInBackground: stars with ${url[0]}")
        val rssFeed = downloadXML(url[0])
        if (rssFeed.isEmpty()){
            Log.e(TAG, "doInBackground: Error downloading")
        }
        return rssFeed
    }

    private  fun downloadXML(urlPath: String): String {
        try {
            return URL(urlPath).readText()
        } catch(e: MalformedURLException) {
            Log.d(TAG, "downloadXML: Invalid URL " + e.message)
        } catch(e: IOException) {
            Log.d(TAG, "downloadXML: IO Exception reading data " + e.message)
        } catch(e: SecurityException) {
            Log.d(TAG, "downloadXML: Security exception. Needs permission? " + e.message)
//            e.printStackTrace()
        }

        return ""
    }
}