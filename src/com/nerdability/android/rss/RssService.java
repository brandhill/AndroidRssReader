package com.nerdability.android.rss;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nerdability.android.ArticleListFragment;
import com.nerdability.android.adapter.ArticleListAdapter;
import com.nerdability.android.db.DbAdapter;
import com.nerdability.android.rss.domain.Article;
import com.nerdability.android.rss.domain.GoogleHotTrendItem;
import com.nerdability.android.rss.parser.RssHandler;


public class RssService extends AsyncTask<String, Void, List<GoogleHotTrendItem>> {

    public static final String TAG = "RssService";
	private ProgressDialog progress;
	private Context context;
	private ArticleListFragment articleListFrag;

	public RssService(ArticleListFragment articleListFragment) {
		context = articleListFragment.getActivity();
		articleListFrag = articleListFragment;
		progress = new ProgressDialog(context);
		progress.setMessage("Loading...");
	}


	protected void onPreExecute() {
		Log.d(TAG, "PRE EXECUTE");
		progress.show();
	}


//	protected  void onPostExecute(final List<Article>  articles) {
//		Log.d(TAG, "POST EXECUTE");
//		articleListFrag.getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                for (Article a : articles) {
//                    Log.d("DB", "Searching DB for GUID: " + a.getGuid());
//                    DbAdapter dba = new DbAdapter(articleListFrag.getActivity());
//                    dba.openToRead();
//                    Article fetchedArticle = dba.getBlogListing(a.getGuid());
//                    dba.close();
//                    if (fetchedArticle == null) {
//                        Log.d("DB", "Found entry for first time: " + a.getTitle());
//                        dba = new DbAdapter(articleListFrag.getActivity());
//                        dba.openToWrite();
//                        dba.insertBlogListing(a.getGuid());
//                        dba.close();
//                    } else {
//                        a.setDbId(fetchedArticle.getDbId());
//                        a.setOffline(fetchedArticle.isOffline());
//                        a.setRead(fetchedArticle.isRead());
//                    }
//                }
//                ArticleListAdapter adapter = new ArticleListAdapter(articleListFrag.getActivity(), articles);
//                articleListFrag.setListAdapter(adapter);
//                adapter.notifyDataSetChanged();
//
//            }
//        });
//		progress.dismiss();
//	}


    protected  void onPostExecute(final List<GoogleHotTrendItem>  itemList) {
        Log.d(TAG, "POST EXECUTE");
        articleListFrag.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (GoogleHotTrendItem item : itemList) {
                    Log.d(TAG, item.toString());
                }


            }
        });
        progress.dismiss();
    }

	@Override
	protected List<GoogleHotTrendItem> doInBackground(String... urls) {
		String feed = urls[0];

		URL url = null;
		try {

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			url = new URL(feed);
			RssHandler rh = new RssHandler();

			xr.setContentHandler(rh);
			xr.parse(new InputSource(url.openStream()));

			Log.d(TAG, "PARSING FINISHED");
			return rh.getGoogleHotTrendItemList();

		} catch (IOException e) {
			Log.e(TAG, e.getMessage() + " >> " + e.toString());
		} catch (SAXException e) {
			Log.e(TAG, e.toString());
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			Log.e(TAG, e.toString());
		}

		return null;

	}
}