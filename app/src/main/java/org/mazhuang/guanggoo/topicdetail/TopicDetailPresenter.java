package org.mazhuang.guanggoo.topicdetail;

import org.mazhuang.guanggoo.data.NetworkTaskScheduler;
import org.mazhuang.guanggoo.data.OnResponseListener;
import org.mazhuang.guanggoo.data.entity.TopicDetail;
import org.mazhuang.guanggoo.data.task.GetTopicDetailTask;
import org.mazhuang.guanggoo.util.UrlUtil;

/**
 * Created by mazhuang on 2017/9/17.
 */

public class TopicDetailPresenter implements TopicDetailContract.Presenter {

    private TopicDetailContract.View mView;

    private String mUrl;

    public TopicDetailPresenter(TopicDetailContract.View view, String url) {
        mView = view;
        mUrl = url;
        mView.setPresenter(this);
    }

    @Override
    public void getTopicDetail() {
        NetworkTaskScheduler.getInstance().execute(new GetTopicDetailTask(mUrl, new OnResponseListener<TopicDetail>() {
            @Override
            public void onSucceed(TopicDetail data) {
                mView.onGetTopicDetailSucceed(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.onGetTopicDetailFailed(msg);
            }
        }));
    }

    @Override
    public void getMoreComments(int page) {
        String url = mUrl.replaceAll("#reply\\d+", "");
        NetworkTaskScheduler.getInstance().execute(new GetTopicDetailTask(UrlUtil.appendPage(url, page), new OnResponseListener<TopicDetail>() {
            @Override
            public void onSucceed(TopicDetail data) {
                mView.onGetMoreCommentsSucceed(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.onGetMoreCommentsFailed(msg);
            }
        }));
    }
}