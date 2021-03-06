package com.nerdability.android.rss.parser;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nerdability.android.rss.RssService;
import com.nerdability.android.rss.domain.Article;
import com.nerdability.android.rss.domain.GoogleHotTrendItem;


public class RssHandler extends DefaultHandler {

	// Feed and Article objects to use for temporary storage
	private Article currentArticle = new Article();
	private List<Article> articleList = new ArrayList<Article>();

    private List<GoogleHotTrendItem> hotTrendItems = new ArrayList<>();
    private GoogleHotTrendItem currentTrendItem = new GoogleHotTrendItem();

	// Number of articles added so far
	private int articlesAdded = 0;

	// Number of articles to download
	private static final int ARTICLES_LIMIT = 15;

	//Current characters being accumulated
	StringBuffer chars = new StringBuffer();


	public List<Article> getArticleList() {
		return articleList;
	}

    public List<GoogleHotTrendItem> getGoogleHotTrendItemList() {
        return hotTrendItems;
    }

	/* 
	 * This method is called everytime a start element is found (an opening XML marker)
	 * here we always reset the characters StringBuffer as we are only currently interested
	 * in the the text values stored at leaf nodes
	 * 
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        chars = new StringBuffer();
//        Log.d(RssService.TAG, String.format("startElement, url : %s, localName : %s, qName : %s, atts : %s", uri, localName, qName, attributes.toString()));
    }

    /*
         * This method is called everytime an end element is found (a closing XML marker)
         * here we check what element is being closed, if it is a relevant leaf node that we are
         * checking, such as Title, then we get the characters we have accumulated in the StringBuffer
         * and set the current Article's title to the value
         *
         * If this is closing the "entry", it means it is the end of the article, so we add that to the list
         * and then reset our Article object for the next one on the stream
         *
         *
         * (non-Javadoc)
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
         */
    @Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

//        Log.d(RssService.TAG, String.format("endElement, url : %s, localName : %s, qName : %s", uri, localName, qName));


        if (localName.equalsIgnoreCase("title")){
            currentTrendItem.setTitle(chars.toString());
        }
        else if (localName.equalsIgnoreCase("description")){
            currentTrendItem.setDescription(chars.toString());
        }
        else if (localName.equalsIgnoreCase("link")){
            currentTrendItem.setLink(chars.toString());
        }
        else if (localName.equalsIgnoreCase("pubDate")){
            currentTrendItem.setPubDate(chars.toString());
        }
        else if (localName.equalsIgnoreCase("picture")){    //localName : picture, qName : ht:picture
            if(!TextUtils.isEmpty(localName)){
                currentTrendItem.setPicture("http:" +chars.toString());
            }
        }
        else if (localName.equalsIgnoreCase("picture_source")){
            currentTrendItem.setPicture_source(chars.toString());
        }
        else if (localName.equalsIgnoreCase("approx_traffic")){
            currentTrendItem.setApprox_traffic(chars.toString());
        }
        else if (localName.equalsIgnoreCase("news_item_title")){
            currentTrendItem.setNews_item_title(chars.toString());
        }
        else if (localName.equalsIgnoreCase("news_item_snippet")){
            currentTrendItem.setNews_item_snippet(chars.toString());
        }
        else if (localName.equalsIgnoreCase("news_item_url")){
            currentTrendItem.setNews_item_url(chars.toString());
        }
        else if (localName.equalsIgnoreCase("news_item_source")){
            currentTrendItem.setNews_item_source(chars.toString());
        }


        if (localName.equalsIgnoreCase("item")) {
            hotTrendItems.add(currentTrendItem);
            currentTrendItem = new GoogleHotTrendItem();
        }


//        if (localName.equalsIgnoreCase("title")){
//			currentArticle.setTitle(chars.toString());
//		} else if (localName.equalsIgnoreCase("description")){
//			currentArticle.setDescription(chars.toString());
//		} else if (localName.equalsIgnoreCase("published")){
//			currentArticle.setPubDate(chars.toString());
//		} else if (localName.equalsIgnoreCase("id")){
//			currentArticle.setGuid(chars.toString());
//		} else if (localName.equalsIgnoreCase("author")){
//			currentArticle.setAuthor(chars.toString());
//		} else if (localName.equalsIgnoreCase("content")){
//			currentArticle.setEncodedContent(chars.toString());
//		} else if (localName.equalsIgnoreCase("entry")){
//
//		}

//        // Check if looking for article, and if article is complete
//        if (localName.equalsIgnoreCase("entry")) {
//
//            articleList.add(currentArticle);
//
//            currentArticle = new Article();
//
//            // Lets check if we've hit our limit on number of articles
//            articlesAdded++;
//            if (articlesAdded >= ARTICLES_LIMIT)
//            {
//                throw new SAXException();
//            }
//        }





	}


	/* 
	 * This method is called when characters are found in between XML markers, however, there is no
	 * guarante that this will be called at the end of the node, or that it will be called only once
	 * , so we just accumulate these and then deal with them in endElement() to be sure we have all the
	 * text
	 * 
	 * (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
    @Override
	public void characters(char ch[], int start, int length) {
		chars.append(new String(ch, start, length));
	}
}