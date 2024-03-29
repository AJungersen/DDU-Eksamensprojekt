package com.mycompany.ddueksamensprojekt;

import Classes.CreditCard;
import Classes.ProductCategory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import repository.StoreDatabaseMethods;
import repository.Tools;
import repository.UserDatabaseMethods;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chris
 */
public class AdminProductViewController implements Initializable {

    private StoreDatabaseMethods sdm = new StoreDatabaseMethods();
    private float boarderThikness = 2.5f;
    private float textSpace = 5;

    private float paneSpace_X = 10;
    private float paneSpace_Y = 10;
    private float paneSize_X = 137;
    private float panesPerRow;

    private float imgSize_X = paneSize_X - 2 * boarderThikness;
    private float imgSize_Y = 143 - boarderThikness;

    private float nameTextSize = 14;
    private float namepos_X = 0;
    private float namePos_Y = 158;

    private float priceTextSize = 12;
    private float pricePos_X = 0;
    private float pricePos_Y = namePos_Y + nameTextSize;

    private float lagerstatusPos_X = 0;
    private float lagerstatusPos_Y = pricePos_Y + priceTextSize;
    private float lagerstatusSize = 12;
    private float lagerstatusCirkel_X = 0;
    private float lagerstatusCirkel_Y = lagerstatusPos_Y;
    private float lagerStatusCirkelSize = 7;

    private float paneSize_Y = lagerstatusPos_Y + lagerstatusSize;
    private Font nameFont = Font.font("italic", nameTextSize);
    private Font priceFont = Font.font("italic", priceTextSize);

    private int fewProductsRemaning = 10;
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Product> curentProducts = new ArrayList<>();
    private ArrayList<ProductSortingMethods> sortingMethods = new ArrayList<>();

    private String comboBoxCategoryPrompText;
    private String comboBoxSortPrompText;

