package com.onliner.parser_onliner;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Set;

public class TaskToParse extends Task<Void> {

    private boolean isCollectReview;
    private ArrayList<String> linksCategories;
    private boolean isOnSale;
    private DatePicker dateStart;
    private DatePicker dateEnd;
    private Set<CheckBox> setCheckBox;
    private Tab brendTab;
    private Tab categoriesTab;
    private Tab reviewTab;
    private Button startButton;
    private Button chooseButton;
    private String pathToSave;

    public TaskToParse(boolean isCollectReview, ArrayList<String> linksCategories, boolean isOnSale, DatePicker dateStart, DatePicker dateEnd, Set<CheckBox> setCheckBox, Tab brendTab, Tab categoriesTab, Tab reviewTab, Button startButton, Button chooseButton, String pathToSave) {
        this.isCollectReview = isCollectReview;
        this.linksCategories = linksCategories;
        this.isOnSale = isOnSale;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.setCheckBox = setCheckBox;
        this.brendTab = brendTab;
        this.categoriesTab = categoriesTab;
        this.reviewTab = reviewTab;
        this.startButton = startButton;
        this.chooseButton = chooseButton;
        this.pathToSave = pathToSave;
    }

    public TaskToParse(boolean isCollectReview, ArrayList<String> linksCategories, boolean isOnSale, Tab brendTab, Tab categoriesTab, Tab reviewTab, Button startButton, Button chooseButton, String pathToSave) {
        this.isCollectReview = isCollectReview;
        this.linksCategories = linksCategories;
        this.isOnSale = isOnSale;
        this.brendTab = brendTab;
        this.categoriesTab = categoriesTab;
        this.reviewTab = reviewTab;
        this.startButton = startButton;
        this.chooseButton = chooseButton;
        this.pathToSave = pathToSave;
    }

    @Override
    protected Void call() throws Exception {
        ArrayList<Nomenclatures> nomenclatures = new ArrayList<>();
        ArrayList<Reviews> reviewsArrayList = new ArrayList<>();

        //сбор общей информации
        for (String link : linksCategories) {

            int id, countReview;
            double averageRating;
            String article, fullName, category, linkToReview, linkToReviewJson;

            String out = getUrlContent(link);
            JSONObject jsonObject = new JSONObject(out);

            int current, last;

            current = jsonObject.getJSONObject("page").getInt("current");
            last = jsonObject.getJSONObject("page").getInt("last");

            for (int i = 0; i < jsonObject.getJSONArray("products").length(); i++) {
                if (isOnSale && jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("prices") == null) continue;

                id = jsonObject.getJSONArray("products").optJSONObject(i).getInt("id");
                article = jsonObject.getJSONArray("products").getJSONObject(i).getString("name");
                category = jsonObject.getJSONArray("products").getJSONObject(i).getString("name_prefix");
                fullName = jsonObject.getJSONArray("products").getJSONObject(i).getString("full_name");
                linkToReview = jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("reviews").getString("html_url");
                linkToReviewJson = jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("reviews").getString("url");
                countReview = jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("reviews").getInt("count");
                averageRating = Double.parseDouble(String.valueOf(jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("reviews").getInt("rating"))) / 10;

                nomenclatures.add(new Nomenclatures(id, article, category, fullName, linkToReview, linkToReviewJson, countReview, averageRating));
            }
            if (current < last) {
                for (int i = 2; i <= last; i++) {
                    link += "&page=" + i;
                    out = getUrlContent(link);
                    jsonObject = new JSONObject(out);

                    for (int j = 0; j < jsonObject.getJSONArray("products").length(); j++) {
                        id = jsonObject.getJSONArray("products").optJSONObject(j).getInt("id");
                        article = jsonObject.getJSONArray("products").getJSONObject(j).getString("name");
                        category = jsonObject.getJSONArray("products").getJSONObject(j).getString("name_prefix");
                        fullName = jsonObject.getJSONArray("products").getJSONObject(j).getString("full_name");
                        linkToReview = jsonObject.getJSONArray("products").getJSONObject(j).getJSONObject("reviews").getString("html_url");
                        linkToReviewJson = jsonObject.getJSONArray("products").getJSONObject(j).getJSONObject("reviews").getString("url");
                        countReview = jsonObject.getJSONArray("products").getJSONObject(j).getJSONObject("reviews").getInt("count");
                        averageRating = Double.parseDouble(String.valueOf(jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("reviews").getInt("rating"))) / 10;

                        nomenclatures.add(new Nomenclatures(id, article, category, fullName, linkToReview, linkToReviewJson, countReview, averageRating));
                    }
                }
            }
        }

        //Сбор самих отзывов
        if (isCollectReview) {
            int id, rating;
            String category, article, fullName, author, dateReview, summary, textReview, linkToReviews;
            String dignity = "Достоинства: ";
            String disadvantages = "Недостатки: ";

            for (Nomenclatures nom : nomenclatures) {
                String linkToParseReview = getUrlContent(nom.getLinkToReviewsJson() + "?order=created_at:desc");
                JSONObject object = new JSONObject(linkToParseReview);

                int current, last;
                current = object.getJSONObject("page").getInt("current");
                last = object.getJSONObject("page").getInt("last");
                if (object.getInt("total") != 0) {

                }
            }
        }


        return null;
    }

    private static String getUrlContent(String urlAddress) {
        StringBuilder x = new StringBuilder();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                x.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x.toString();
    }
}
