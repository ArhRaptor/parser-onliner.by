package com.onliner.parser_onliner;

import javafx.concurrent.Task;
import javafx.scene.control.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskToParse extends Task<Void>{

    private boolean isCollectReview;
    private ArrayList<String> linksCategories;
    private boolean isOnSale;
    private DatePicker dateStart;
    private DatePicker dateEnd;
    private ArrayList<CheckBox> setCheckBox;
    private Tab brendTab;
    private Tab categoriesTab;
    private Tab reviewTab;
    private Button startButton;
    private Button chooseButton;
    private String pathToSave;
    private ProgressBar progressBar;
    private Label textProgress;

    public TaskToParse(boolean isCollectReview, ArrayList<String> linksCategories, boolean isOnSale, DatePicker dateStart, DatePicker dateEnd, ArrayList<CheckBox> setCheckBox,
                       Tab brendTab, Tab categoriesTab, Tab reviewTab, Button startButton, Button chooseButton, String pathToSave, ProgressBar progressBar, Label textProgress) {
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
        this.progressBar = progressBar;
        this.textProgress = textProgress;
    }

    public TaskToParse(boolean isCollectReview, ArrayList<String> linksCategories, boolean isOnSale, Tab brendTab, Tab categoriesTab, Tab reviewTab, Button startButton, Button chooseButton, String pathToSave, ProgressBar progressBar, Label textProgress) {
        this.isCollectReview = isCollectReview;
        this.linksCategories = linksCategories;
        this.isOnSale = isOnSale;
        this.brendTab = brendTab;
        this.categoriesTab = categoriesTab;
        this.reviewTab = reviewTab;
        this.startButton = startButton;
        this.chooseButton = chooseButton;
        this.pathToSave = pathToSave;
        this.progressBar = progressBar;
        this.textProgress = textProgress;
    }

    @Override
    protected Void call() throws Exception {
        ArrayList<Nomenclatures> nomenclatures = new ArrayList<>();
        ArrayList<Reviews> reviewsArrayList = new ArrayList<>();

        updateMessage("Подключение к сайту.");
        updateProgress(0.01, 1);
        double pathOfFirstPart = 0.44 / linksCategories.size();
        double i1 = pathOfFirstPart;

        //сбор общей информации
        for (String link : linksCategories) {
            updateMessage("Получение данных по ссылке: " + link);

            int id, countReview;
            double averageRating;
            String article, fullName, category, linkToReview, linkToReviewJson;

            String out = getUrlContent(link);
            JSONObject jsonObject = new JSONObject(out);

            int current, last;

            current = jsonObject.getJSONObject("page").getInt("current");
            last = jsonObject.getJSONObject("page").getInt("last");

            for (int i = 0; i < jsonObject.getJSONArray("products").length(); i++) {
                if (isOnSale && jsonObject.getJSONArray("products").getJSONObject(i).isNull("prices"))
                    continue;

                id = jsonObject.getJSONArray("products").optJSONObject(i).getInt("id");
                article = jsonObject.getJSONArray("products").getJSONObject(i).getString("name");
                category = jsonObject.getJSONArray("products").getJSONObject(i).getString("name_prefix");
                fullName = jsonObject.getJSONArray("products").getJSONObject(i).getString("full_name");
                linkToReview = jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("reviews").getString("html_url");
                linkToReviewJson = jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("reviews").getString("url");
                countReview = jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("reviews").getInt("count");
                averageRating = Double.parseDouble(String.valueOf(jsonObject.getJSONArray("products").getJSONObject(i).getJSONObject("reviews").getInt("rating"))) / 10;

                updateMessage("Запись данных по " + fullName);

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
                        averageRating = Double.parseDouble(String.valueOf(jsonObject.getJSONArray("products").getJSONObject(j).getJSONObject("reviews").getInt("rating"))) / 10;

                        updateMessage("Запись данных по " + fullName);

                        nomenclatures.add(new Nomenclatures(id, article, category, fullName, linkToReview, linkToReviewJson, countReview, averageRating));
                    }
                }
            }
            updateProgress(0.01 + i1, 1);
            i1 += pathOfFirstPart;
        }
        updateProgress(0.45, 1);

        double pathOfSecondPart = 0.45 / nomenclatures.size();
        double i2 = pathOfSecondPart;

        //Сбор самих отзывов
        if (isCollectReview) {
            for (Nomenclatures nom : nomenclatures) {
                updateMessage("Получение данных отзывов по: " + nom.getFullName());
                int id, rating;
                String category, article, fullName, author, dateReview, summary, textReview, linkToReviews;
                String dignity = "Достоинства: ";
                String disadvantages = "Недостатки: ";

                SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat siteFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                String dts, dte;

                if (dateStart.getValue() == null) dts = "2000-01-01";
                else dts = String.valueOf(dateStart.getValue());
                if (dateEnd.getValue() == null) dte = siteFormat.format(today);
                else dte = String.valueOf(dateEnd.getValue());

                Date d1 = siteFormat.parse(dts);
                Date d2 = siteFormat.parse(dte);

                String linkToParseReview = getUrlContent(nom.getLinkToReviewsJson() + "?order=created_at:desc");
                JSONObject object = new JSONObject(linkToParseReview);

                int current, last;
                current = object.getJSONObject("page").getInt("current");
                last = object.getJSONObject("page").getInt("last");
                if (object.getInt("total") != 0) {
                    for (int i = 0; i < object.getJSONArray("reviews").length(); i++) {
                        id = nom.getId();
                        category = nom.getCategory();
                        article = nom.getArticle();
                        fullName = nom.getFullName();
                        dignity = "Достоинства: ";
                        disadvantages = "Недостатки: ";

                        author = object.getJSONArray("reviews").getJSONObject(i).getJSONObject("author").getString("name");
                        summary = object.getJSONArray("reviews").getJSONObject(i).getString("summary");
                        textReview = object.getJSONArray("reviews").getJSONObject(i).getString("text");
                        dignity += object.getJSONArray("reviews").getJSONObject(i).getString("pros");
                        disadvantages += object.getJSONArray("reviews").getJSONObject(i).getString("cons");
                        String fullDate = object.getJSONArray("reviews").getJSONObject(i).getString("created_at");
                        dateReview = fullDate.substring(0, fullDate.indexOf("T"));
                        rating = object.getJSONArray("reviews").getJSONObject(i).getInt("rating");
                        linkToReviews = nom.getLinkToReviews();

                        Date d3 = siteFormat.parse(dateReview);
                        if ((d1.equals(d3) || d1.before(d3)) && (d2.equals(d3) || d2.after(d3))) {
                            dateReview = myFormat.format(d3);
                            reviewsArrayList.add(new Reviews(id, category, article, fullName, author, summary, textReview, dignity, disadvantages, dateReview, rating, linkToReviews));
                        }
                    }
                }
                if (current < last) {
                    for (int i = 2; i <= last; i++) {

                        String linkToParseReview1 = getUrlContent(nom.getLinkToReviewsJson() + "?order=created_at:desc&page=" + i);
                        JSONObject object2 = new JSONObject(linkToParseReview1);

                        for (int j = 0; j < object2.getJSONArray("reviews").length(); j++) {
                            id = nom.getId();
                            category = nom.getCategory();
                            article = nom.getArticle();
                            fullName = nom.getFullName();
                            dignity = "Достоинства: ";
                            disadvantages = "Недостатки: ";

                            author = object2.getJSONArray("reviews").getJSONObject(j).getJSONObject("author").getString("name");
                            summary = object2.getJSONArray("reviews").getJSONObject(j).getString("summary");
                            textReview = object2.getJSONArray("reviews").getJSONObject(j).getString("text");
                            dignity += object2.getJSONArray("reviews").getJSONObject(j).getString("pros");
                            disadvantages += object2.getJSONArray("reviews").getJSONObject(j).getString("cons");
                            String fullDate = object2.getJSONArray("reviews").getJSONObject(j).getString("created_at");
                            dateReview = fullDate.substring(0, fullDate.indexOf("T"));
                            rating = object2.getJSONArray("reviews").getJSONObject(j).getInt("rating");
                            linkToReviews = nom.getLinkToReviews();

                            Date d3 = siteFormat.parse(dateReview);
                            if ((d1.equals(d3) || d1.before(d3)) && (d2.equals(d3) || d2.after(d3))) {
                                dateReview = myFormat.format(d3);
                                reviewsArrayList.add(new Reviews(id, category, article, fullName, author, summary, textReview, dignity, disadvantages, dateReview, rating, linkToReviews));
                            }
                        }
                    }
                }
                updateProgress(0.45 + i2, 1);
                i2 += pathOfSecondPart;
            }
        }
        updateProgress(0.9, 1);

        createExcelFile(nomenclatures, reviewsArrayList);
        Thread.sleep(3000);

        brendTab.setDisable(false);
        categoriesTab.setDisable(false);
        reviewTab.setDisable(false);
        startButton.setDisable(false);
        chooseButton.setDisable(false);
        progressBar.setVisible(false);
        textProgress.setVisible(false);
        return null;
    }

    private static String getUrlContent(String urlAddress) {
        StringBuilder x = new StringBuilder();
        try {
            URL url = new URL(urlAddress);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            while (urlConnection.getResponseCode() == 503) {
                Thread.sleep(1000);
                url = new URL(urlAddress);
                urlConnection = (HttpsURLConnection) url.openConnection();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                x.append(line).append("\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return x.toString();
    }

    //создание excel-файла
    public void createExcelFile(ArrayList<Nomenclatures> nomenclatures, ArrayList<Reviews> reviewsArrayList) throws IOException {
        updateMessage("Формирование excel файла");
        int startRow = 1;
        int startColumn = 1;

        //Создаю книгу excel
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheetTotal = wb.createSheet("Сводная таблица");
        XSSFSheet sheetWithReview = null;
        if (isCollectReview) {
            sheetWithReview = wb.createSheet("Отзывы с Onliner.by");
        }

        //стиль ячеек шапки
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setBorderBottom(BorderStyle.MEDIUM);
        headStyle.setBorderLeft(BorderStyle.MEDIUM);
        headStyle.setBorderTop(BorderStyle.MEDIUM);
        headStyle.setBorderRight(BorderStyle.MEDIUM);

        // стиль для основной табличной части
        CellStyle contentStyle = wb.createCellStyle();
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);

        //шрифты шапки
        Font headFont = wb.createFont();
        headFont.setFontName("Times New Roman");
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 12);
        headStyle.setFont(headFont);

        XSSFRow head = sheetTotal.createRow(startRow);

        XSSFCell categoryHead = head.createCell(startColumn);
        categoryHead.setCellValue("Категория бытовой техники");
        headStyle.setWrapText(true);
        sheetTotal.autoSizeColumn(startColumn);
        categoryHead.setCellStyle(headStyle);

        XSSFCell nameHead = head.createCell(++startColumn);
        nameHead.setCellValue("Наименование");
        headStyle.setWrapText(true);
        sheetTotal.autoSizeColumn(startColumn);
        nameHead.setCellStyle(headStyle);

        XSSFCell idHead = head.createCell(++startColumn);
        idHead.setCellValue("Код товара");
        headStyle.setWrapText(true);
        sheetTotal.autoSizeColumn(startColumn);
        idHead.setCellStyle(headStyle);

        XSSFCell averageRatingHead = head.createCell(++startColumn);
        averageRatingHead.setCellValue("Средняя оценка");
        headStyle.setWrapText(true);
        sheetTotal.setColumnWidth(startColumn, 2500);
        averageRatingHead.setCellStyle(headStyle);

        XSSFCell countReviewHead = head.createCell(++startColumn);
        countReviewHead.setCellValue("Количество отзывов");
        headStyle.setWrapText(true);
        sheetTotal.setColumnWidth(startColumn, 3000);
        countReviewHead.setCellStyle(headStyle);

        XSSFCell linkHead = head.createCell(++startColumn);
        linkHead.setCellValue("Ссылка");
        headStyle.setWrapText(true);
        sheetTotal.setColumnWidth(startColumn, 15000);
        linkHead.setCellStyle(headStyle);

        int startRowContent = startRow + 1;

        double pathTable = 0.1 / nomenclatures.size();
        double a = pathTable;
        for (Nomenclatures nom : nomenclatures) {
            int startColumnContent = 1;

            XSSFRow content = sheetTotal.createRow(startRowContent);

            //Категория товара в excel
            XSSFCell cellCategory = content.createCell(startColumnContent);
            contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
            contentStyle.setWrapText(true);
            cellCategory.setCellValue(nom.getCategory());
            cellCategory.setCellStyle(contentStyle);

            //Наименование товара в excel
            XSSFCell cellName = content.createCell(++startColumnContent);
            contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
            contentStyle.setWrapText(true);
            cellName.setCellValue(nom.getFullName());
            cellName.setCellStyle(contentStyle);

            //id товара в excel
            XSSFCell cellid = content.createCell(++startColumnContent);
            contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
            contentStyle.setWrapText(true);
            cellid.setCellValue(nom.getId());
            cellid.setCellStyle(contentStyle);

            //Средняя оценка в excel
            XSSFCell cellAverageRating = content.createCell(++startColumnContent);
            contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
            contentStyle.setWrapText(true);
            cellAverageRating.setCellValue(nom.getAverageRating());
            cellAverageRating.setCellStyle(contentStyle);

            //Количество отзывов в excel
            XSSFCell cellCountReview = content.createCell(++startColumnContent);
            contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
            contentStyle.setWrapText(true);
            cellCountReview.setCellValue(nom.getCountReviews());
            cellCountReview.setCellStyle(contentStyle);

            //Ссылка на товар в excel
            XSSFCell cellLink = content.createCell(++startColumnContent);
            contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
            contentStyle.setWrapText(true);
            cellLink.setCellValue(nom.getLinkToReviews());
            cellLink.setCellStyle(contentStyle);

            startRowContent++;

            updateProgress(0.9 + a, 1);
            updateMessage("Формирование excel файла: запись " + startRowContent + " строки");
            a += pathTable;
        }

        for (int i = 1; i < 3; i++) {
            sheetTotal.autoSizeColumn(i);
        }
        if (isCollectReview) {
            int startRowWithReview = 0;
            int startColumnWithReview = 0;

            XSSFRow headReview = sheetWithReview.createRow(startRowWithReview);

            for (CheckBox check : setCheckBox) {
                if (check.isSelected() && check.getText().equals("ID товара")) {
                    XSSFCell idHeadReview = headReview.createCell(++startColumnWithReview);
                    idHeadReview.setCellValue("Код товара");
                    headStyle.setWrapText(true);
                    sheetWithReview.setColumnWidth(startColumnWithReview, 3000);
                    idHeadReview.setCellStyle(headStyle);
                } else if (check.isSelected() && check.getText().equals("Категория товара")) {
                    XSSFCell categoryHeadReview = headReview.createCell(++startColumnWithReview);
                    categoryHeadReview.setCellValue("Категория бытовой техники");
                    headStyle.setWrapText(true);
                    sheetWithReview.setColumnWidth(startColumnWithReview, 5000);
                    categoryHeadReview.setCellStyle(headStyle);
                } else if (check.isSelected() && check.getText().equals("Артикул")) {
                    XSSFCell nameArticleReview = headReview.createCell(++startColumnWithReview);
                    nameArticleReview.setCellValue("Артикул");
                    headStyle.setWrapText(true);
                    sheetWithReview.setColumnWidth(startColumnWithReview, 5000);
                    nameArticleReview.setCellStyle(headStyle);
                } else if (check.isSelected() && check.getText().equals("Полное наименование")) {
                    XSSFCell nameHeadReview = headReview.createCell(++startColumnWithReview);
                    nameHeadReview.setCellValue("Наименование");
                    headStyle.setWrapText(true);
                    sheetWithReview.setColumnWidth(startColumnWithReview, 5000);
                    nameHeadReview.setCellStyle(headStyle);
                } else if (check.isSelected() && check.getText().equals("Автор отзыва")) {
                    XSSFCell authorHeadReview = headReview.createCell(++startColumnWithReview);
                    authorHeadReview.setCellValue("Автор отзыва");
                    headStyle.setWrapText(true);
                    sheetWithReview.setColumnWidth(startColumnWithReview, 5000);
                    authorHeadReview.setCellStyle(headStyle);
                } else if (check.isSelected() && check.getText().equals("Текст отзыва")) {
                    XSSFCell textHeadReview = headReview.createCell(++startColumnWithReview);
                    textHeadReview.setCellValue("Текст отзыва");
                    headStyle.setWrapText(true);
                    sheetWithReview.setColumnWidth(startColumnWithReview, 15000);
                    textHeadReview.setCellStyle(headStyle);
                } else if (check.isSelected() && check.getText().equals("Оценка")) {
                    XSSFCell ratingHeadReview = headReview.createCell(++startColumnWithReview);
                    ratingHeadReview.setCellValue("Оценка");
                    headStyle.setWrapText(true);
                    sheetWithReview.setColumnWidth(startColumnWithReview, 2500);
                    ratingHeadReview.setCellStyle(headStyle);
                } else if (check.isSelected() && check.getText().equals("Дата отзыва")) {
                    XSSFCell dateHeadReview = headReview.createCell(++startColumnWithReview);
                    dateHeadReview.setCellValue("Дата отзыва");
                    headStyle.setWrapText(true);
                    sheetWithReview.setColumnWidth(startColumnWithReview, 3000);
                    dateHeadReview.setCellStyle(headStyle);
                } else if (check.isSelected() && check.getText().equals("Ссылка на сайте")) {
                    XSSFCell linkHeadReview = headReview.createCell(++startColumnWithReview);
                    linkHeadReview.setCellValue("Текст отзыва");
                    headStyle.setWrapText(true);
                    sheetWithReview.setColumnWidth(startColumnWithReview, 12000);
                    linkHeadReview.setCellStyle(headStyle);
                }
            }
            int startRowContentWithReview = startRowWithReview + 1;
            for (Reviews review : reviewsArrayList) {
                int startColumnContentWithReview = 0;
                XSSFRow content = sheetWithReview.createRow(startRowContentWithReview);

                for (CheckBox check : setCheckBox) {
                    if (check.isSelected() && check.getText().equals("ID товара")) {
                        XSSFCell cell = content.createCell(++startColumnContentWithReview);
                        contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        contentStyle.setWrapText(true);
                        cell.setCellValue(review.getId());
                        cell.setCellStyle(contentStyle);
                    } else if (check.isSelected() && check.getText().equals("Категория товара")) {
                        XSSFCell cell = content.createCell(++startColumnContentWithReview);
                        contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        contentStyle.setWrapText(true);
                        cell.setCellValue(review.getCategory());
                        cell.setCellStyle(contentStyle);
                    } else if (check.isSelected() && check.getText().equals("Артикул")) {
                        XSSFCell cell = content.createCell(++startColumnContentWithReview);
                        contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        contentStyle.setWrapText(true);
                        cell.setCellValue(review.getArticle());
                        cell.setCellStyle(contentStyle);
                    } else if (check.isSelected() && check.getText().equals("Полное наименование")) {
                        XSSFCell cell = content.createCell(++startColumnContentWithReview);
                        contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        contentStyle.setWrapText(true);
                        cell.setCellValue(review.getFullName());
                        cell.setCellStyle(contentStyle);
                    } else if (check.isSelected() && check.getText().equals("Автор отзыва")) {
                        XSSFCell cell = content.createCell(++startColumnContentWithReview);
                        contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        contentStyle.setWrapText(true);
                        cell.setCellValue(review.getAuthor());
                        cell.setCellStyle(contentStyle);
                    } else if (check.isSelected() && check.getText().equals("Текст отзыва")) {
                        XSSFCell cell = content.createCell(++startColumnContentWithReview);
                        contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        contentStyle.setWrapText(true);
                        cell.setCellValue(review.getSummary() + "\n" + review.getTextReview() + "\n" + review.getDignity() + "\n" + review.getDisadvantages());
                        cell.setCellStyle(contentStyle);
                    } else if (check.isSelected() && check.getText().equals("Оценка")) {
                        XSSFCell cell = content.createCell(++startColumnContentWithReview);
                        contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        contentStyle.setWrapText(true);
                        cell.setCellValue(review.getRating());
                        cell.setCellStyle(contentStyle);
                    } else if (check.isSelected() && check.getText().equals("Дата отзыва")) {
                        XSSFCell cell = content.createCell(++startColumnContentWithReview);
                        contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        contentStyle.setWrapText(true);
                        cell.setCellValue(review.getDateReview());
                        cell.setCellStyle(contentStyle);
                    } else if (check.isSelected() && check.getText().equals("Ссылка на сайте")) {
                        XSSFCell cell = content.createCell(++startColumnContentWithReview);
                        contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
                        contentStyle.setWrapText(true);
                        cell.setCellValue(review.getLinkToReviews());
                        cell.setCellStyle(contentStyle);
                    }
                }
                startRowContentWithReview++;
            }
        }
        FileOutputStream fos = new FileOutputStream(pathToSave);
        wb.write(fos);
        fos.close();

        updateProgress(1, 1);
        updateMessage("Файл " + pathToSave + " готов!!!");
    }
}