    @FXML
    private AnchorPane anchorPaneProducts;
    @FXML
    private TextField textFieldSearchBar;
    @FXML
    private ComboBox<ProductCategory> comboBoxCategory;
    @FXML
    private ComboBox<ProductSortingMethods> comboBoxSort;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            sortingMethods.add(new ProductSortingMethods("A-Å", new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            }));

            sortingMethods.add(new ProductSortingMethods("Å-A", new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            }));
            
            sortingMethods.add(new ProductSortingMethods("Lager status stigende", new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o2.getStock() - o1.getStock();
                }
            }));

            sortingMethods.add(new ProductSortingMethods("Lager status faldende", new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getStock() - o2.getStock();
                }
            }));

            sortingMethods.add(new ProductSortingMethods("Produktkategori, A-Å", new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getProductCategory().toString().compareTo(o2.getProductCategory().toString());
                }
            }));

            sortingMethods.add(new ProductSortingMethods("Produktkategori, Å-A", new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o2.getProductCategory().toString().compareTo(o1.getProductCategory().toString());
                }
            }));

            comboBoxSort.setCellFactory(new Callback<ListView<ProductSortingMethods>, ListCell<ProductSortingMethods>>() {
                @Override
                public ListCell<ProductSortingMethods> call(ListView<ProductSortingMethods> p) {
                    return new ListCell<>() {
                        @Override
                        public void updateItem(ProductSortingMethods item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.getName());
                            }
                        }
                    };
                }
            });

            comboBoxSort.setButtonCell(
                    new ListCell<ProductSortingMethods>() {
                @Override
                protected void updateItem(ProductSortingMethods item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(comboBoxSortPrompText);
                    } else {
                        setText(item.getName());
                    }
                }
            });

            comboBoxSort.getItems().addAll(sortingMethods);
            comboBoxSortPrompText = comboBoxSort.getPromptText();

            comboBoxCategory.setCellFactory(new Callback<ListView<ProductCategory>, ListCell<ProductCategory>>() {
                @Override
                public ListCell<ProductCategory> call(ListView<ProductCategory> p) {
                    return new ListCell<>() {
                        @Override
                        public void updateItem(ProductCategory item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.asFormatedString());
                            }
                        }
                    };
                }
            });

            comboBoxCategory.setButtonCell(
                    new ListCell<ProductCategory>() {
                @Override
                protected void updateItem(ProductCategory item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(comboBoxCategoryPrompText);
                    } else {
                        setText(item.asFormatedString());
                    }
                }
            });

            comboBoxCategory.getItems().addAll(ProductCategory.values());
            comboBoxCategoryPrompText = comboBoxCategory.getPromptText();

            //load allProducts
            allProducts = sdm.getAllProducts();
            curentProducts.addAll(allProducts);

            //calc panes per row
            panesPerRow = (int) Math.floor((anchorPaneProducts.getPrefWidth() - paneSpace_X) / (paneSize_X + paneSpace_X));

            //update pane space x
            paneSpace_X = (float) (anchorPaneProducts.getPrefWidth()
                    - (paneSize_X * panesPerRow))
                    / (2 + panesPerRow - 1);

            //update anchor pane height
            float newHeight = (float) ((paneSize_Y + paneSpace_Y)
                    * Math.ceil(ProductCategory.values().length / panesPerRow) + paneSpace_Y);

            anchorPaneProducts.setPrefHeight(newHeight);
            loadProducts();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void loadProducts() {
        anchorPaneProducts.getChildren().clear();
        //insert allProducts
        int column = 0;
        int row = 1;

        for (Product p : curentProducts) {
            column++;
            Pane pane = new Pane();

            //Pane pane = new StackPane();
            //pane pos
            pane.setLayoutX((paneSpace_X * column) + (paneSize_X * (column - 1)));
            pane.setLayoutY((paneSpace_Y * row) + (paneSize_Y * (row - 1)));

            //pane size
            pane.setMinSize(paneSize_X, paneSize_Y);
            pane.setMaxSize(paneSize_X, paneSize_Y);

            pane.setStyle("-fx-border-color: #666666;"
                    + "-fx-border-width: " + boarderThikness + ";"
                    + "-fx-background-color: #ffffff");

            //product image
            ImageView imgView = new ImageView(p.getImage());

            imgView.setFitWidth(imgSize_X);
            imgView.setFitHeight(imgSize_Y);

            imgView.setLayoutX(boarderThikness);
            imgView.setLayoutY(boarderThikness);

            //name text
            p.setName(Tools.capitalizeFirstLetter(p.getName()));
            Text nameText = new Text(namepos_X, namePos_Y, p.getName());
            nameText.setFont(nameFont);
            nameText.setLayoutX(paneSize_X / 2 - nameText.getBoundsInLocal().getWidth() / 2);
            //nameText.setTextOrigin(VPos.CENTER);

            //price text
            Text priceText = new Text(pricePos_X, pricePos_Y, Float.toString(p.getPrice()) + " DKK");
            priceText.setFont(priceFont);
            priceText.setLayoutX(paneSize_X / 2 - priceText.getBoundsInLocal().getWidth() / 2);
            //priceText.setTextOrigin(VPos.CENTER);

            //Lagerstatus text and circel
            Text lagerstatusText = new Text(lagerstatusPos_X, lagerstatusPos_Y, "lagerstatus");
            lagerstatusText.setFont(priceFont);
            lagerstatusText.setLayoutX(paneSize_X / 2 - lagerstatusText.getBoundsInLocal().getWidth() / 2 - lagerStatusCirkelSize - 10);
            lagerstatusText.setTextOrigin(VPos.CENTER);

            Circle lagerstatusCircle = new Circle(lagerstatusCirkel_X, lagerstatusCirkel_Y, lagerStatusCirkelSize);
            if (p.getStock() != 0) {
                if (p.getStock() <= fewProductsRemaning) {
                    //few
                    lagerstatusCircle.setFill(Paint.valueOf("#F7DC6F"));
                } else {
                    //in stock
                    lagerstatusCircle.setFill(Paint.valueOf("#82E0AA"));
                }
            } else {
                //empty
                lagerstatusCircle.setFill(Paint.valueOf("#F1948A"));
            }
            lagerstatusCircle.setCenterX(lagerstatusText.getLayoutX() + lagerstatusText.getBoundsInLocal().getWidth() + lagerStatusCirkelSize + 10);

            //set mouse clicked on, to switch to product view
            EventHandler<MouseEvent> clicked = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    App.setCurrentProduct(p);
                    try {
                        App.setLastSceneFxml("adminProductView");
                        App.setRoot("productInformationAdmin");
                    } catch (Exception e) {
                        System.out.println("Error in " + e.getMessage());;
                    }
                }
            };

            EventHandler<MouseEvent> entered = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    App.getStage().getScene().setCursor(Cursor.HAND);
                }
            };

            EventHandler<MouseEvent> exited = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    App.getStage().getScene().setCursor(Cursor.DEFAULT);
                }
            };

            pane.getChildren().add(imgView);
            pane.getChildren().add(nameText);
            pane.getChildren().add(priceText);
            pane.getChildren().add(lagerstatusText);
            pane.getChildren().add(lagerstatusCircle);

            /*pane.setOnMouseClicked(clicked);
                pane.setOnMouseEntered(entered);
                pane.setOnMouseExited(exited);*/
            for (Node sp : pane.getChildren()) {
                sp.setOnMouseClicked(clicked);
                sp.setOnMouseEntered(entered);
                sp.setOnMouseExited(exited);
            }

            anchorPaneProducts.getChildren().add(pane);
            if (column % panesPerRow == 0) {
                column = 0;
                row++;
            }
        }
    }

    @FXML
    void openProfile() throws Exception {
        App.setRoot("profileAdmin");
    }

    @FXML
    void openShop() throws Exception {
        App.setRoot("main");
    }

    @FXML
    void openMain() throws Exception {
        App.setRoot("mainAdmin");
    }

    private void search() {
        if (!textFieldSearchBar.getText().isBlank()) {
            Predicate<Product> search = (p) -> !Pattern.matches(".*" + textFieldSearchBar.getText().toLowerCase() + "+.*", p.getName().toLowerCase());

            curentProducts.removeIf(search);
        }
    }

    private void showCategory() {
        if (!comboBoxCategory.getSelectionModel().isEmpty()) {
            Predicate<Product> search = (p) -> !p.getProductCategory().equals(comboBoxCategory.getSelectionModel().getSelectedItem());

            curentProducts.removeIf(search);
        }
    }

    private void sortProducts() {
        System.out.println("test test");
        if (!comboBoxSort.getSelectionModel().isEmpty()) {
            curentProducts.sort(comboBoxSort.getSelectionModel().getSelectedItem().getComparator());
        }
    }

    @FXML
    private void clearSearch(ActionEvent event) {
        textFieldSearchBar.clear();
        handleSorting();
    }

    @FXML
    private void clearSort(ActionEvent event) {
        comboBoxSort.getSelectionModel().clearSelection();
        comboBoxSort.setPromptText(comboBoxSortPrompText);
        handleSorting();
    }
    
    @FXML
    private void clearCategory(ActionEvent event) {
        comboBoxCategory.getSelectionModel().clearSelection();
        handleSorting();
    }

    private void handleSorting() {
        curentProducts.clear();
        curentProducts.addAll(allProducts);
        
        search();
        
        showCategory();
        
        sortProducts();
        
        loadProducts();
    }

    @FXML
    private void handleShowCategory(ActionEvent event) {
        handleSorting();
    }

    @FXML
    private void handleSortProducts(ActionEvent event) {
        handleSorting();
    }

    @FXML
    private void handleSearch(KeyEvent event) {
        handleSorting();
    }
}
