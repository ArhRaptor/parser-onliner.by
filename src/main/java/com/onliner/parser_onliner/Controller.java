package com.onliner.parser_onliner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Controller {

    @FXML
    private CheckBox bAresa;

    @FXML
    private CheckBox bHitt;

    @FXML
    private Tab brendTab;

    @FXML
    private Tab categoriesTab;

    @FXML
    private Button chooseBtn;

    @FXML
    private DatePicker dateEnd;

    @FXML
    private DatePicker dateStart;

    @FXML
    private Tab finalTab;

    @FXML
    private Label h1;

    @FXML
    private Label h2;

    @FXML
    private Label h3;

    @FXML
    private CheckBox isAddTextReview;

    @FXML
    private CheckBox isArticle;

    @FXML
    private CheckBox isAuthor;

    @FXML
    private CheckBox isBathScales;

    @FXML
    private CheckBox isBlend;

    @FXML
    private CheckBox isCategory;

    @FXML
    private CheckBox isCoffeeGrinders;

    @FXML
    private CheckBox isCofferMakers;

    @FXML
    private CheckBox isDateReview;

    @FXML
    private CheckBox isElectShaver;

    @FXML
    private CheckBox isFan;

    @FXML
    private CheckBox isFanHeaters;

    @FXML
    private CheckBox isFoodDryers;

    @FXML
    private CheckBox isFoodProc;

    @FXML
    private CheckBox isFullName;

    @FXML
    private CheckBox isGarmentStreams;

    @FXML
    private CheckBox isGrill;

    @FXML
    private CheckBox isHairClippers;

    @FXML
    private CheckBox isHairDryers;

    @FXML
    private CheckBox isHumidifiers;

    @FXML
    private CheckBox isId;

    @FXML
    private CheckBox isIrons;

    @FXML
    private CheckBox isJuicers;

    @FXML
    private CheckBox isKettles;

    @FXML
    private CheckBox isKitchenScales;

    @FXML
    private CheckBox isLink;

    @FXML
    private CheckBox isLintRem;

    @FXML
    private CheckBox isMeatgreanders;

    @FXML
    private CheckBox isMix;

    @FXML
    private CheckBox isMulticoockers;

    @FXML
    private CheckBox isOnSale;

    @FXML
    private CheckBox isRadioClock;

    @FXML
    private CheckBox isRating;

    @FXML
    private CheckBox isSandwichMakers;

    @FXML
    private CheckBox isStailers;

    @FXML
    private CheckBox isTextReview;

    @FXML
    private CheckBox isToasters;

    @FXML
    private CheckBox isWM;

    @FXML
    private CheckBox isWaf;

    @FXML
    private CheckBox isWeatherStation;

    @FXML
    private TextField pathToSave;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Tab reviewsTab;

    @FXML
    private Button selAllCat;

    @FXML
    private Button selAllReview;

    @FXML
    private Button startBtn;

    @FXML
    private Label textProgress;

    @FXML
    private Button unselAllCat;

    @FXML
    private Button unselAllReview;

    @FXML
    void initialize() {
        bAresa.setOnAction(event -> {
            if (bHitt.isSelected() && !bAresa.isSelected()) {
                categoriesTab.setDisable(false);
                isCofferMakers.setDisable(true);
                isGrill.setDisable(true);
                isWaf.setDisable(true);
                isFoodProc.setDisable(true);
                isHairClippers.setDisable(true);
                isLintRem.setDisable(true);
                isWeatherStation.setDisable(true);
                isMulticoockers.setDisable(true);
                isRadioClock.setDisable(true);
                isFanHeaters.setDisable(true);
                isGarmentStreams.setDisable(true);
                isJuicers.setDisable(true);
                isWM.setDisable(true);
                isHumidifiers.setDisable(true);
                isElectShaver.setDisable(true);
                isGrill.setDisable(true);
            }
            if ((bAresa.isSelected() && bHitt.isSelected()) || bAresa.isSelected()) {
                categoriesTab.setDisable(false);
                isCofferMakers.setDisable(false);
                isGrill.setDisable(false);
                isWaf.setDisable(false);
                isFoodProc.setDisable(false);
                isHairClippers.setDisable(false);
                isLintRem.setDisable(false);
                isWeatherStation.setDisable(false);
                isMulticoockers.setDisable(false);
                isRadioClock.setDisable(false);
                isFanHeaters.setDisable(false);
                isGarmentStreams.setDisable(false);
                isJuicers.setDisable(false);
                isWM.setDisable(false);
                isHumidifiers.setDisable(false);
                isElectShaver.setDisable(false);
                isGrill.setDisable(false);
            }
            if (!bAresa.isSelected() && !bHitt.isSelected()) {
                categoriesTab.setDisable(true);
                reviewsTab.setDisable(true);
                finalTab.setDisable(true);
            }
            selectAllCategoriesAresa(false);
        });
        bHitt.setOnAction(event -> {
            if (bHitt.isSelected() && !bAresa.isSelected()) {
                categoriesTab.setDisable(false);
                isCofferMakers.setDisable(true);
                isGrill.setDisable(true);
                isWaf.setDisable(true);
                isFoodProc.setDisable(true);
                isHairClippers.setDisable(true);
                isLintRem.setDisable(true);
                isWeatherStation.setDisable(true);
                isMulticoockers.setDisable(true);
                isRadioClock.setDisable(true);
                isFanHeaters.setDisable(true);
                isGarmentStreams.setDisable(true);
                isJuicers.setDisable(true);
                isWM.setDisable(true);
                isHumidifiers.setDisable(true);
                isElectShaver.setDisable(true);
                isGrill.setDisable(true);

            }
            if (bHitt.isSelected() && bAresa.isSelected()) {
                categoriesTab.setDisable(false);
            }
            if (!bAresa.isSelected() && !bHitt.isSelected()) {
                categoriesTab.setDisable(true);
                reviewsTab.setDisable(true);
                finalTab.setDisable(true);
            }
            selectAllCategoriesAresa(false);
        });

        isBlend.setOnAction(event -> setCategories(isBlend));
        isWaf.setOnAction(event -> setCategories(isWaf));
        isFan.setOnAction(event -> setCategories(isFan));
        isKitchenScales.setOnAction(event -> setCategories(isKitchenScales));
        isBathScales.setOnAction(event -> setCategories(isBathScales));
        isCofferMakers.setOnAction(event -> setCategories(isCofferMakers));
        isCoffeeGrinders.setOnAction(event -> setCategories(isCoffeeGrinders));
        isFoodProc.setOnAction(event -> setCategories(isFoodProc));
        isHairClippers.setOnAction(event -> setCategories(isHairClippers));
        isLintRem.setOnAction(event -> setCategories(isLintRem));
        isWeatherStation.setOnAction(event -> setCategories(isWeatherStation));
        isMulticoockers.setOnAction(event -> setCategories(isMulticoockers));
        isMeatgreanders.setOnAction(event -> setCategories(isMeatgreanders));
        isRadioClock.setOnAction(event -> setCategories(isRadioClock));
        isFanHeaters.setOnAction(event -> setCategories(isFanHeaters));
        isGarmentStreams.setOnAction(event -> setCategories(isGarmentStreams));
        isMix.setOnAction(event -> setCategories(isMix));
        isJuicers.setOnAction(event -> setCategories(isJuicers));
        isStailers.setOnAction(event -> setCategories(isStailers));
        isWM.setOnAction(event -> setCategories(isWM));
        isFoodDryers.setOnAction(event -> setCategories(isFoodDryers));
        isSandwichMakers.setOnAction(event -> setCategories(isSandwichMakers));
        isToasters.setOnAction(event -> setCategories(isToasters));
        isHumidifiers.setOnAction(event -> setCategories(isHumidifiers));
        isIrons.setOnAction(event -> setCategories(isIrons));
        isHairDryers.setOnAction(event -> setCategories(isHairDryers));
        isElectShaver.setOnAction(event -> setCategories(isElectShaver));
        isGrill.setOnAction(event -> setCategories(isGrill));
        isKettles.setOnAction(event -> setCategories(isKettles));

        isTextReview.setOnAction(event -> {
            if (isTextReview.isSelected()) {
                isId.setVisible(true);
                isCategory.setVisible(true);
                isArticle.setVisible(true);
                isFullName.setVisible(true);
                isAuthor.setVisible(true);
                isAddTextReview.setVisible(true);
                isRating.setVisible(true);
                isDateReview.setVisible(true);
                isLink.setVisible(true);
                selAllReview.setVisible(true);
                unselAllReview.setVisible(true);
                h1.setVisible(true);
                h2.setVisible(true);
                h3.setVisible(true);
                dateStart.setVisible(true);
                dateEnd.setVisible(true);
            } else {
                isId.setVisible(false);
                isCategory.setVisible(false);
                isArticle.setVisible(false);
                isFullName.setVisible(false);
                isAuthor.setVisible(false);
                isAddTextReview.setVisible(false);
                isRating.setVisible(false);
                isDateReview.setVisible(false);
                isLink.setVisible(false);
                selAllReview.setVisible(false);
                unselAllReview.setVisible(false);
                selectAllItemsReview(false);
                h1.setVisible(false);
                h2.setVisible(false);
                h3.setVisible(false);
                dateStart.setVisible(false);
                dateEnd.setVisible(false);
            }
        });
        //Кнопка выбрать все в закладке с категориями
        selAllCat.setOnAction(event -> {
            if (!bAresa.isSelected() && bHitt.isSelected()) {
                selectAllCategoriesHitt(true);
            } else {
                selectAllCategoriesAresa(true);
            }
            reviewsTab.setDisable(false);
            finalTab.setDisable(false);
        });
        //Кнопка снять выделение в закладке с категориями
        unselAllCat.setOnAction(event -> {
            if (!bAresa.isSelected() && bHitt.isSelected()) {
                selectAllCategoriesHitt(false);
            } else {
                selectAllCategoriesAresa(false);
            }
            reviewsTab.setDisable(true);
            finalTab.setDisable(true);
        });
        //Кнопка выбрать все в закладке с отзывами
        selAllReview.setOnAction(event -> selectAllItemsReview(true));
        //Кнопка снять выделение в закладке с отзывами
        unselAllReview.setOnAction(event -> selectAllItemsReview(false));
    }

    public boolean isNotCheck() {
        return !isBlend.isSelected() && !isWaf.isSelected() && !isFan.isSelected() && !isKitchenScales.isSelected() && !isBathScales.isSelected() && !isCofferMakers.isSelected() && !isCoffeeGrinders.isSelected()
                && !isFoodProc.isSelected() && !isHairClippers.isSelected() && !isLintRem.isSelected() && !isWeatherStation.isSelected() && !isMulticoockers.isSelected() && !isMeatgreanders.isSelected() && !isRadioClock.isSelected() &&
                !isFanHeaters.isSelected() && !isGarmentStreams.isSelected() && !isMix.isSelected() && !isJuicers.isSelected() && !isStailers.isSelected() && !isWM.isSelected() && !isFoodDryers.isSelected() && !isSandwichMakers.isSelected()
                && !isToasters.isSelected() && !isHumidifiers.isSelected() && !isIrons.isSelected() && !isHairDryers.isSelected() && !isElectShaver.isSelected() && !isGrill.isSelected() && !isKettles.isSelected();
    }

    //Метод активации закладок с отзывами и финальной
    public void setCategories(CheckBox checkBox) {
        if (checkBox.isSelected()) {
            reviewsTab.setDisable(false);
            finalTab.setDisable(false);
        }
        if (isNotCheck()) {
            reviewsTab.setDisable(true);
            finalTab.setDisable(true);
        }
    }

    //Выбор всех категорий ARESA
    public void selectAllCategoriesAresa(boolean select) {
        isBlend.setSelected(select);
        isWaf.setSelected(select);
        isFan.setSelected(select);
        isKitchenScales.setSelected(select);
        isBathScales.setSelected(select);
        isCofferMakers.setSelected(select);
        isCoffeeGrinders.setSelected(select);
        isFoodProc.setSelected(select);
        isHairClippers.setSelected(select);
        isLintRem.setSelected(select);
        isWeatherStation.setSelected(select);
        isMulticoockers.setSelected(select);
        isMeatgreanders.setSelected(select);
        isRadioClock.setSelected(select);
        isFanHeaters.setSelected(select);
        isGarmentStreams.setSelected(select);
        isMix.setSelected(select);
        isJuicers.setSelected(select);
        isStailers.setSelected(select);
        isWM.setSelected(select);
        isFoodDryers.setSelected(select);
        isSandwichMakers.setSelected(select);
        isToasters.setSelected(select);
        isHumidifiers.setSelected(select);
        isIrons.setSelected(select);
        isHairDryers.setSelected(select);
        isElectShaver.setSelected(select);
        isGrill.setSelected(select);
        isKettles.setSelected(select);
    }

    //Выбор всех категорий HiTT
    public void selectAllCategoriesHitt(boolean select) {
        isBlend.setSelected(select);
        isFan.setSelected(select);
        isKitchenScales.setSelected(select);
        isBathScales.setSelected(select);
        isCoffeeGrinders.setSelected(select);
        isMeatgreanders.setSelected(select);
        isMix.setSelected(select);
        isStailers.setSelected(select);
        isFoodDryers.setSelected(select);
        isSandwichMakers.setSelected(select);
        isToasters.setSelected(select);
        isIrons.setSelected(select);
        isHairDryers.setSelected(select);
        isKettles.setSelected(select);
    }

    //Выбор всех checkbox в закладке отзывы
    public void selectAllItemsReview(boolean select) {
        isId.setSelected(select);
        isCategory.setSelected(select);
        isArticle.setSelected(select);
        isFullName.setSelected(select);
        isAuthor.setSelected(select);
        isAddTextReview.setSelected(select);
        isRating.setSelected(select);
        isDateReview.setSelected(select);
        isLink.setSelected(select);
    }

    //Нажатие кнопки "Старт"
    @FXML
    void startApplication(ActionEvent event) throws ParseException {

        String dts, dte;

        SimpleDateFormat ftPicker = new SimpleDateFormat("yyyy-MM-dd");
        Date dateToday = new Date();

        if (dateStart.getValue() == null) dts = "2000-01-01";
        else dts = String.valueOf(dateStart.getValue());
        if (dateEnd.getValue() == null) dte = ftPicker.format(dateToday);
        else dte = String.valueOf(dateEnd.getValue());

        Date d1 = ftPicker.parse(dts);
        Date d2 = ftPicker.parse(dte);


        if (d1.after(d2) && d2.before(d1)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Дата начала сбора отзывов позже даты его завершения. \nПроверьте корректность ввода дат!");
            alert.setTitle("Ошибка ввода дат!!!");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/logo.png"))));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) event.consume();
        }
        if (pathToSave.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Не выбран путь к файлу и наименование файла.");
            alert.setTitle("Внимание!!!");
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/logo.png"))));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) event.consume();
        } else {
            ArrayList<String> linksList = new ArrayList<>();
            String startLink = "https://catalog.onliner.by/sdapi/catalog.api/search/";
            String brand = null, categories;

            if (isBlend.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "blender?";
                linksList.add(startLink + categories + brand);
            }
            if (isWaf.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "waffle?";
                linksList.add(startLink + categories + brand);
            }
            if (isFan.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "blower?";
                linksList.add(startLink + categories + brand);
            }
            if (isKitchenScales.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "kitchenscales?";
                linksList.add(startLink + categories + brand);
            }
            if (isBathScales.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "scales?";
                linksList.add(startLink + categories + brand);
            }
            if (isCofferMakers.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "coffee?";
                linksList.add(startLink + categories + brand);
            }
            if (isCoffeeGrinders.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "coffeegrinders?";
                linksList.add(startLink + categories + brand);
            }
            if (isFoodProc.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "foodprocessors?";
                linksList.add(startLink + categories + brand);
            }
            if (isHairClippers.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "hairclipper?";
                linksList.add(startLink + categories + brand);
            }
            if (isLintRem.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "lintshaver?";
                linksList.add(startLink + categories + brand);
            }
            if (isWeatherStation.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "meteostations?";
                linksList.add(startLink + categories + brand);
            }
            if (isMulticoockers.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "multicooker?";
                linksList.add(startLink + categories + brand);
            }
            if (isMeatgreanders.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "meatgrinder?";
                linksList.add(startLink + categories + brand);
            }
            if (isRadioClock.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "clockradio?";
                linksList.add(startLink + categories + brand);
            }
            if (isFanHeaters.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "heater?";
                linksList.add(startLink + categories + brand);
            }
            if (isGarmentStreams.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "steamcleaner?";
                linksList.add(startLink + categories + brand);
            }
            if (isMix.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "mixer?";
                linksList.add(startLink + categories + brand);
            }
            if (isJuicers.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "juicer?";
                linksList.add(startLink + categories + brand);
            }
            if (isStailers.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "styler?";
                linksList.add(startLink + categories + brand);
            }
            if (isWM.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "washingmachine?";
                linksList.add(startLink + categories + brand);
            }
            if (isFoodDryers.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "food_dehydrator?";
                linksList.add(startLink + categories + brand);
            }
            if (isSandwichMakers.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "sandwichers?";
                linksList.add(startLink + categories + brand);
            }
            if (isToasters.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "toaster?";
                linksList.add(startLink + categories + brand);
            }
            if (isHumidifiers.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "humidifier?";
                linksList.add(startLink + categories + brand);
            }
            if (isIrons.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "iron?";
                linksList.add(startLink + categories + brand);
            }
            if (isHairDryers.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "hairdryer?";
                linksList.add(startLink + categories + brand);
            }
            if (isElectShaver.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "shaver?";
                linksList.add(startLink + categories + brand);
            }
            if (isGrill.isSelected()) {
                brand = "mfr[0]=aresa";
                categories = "electricgrill?";
                linksList.add(startLink + categories + brand);
            }
            if (isKettles.isSelected()) {

                if (bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa&mfr[1]=hitt";
                if (!bHitt.isSelected() && bAresa.isSelected()) brand = "mfr[0]=aresa";
                if (bHitt.isSelected() && !bAresa.isSelected()) brand = "mfr[0]=hitt";

                categories = "kettle?";
                linksList.add(startLink + categories + brand);
            }

            brendTab.setDisable(true);
            categoriesTab.setDisable(true);
            reviewsTab.setDisable(true);
            startBtn.setDisable(true);
            chooseBtn.setDisable(true);

            progressBar.setVisible(true);
            textProgress.setVisible(true);
            TaskToParse tsk;

            if (isTextReview.isSelected()) {
                ArrayList<CheckBox> setCheckBox = new ArrayList<>();
                setCheckBox.add(isId);
                setCheckBox.add(isCategory);
                setCheckBox.add(isArticle);
                setCheckBox.add(isFullName);
                setCheckBox.add(isAuthor);
                setCheckBox.add(isAddTextReview);
                setCheckBox.add(isRating);
                setCheckBox.add(isDateReview);
                setCheckBox.add(isLink);
                tsk = new TaskToParse(true, linksList, isOnSale.isSelected(), dateStart, dateEnd, setCheckBox, brendTab, categoriesTab, reviewsTab, startBtn, chooseBtn, pathToSave.getText().trim(), progressBar, textProgress);
            } else {
                tsk = new TaskToParse(false, linksList, isOnSale.isSelected(), brendTab, categoriesTab, reviewsTab, startBtn, chooseBtn, pathToSave.getText().trim(), progressBar, textProgress);
            }

            Thread thread = new Thread(tsk);
            thread.setDaemon(true);
            thread.start();

            progressBar.progressProperty().bind(tsk.progressProperty());
            textProgress.textProperty().bind(tsk.messageProperty());
        }
    }

    // метод выбора пути сохранения файла
    public void bntChoose(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Книга Excel", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            pathToSave.setText(file.getAbsoluteFile().toString());
        } else {
            if (pathToSave.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Не выбран путь к файлу и наименование файла.");
                alert.setTitle("Внимание!!!");
                alert.setHeaderText(null);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/logo.png"))));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) event.consume();
            }
        }
    }
}
