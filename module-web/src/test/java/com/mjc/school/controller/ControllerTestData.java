package com.mjc.school.controller;

import com.mjc.school.web.controller.PathConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ControllerTestData {

    private List<String> pathList;
    private List<String> deletionPathList;
    private List<String> testData;
    private List<String> updatedTestData;

    public ControllerTestData() throws JSONException {
        this.pathList = initPathList();
        this.deletionPathList = initDeletionPathList();
        this.testData = initialTestData();
        this.updatedTestData = updatedTestData();
    }

    private List<String> initPathList() {
        return pathList = new ArrayList<>() {
            {
                add(PathConstants.TAG_PATH);
                add(PathConstants.AUTHOR_PATH);
                add(PathConstants.NEWS_PATH);
                add(PathConstants.COMMENT_PATH);
            }
        };
    }


    private List<String> initDeletionPathList() {
        return deletionPathList = new ArrayList<>() {
            {
                add(PathConstants.COMMENT_PATH);
                add(PathConstants.NEWS_PATH);
                add(PathConstants.AUTHOR_PATH);
                add(PathConstants.TAG_PATH);
            }
        };
    }

    private List<String> initialTestData() throws JSONException {
        return testData = new ArrayList<>() {
            {
                add(new JSONObject()
                        .put("name", "TagNameTest")
                        .toString());
                add(new JSONObject()
                        .put("name", "AuthorNameTest")
                        .toString());
                add(new JSONObject()
                        .put("title", "NewsTitleTest")
                        .put("content", "NewsContentTest")
                        .put("authorId", 1L)
                        .put("tagsId", new JSONArray().put(1L))
                        .toString());
                add(new JSONObject()
                        .put("content", "CommentContentTest")
                        .put("newsId", 1L)
                        .toString());
            }
        };
    }

    private List<String> updatedTestData() throws JSONException {
        return updatedTestData = new ArrayList<>() {
            {
                add(new JSONObject()
                        .put("name", "TagNameUpd")
                        .toString());
                add(new JSONObject()
                        .put("name", "AuthorNameUpd")
                        .toString());
                add(new JSONObject()
                        .put("title", "TitleUpd")
                        .put("content", "NewsContentTest")
                        .put("authorId", 1L)
                        .put("tagsId", new JSONArray().put(1L))
                        .toString());
                add(new JSONObject()
                        .put("content", "CommentContentUpd")
                        .put("newsId", 1L)
                        .toString());
            }
        };
    }

    public List<String> getPathList() {
        return pathList;
    }

    public List<String> getDeletionPathList() {
        return deletionPathList;
    }

    public List<String> getTestData() {
        return testData;
    }

    public List<String> getUpdatedTestData() {
        return updatedTestData;
    }
}
