package id.sch.smkn2cikbar.easyschedule.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import id.sch.smkn2cikbar.easyschedule.GuruActivity;
import id.sch.smkn2cikbar.easyschedule.R;
import id.sch.smkn2cikbar.easyschedule.models.progressDialogModel;

import static id.sch.smkn2cikbar.easyschedule.adapters.DetectConnection.isNetworkStatusAvialable;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuruTwitterFragment extends Fragment {

    private Toolbar toolbar;
    private GuruActivity guruActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String baseURl = "https://twitter.com";

    private static final String widgetInfo = "<a class=\"twitter-timeline\" href=\"https://twitter.com/smkn2cikbar\">Tweets by smkn2cikbar</a>" +
            "<script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";

    public GuruTwitterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        guruActivity = (GuruActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        final WebView webView = (WebView) view.findViewById(R.id.timeline_webview);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkStatusAvialable (getActivity().getApplicationContext())) {
                    webView.reload();
                    webView.getSettings().setDomStorageEnabled(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new MyWebViewClient());
                    webView.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);
                    progressDialogModel.pdMenyiapkanDataLogin(getActivity());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
                    webView.loadDataWithBaseURL(null, "<html><body><img width=\"100%\" height=\"100%\" src=\"file:///android_res/drawable/offline.png\"></body></html>", "text/html", "UTF-8", null);
                    progressDialogModel.hideProgressDialog();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        setupToolbar();

        if(isNetworkStatusAvialable (getActivity().getApplicationContext())) {
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new MyWebViewClient());
            webView.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);
            progressDialogModel.pdMenyiapkanDataLogin(getActivity());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
            webView.loadDataWithBaseURL(null, "<html><body><img width=\"100%\" height=\"100%\" src=\"file:///android_res/drawable/offline.png\"></body></html>", "text/html", "UTF-8", null);
            progressDialogModel.hideProgressDialog();
            swipeRefreshLayout.setRefreshing(false);
        }

        return view;
    }

    public class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(isNetworkStatusAvialable (getActivity().getApplicationContext())) {
                view.loadUrl(url);
                progressDialogModel.pdMenyiapkanDataLogin(getActivity());
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
                view.loadDataWithBaseURL(null, "<html><body><img width=\"100%\" height=\"100%\" src=\"file:///android_res/drawable/offline.png\"></body></html>", "text/html", "UTF-8", null);
                progressDialogModel.hideProgressDialog();
                swipeRefreshLayout.setRefreshing(false);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialogModel.hideProgressDialog();
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        guruActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle(R.string.twitter_fragment_title);
        toolbar.setSubtitle(R.string.twitter_fragment_subtitle);
        guruActivity.setSupportActionBar(toolbar);
    }

}
