package MetroNav.App;

import MetroNav.Database.Metro;
import MetroNav.Algorithms.Dijkstra;
import MetroNav.Algorithms.FloydWarshall;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

import java.io.File;
import java.net.MalformedURLException;

public class MetroNav2 extends Application {
    // поле для збереження primaryStage
    private Stage primaryStage;

    //граф на основі карти метро
    private final Metro kyivMetro = new Metro();
    private final Metro londonMetro = new Metro();

    //силки на ресурси(рисунки карт метро)
    String urlImageKyiv = "C:/Users/Maks/IdeaProjects/MetroNav/src/MetroNav/Resource/karta-metro-kiev-1.jpg";
    String urlImageLondon = "C:/Users/Maks/IdeaProjects/MetroNav/src/MetroNav/Resource/london-metro.png";

    //початкова станція
    private String startStation;
    //кінцева станція
    private String endStation;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // зберігаємо primaryStage
        this.primaryStage = primaryStage;
        //заголовок вікна
        primaryStage.setTitle("Metro Navigator");

        // встановити мінімальний та максимальний розмір вікна
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);


        // додаємо об'єкт сцени до Stage
        primaryStage.setScene(firstScene());
        primaryStage.setResizable(false);

        // Показуємо "підмостки"
        primaryStage.show();

    }

    // перша сцена
    private Scene firstScene() {
        //текс начального екрану для запиту який граф використовувати
        Text text = new Text("Оберіть карту метро якого міста ви хочете використовувати:");

        //створення кнопок
        Button buttonKyiv = new Button("Київське метро");
        Button buttonLondon = new Button("Лондонське метро");

        //реалізація виконання при натискані кнопки
        buttonKyiv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //создамо карту метро
                initializeMetroKyiv(kyivMetro);
                //розміри сцени та рисунка
                double imageWidth = 750;
                double imageHeight = 700;
                double sceneWidth = 800;
                double sceneHeight = 1000;

                //додавання рисунку(карти метро) з ресурсів додатку
                Image image = null;
                try {
                    image = getImageFromPath(urlImageKyiv);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageWidth);
                imageView.setFitHeight(imageHeight);

                //текст для краси
                Text primaryText = new Text("Звідки та куди ви хочете поїхати?");
                primaryText.setFont(Font.font(20));

                //настроюємо горизонтальний ряд вводу станцій
                final HBox hboxinputStartStation = setInputStartStation(kyivMetro);
                final HBox hboxinputEndStation = setInputEndStation(kyivMetro);
                final HBox hBoxButton = setButtons(kyivMetro, urlImageKyiv, imageWidth, imageHeight, sceneWidth, sceneHeight);

                //налаштовуємо вертикальний ряд елементів
                final VBox vbox = new VBox();
                // відстань між елементам
                vbox.setSpacing(10);
                // встановлюємо padding у 5 пікселів у всіх напрямках
                vbox.setPadding(new Insets(10));
                vbox.setAlignment(Pos.CENTER);

                //додаємо всі елементи зверху вниз(рисунок, текст, ввод даних, кнопки)
                vbox.getChildren().addAll(imageView, primaryText, hboxinputStartStation, hboxinputEndStation, hBoxButton);

                //створюємо сцену та додаємо вертикальний ряд
                Scene newScene = new Scene(vbox);
                // встановити мінімальний та максимальний розмір вікна
                primaryStage.setHeight(sceneHeight);
                primaryStage.setWidth(sceneWidth);
                //встановлення нової сцени
                primaryStage.setScene(newScene);

            }
        });

        buttonLondon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //создамо карту метро
                initializeMetroLondon(londonMetro);
                //розміри сцени та рисунка
                double imageWidth = 1100;
                double imageHeight = 800;
                double sceneWidth = 1200;
                double sceneHeight = 1050;

                //додавання рисунку(карти метро) з ресурсів додатку
                Image image = null;
                try {
                    image = getImageFromPath(urlImageLondon);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageWidth);
                imageView.setFitHeight(imageHeight);

                //текст для краси
                Text primaryText = new Text("Звідки та куди ви хочете поїхати?");
                primaryText.setFont(Font.font(20));

                //настроюємо горизонтальний ряд вводу станцій
                final HBox hboxinputStartStation = setInputStartStation(londonMetro);
                final HBox hboxinputEndStation = setInputEndStation(londonMetro);
                final HBox hBoxButton = setButtons(londonMetro, urlImageLondon, imageWidth, imageHeight, sceneWidth, sceneHeight);

                //налаштовуємо вертикальний ряд елементів
                final VBox vbox = new VBox();
                // відстань між елементам
                vbox.setSpacing(10);
                // встановлюємо padding у 5 пікселів у всіх напрямках
                vbox.setPadding(new Insets(10));
                vbox.setAlignment(Pos.CENTER);

                //додаємо всі елементи зверху вниз(рисунок, текст, ввод даних, кнопки)
                vbox.getChildren().addAll(imageView, primaryText, hboxinputStartStation, hboxinputEndStation, hBoxButton);

                //створюємо сцену та додаємо вертикальний ряд
                Scene newScene = new Scene(vbox);
                // встановити мінімальний та максимальний розмір вікна
                primaryStage.setHeight(sceneHeight);
                primaryStage.setWidth(sceneWidth);
                //встановлення нової сцени
                primaryStage.setScene(newScene);

            }
        });

        //налаштовуємо вертикальний ряд елементів
        VBox vbox = new VBox();
        // відстань між елементам
        vbox.setSpacing(10);
        // встановлюємо padding у 5 пікселів у всіх напрямках
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        //додаємо всі елементи зверху вниз
        vbox.getChildren().addAll(text, buttonKyiv, buttonLondon);

        //створюємо сцену та додаємо вертикальний ряд
        return new Scene(vbox);
    }

    //ініціалізація карти метро Києва
    public void initializeMetroKyiv(Metro metro) {
        metro.addStation("Академмістечко");
        metro.addStation("Житомирська");
        metro.addStation("Святошин");
        metro.addStation("Нивки");
        metro.addStation("Берестейська");
        metro.addStation("Шулявська");
        metro.addStation("Політехнічний інститут");
        metro.addStation("Вокзальна");
        metro.addStation("Університет");
        metro.addStation("Театральна");
        metro.addStation("Хрещатик");
        metro.addStation("Арсенальна");
        metro.addStation("Дніпро");
        metro.addStation("Гідропарк");
        metro.addStation("Лівобережна");
        metro.addStation("Дарниця");
        metro.addStation("Чернігівська");
        metro.addStation("Лісова");

        metro.addStation("Героїв Дніпра");
        metro.addStation("Мінська");
        metro.addStation("Оболонь");
        metro.addStation("Петрівка");
        metro.addStation("Тараса Шевченка");
        metro.addStation("Контрактова площа");
        metro.addStation("Поштова площа");
        metro.addStation("Майдан Незалежності");
        metro.addStation("Площа Льва Толстого");
        metro.addStation("Олімпійська");
        metro.addStation("Палац 'Україна'");
        metro.addStation("Либідська");
        metro.addStation("Деміївська");
        metro.addStation("Голосіївська");
        metro.addStation("Васильківська");
        metro.addStation("Виставковий центр");
        metro.addStation("Іподром");
        metro.addStation("Теремки");

        metro.addStation("Сирець");
        metro.addStation("Дорогожичі");
        metro.addStation("Лук'янівська");
        metro.addStation("Золоті ворота");
        metro.addStation("Палац спорту");
        metro.addStation("Кловська");
        metro.addStation("Печерська");
        metro.addStation("Дружби народів");
        metro.addStation("Видубичі");
        metro.addStation("Славутич");
        metro.addStation("Осокорки");
        metro.addStation("Позняки");
        metro.addStation("Харківська");
        metro.addStation("Вирлиця");
        metro.addStation("Бориспільська");
        metro.addStation("Червоний хутір");

        metro.addConnection("Академмістечко", "Житомирська", 3);
        metro.addConnection("Житомирська", "Святошин", 3);
        metro.addConnection("Святошин", "Нивки", 2);
        metro.addConnection("Нивки", "Берестейська", 2);
        metro.addConnection("Берестейська", "Шулявська", 3);
        metro.addConnection("Шулявська", "Політехнічний інститут", 2);
        metro.addConnection("Політехнічний інститут", "Вокзальна", 3);
        metro.addConnection("Вокзальна", "Університет", 2);
        metro.addConnection("Університет", "Театральна", 2);
        metro.addConnection("Театральна", "Золоті ворота", 1);
        metro.addConnection("Театральна", "Хрещатик", 2);
        metro.addConnection("Хрещатик", "Майдан Незалежності", 1);
        metro.addConnection("Хрещатик", "Арсенальна", 3);
        metro.addConnection("Арсенальна", "Дніпро", 2);
        metro.addConnection("Дніпро", "Гідропарк", 3);
        metro.addConnection("Гідропарк", "Лівобережна", 3);
        metro.addConnection("Лівобережна", "Дарниця", 2);
        metro.addConnection("Дарниця", "Чернігівська", 2);
        metro.addConnection("Чернігівська", "Лісова", 2);

        metro.addConnection("Героїв Дніпра", "Мінська", 2);
        metro.addConnection("Мінська", "Оболонь", 2);
        metro.addConnection("Оболонь", "Петрівка", 3);
        metro.addConnection("Петрівка", "Тараса Шевченка", 3);
        metro.addConnection("Тараса Шевченка", "Контрактова площа", 2);
        metro.addConnection("Контрактова площа", "Поштова площа", 2);
        metro.addConnection("Поштова площа", "Майдан Незалежності", 2);
        metro.addConnection("Майдан Незалежності", "Площа Льва Толстого", 4);
        metro.addConnection("Площа Льва Толстого", "Олімпійська", 2);
        metro.addConnection("Площа Льва Толстого", "Палац спорту", 1);
        metro.addConnection("Олімпійська", "Палац 'Україна'", 2);
        metro.addConnection("Палац 'Україна'", "Либідська", 1);
        metro.addConnection("Либідська", "Деміївська", 2);
        metro.addConnection("Деміївська", "Голосіївська", 1);
        metro.addConnection("Голосіївська", "Васильківська", 2);
        metro.addConnection("Васильківська", "Виставковий центр", 3);
        metro.addConnection("Виставковий центр", "Іподром", 4);
        metro.addConnection("Іподром", "Теремки", 2);

        metro.addConnection("Сирець", "Дорогожичі", 2);
        metro.addConnection("Дорогожичі", "Лук'янівська", 4);
        metro.addConnection("Лук'янівська", "Золоті ворота", 5);
        metro.addConnection("Золоті ворота", "Палац спорту", 2);
        metro.addConnection("Палац спорту", "Кловська", 2);
        metro.addConnection("Кловська", "Печерська", 2);
        metro.addConnection("Печерська", "Дружби народів", 2);
        metro.addConnection("Дружби народів", "Видубичі", 3);
        metro.addConnection("Видубичі", "Славутич", 7);
        metro.addConnection("Осокорки", "Позняки", 4);
        metro.addConnection("Позняки", "Харківська", 4);
        metro.addConnection("Харківська", "Вирлиця", 2);
        metro.addConnection("Вирлиця", "Бориспільська", 2);
        metro.addConnection("Бориспільська", "Червоний хутір", 2);
    }

    //ініціалізація карти метро Лондона
    public void initializeMetroLondon(Metro metro) {
//        Бейкерлу Лайн (Bakerloo Line):
        metro.addStation("Harrow & Wealdstone");
        metro.addStation("Kenton");
        metro.addStation("South Kenton");
        metro.addStation("North Wembley");
        metro.addStation("Wembley Central");
        metro.addStation("Stonebridge Park");
        metro.addStation("Harlesden");
        metro.addStation("Willesden Junction");
        metro.addStation("Kensal Green");
        metro.addStation("Queen's Park");
        metro.addStation("Kilburn Park");
        metro.addStation("Maida Vale");
        metro.addStation("Warwick Avenue");
        metro.addStation("Paddington(Bakerloo)");
        metro.addStation("Edgware Road(Bakerloo)");
        metro.addStation("Marylebone");
        metro.addStation("Baker Street");
        metro.addStation("Regent's Park");
        metro.addStation("Oxford Circus");
        metro.addStation("Piccadilly Circus");
        metro.addStation("Charing Cross");
        metro.addStation("Embankment");
        metro.addStation("Waterloo");
        metro.addStation("Lambeth North");
        metro.addStation("Elephant & Castle");
//      Централ Лайн (Central Line):
        metro.addStation("Epping");
        metro.addStation("Theydon Bois");
        metro.addStation("Debden");
        metro.addStation("Loughton");
        metro.addStation("Buckhurst Hill");
        metro.addStation("Woodford");
        metro.addStation("Roding Valley");
        metro.addStation("Chigwell");
        metro.addStation("Grange Hill");
        metro.addStation("Hainault");
        metro.addStation("Fairlop");
        metro.addStation("Barkingside");
        metro.addStation("Newbury Park");
        metro.addStation("Gants Hill");
        metro.addStation("Redbridge");
        metro.addStation("Wanstead");
        metro.addStation("South Woodford");
        metro.addStation("Snaresbrook");
        metro.addStation("Leytonstone");
        metro.addStation("Leyton");
        metro.addStation("Stratford");
        metro.addStation("Mile End");
        metro.addStation("Bethnal Green");
        metro.addStation("Liverpool Street");
        metro.addStation("Bank");
        metro.addStation("St. Paul's");
        metro.addStation("Chancery Lane");
        metro.addStation("Holborn");
        metro.addStation("Tottenham Court Road");
        metro.addStation("Bond Street");
        metro.addStation("Marble Arch");
        metro.addStation("Lancaster Gate");
        metro.addStation("Notting Hill Gate");
        metro.addStation("Holland Park");
        metro.addStation("Shepherd's Bush");
        metro.addStation("White City");
        metro.addStation("East Acton");
        metro.addStation("North Acton");
        metro.addStation("West Acton");
        metro.addStation("Ealing Broadway");
        metro.addStation("Perivale");
        metro.addStation("Hanger Lane");
        metro.addStation("Greenford");
        metro.addStation("Northolt");
        metro.addStation("South Ruislip");
        metro.addStation("Ruislip Gardens");
        metro.addStation("West Ruislip");
//        Цінкуль Лайн (Circle Line):
        metro.addStation("Edgware Road(Circle)");
        metro.addStation("Great Portland Street");
        metro.addStation("Euston Square");
        metro.addStation("King's Cross St. Pancras");
        metro.addStation("Farringdon");
        metro.addStation("Barbican");
        metro.addStation("Moorgate");
        metro.addStation("Aldgate");
        metro.addStation("Tower Hill");
        metro.addStation("Monument");
        metro.addStation("Cannon Street");
        metro.addStation("Mansion House");
        metro.addStation("Blackfriars");
        metro.addStation("Temple");
        metro.addStation("Westminster");
        metro.addStation("St. James's Park");
        metro.addStation("Victoria");
        metro.addStation("Sloane Square");
        metro.addStation("South Kensington");
        metro.addStation("Gloucester Road");
        metro.addStation("High Street Kensington");
        metro.addStation("Bayswater");
//        "District Line" (Дістрікт лайн)
        metro.addStation("Upminster");
        metro.addStation("Upminster Bridge");
        metro.addStation("Hornchurch");
        metro.addStation("Elm Park");
        metro.addStation("Dagenham East");
        metro.addStation("Dagenham Heathway");
        metro.addStation("Becontree");
        metro.addStation("Upney");
        metro.addStation("Barking");
        metro.addStation("East Ham");
        metro.addStation("Upton Park");
        metro.addStation("Plaistow");
        metro.addStation("West Ham");
        metro.addStation("Bromley-by-Bow");
        metro.addStation("Bow Road");
        metro.addStation("Stepney Green");
        metro.addStation("Whitechapel");
        metro.addStation("Aldgate East");
        metro.addStation("Earl's Court");
        metro.addStation("West Kensington");
        metro.addStation("Barons Court");
        metro.addStation("Hammersmith");
        metro.addStation("Kensington(Olympia)");
        metro.addStation("West Brompton");
        metro.addStation("Fulham Broadway");
        metro.addStation("Parsons Green");
        metro.addStation("Puthey Bridge");
        metro.addStation("East Putney");
        metro.addStation("Southfields");
        metro.addStation("Wimbledon Park");
        metro.addStation("Wimbledon");
        metro.addStation("Ravenscourt Park");
        metro.addStation("Stamford Brook");
        metro.addStation("Turnham Green");
        metro.addStation("Richmond");
        metro.addStation("Kew Gardens");
        metro.addStation("Gunnersbury");
        metro.addStation("Chiswick Park");
        metro.addStation("Acton Town");
        metro.addStation("Ealing Common");
//        East London
        metro.addStation("New Cross Gate");
        metro.addStation("New Cross");
        metro.addStation("Surrey Quays");
        metro.addStation("Canada Water");
        metro.addStation("Rotherhithe");
        metro.addStation("Wapping");
        metro.addStation("Shadwell");
        metro.addStation("Shoreditch");
//        "Hammersmith & City Line"
        metro.addStation("Goldhawk Road");
        metro.addStation("Shepherd's Bush Market");
        metro.addStation("Latimer Road");
        metro.addStation("Ladbroke Grove");
        metro.addStation("Westbourne Park");
        metro.addStation("Royal Oak");
        metro.addStation("Paddington(Hammersmith & City Line)");
//        Jubilee Line
        metro.addStation("Stanmore");
        metro.addStation("Canons Park");
        metro.addStation("Queensbury");
        metro.addStation("Kingsbury");
        metro.addStation("Wembley Park");
        metro.addStation("Neasden");
        metro.addStation("Dollis Hill");
        metro.addStation("Willesden Green");
        metro.addStation("Kilburn");
        metro.addStation("West Hampstead");
        metro.addStation("Finchley Road");
        metro.addStation("Swiss Cottage");
        metro.addStation("St. John's Wood");
        metro.addStation("Green Park");
        metro.addStation("Southwark");
        metro.addStation("London Bridge");
        metro.addStation("Bermondsey");
        metro.addStation("Canary Wharf");
        metro.addStation("North Greenwich");
        metro.addStation("Canning Town");
        metro.addStation("Stratford");
//        "Metropolitan Line"
        metro.addStation("Preston Road");
        metro.addStation("Northwick Park");
        metro.addStation("Harrow-on-the-Hill");
        metro.addStation("North Harrow");
        metro.addStation("Pinner");
        metro.addStation("Northwood Hills");
        metro.addStation("Northwood");
        metro.addStation("Rickmansworth");
        metro.addStation("Chorleywood");
        metro.addStation("Chalfont & Latimer");
        metro.addStation("Amersham");
        metro.addStation("Chesham");
        metro.addStation("Moor Park");
        metro.addStation("Croxley");
        metro.addStation("Watford");
        metro.addStation("West Harrow");
//        Northern Line
        metro.addStation("High Barnet");
        metro.addStation("Totteridge & Whetstone");
        metro.addStation("Woodside Park");
        metro.addStation("West Finchley");
        metro.addStation("Finchley Central");
        metro.addStation("East Finchley");
        metro.addStation("Highgate");
        metro.addStation("Archway");
        metro.addStation("Tufnell Park");
        metro.addStation("Kentish Town");
        metro.addStation("Mill Hill East");
        metro.addStation("Edgware");
        metro.addStation("Burnt Oak");
        metro.addStation("Colindale");
        metro.addStation("Hendon Central");
        metro.addStation("Brent Cross");
        metro.addStation("Golders Green");
        metro.addStation("Hampstead");
        metro.addStation("Belsize Park");
        metro.addStation("Chalk Farm");
        metro.addStation("Camden Town");
        metro.addStation("Mornington Crescent");
        metro.addStation("Euston");
        metro.addStation("Warren Street");
        metro.addStation("Goodge Street");
        metro.addStation("Leicester Square");
        metro.addStation("Kennington");
        metro.addStation("Oval");
        metro.addStation("Stockwell");
        metro.addStation("Clapham North");
        metro.addStation("Clapham Common");
        metro.addStation("Clapham South");
        metro.addStation("Balham");
        metro.addStation("Tooting Bec");
        metro.addStation("Tooting Broadway");
        metro.addStation("Colliers Wood");
        metro.addStation("South Wimbledon");
        metro.addStation("Morden");
        metro.addStation("Borough");
        metro.addStation("Angel");
        metro.addStation("Old Street");
//      Piccadilly Line
        metro.addStation("Heathrow Terminal 4");
        metro.addStation("Heathrow Terminals 1, 2, 3");
        metro.addStation("Hatton Cross");
        metro.addStation("Hounslow West");
        metro.addStation("Hounslow Central");
        metro.addStation("Hounslow East");
        metro.addStation("Osterley");
        metro.addStation("Boston Manor");
        metro.addStation("Northfields");
        metro.addStation("South Ealing");
        metro.addStation("Knightsbridge");
        metro.addStation("Hyde Park Corner");
        metro.addStation("Covent Garden");
        metro.addStation("Russell Square");
        metro.addStation("Caledonian Road");
        metro.addStation("Holloway Road");
        metro.addStation("Arsenal");
        metro.addStation("Finsbury Park");
        metro.addStation("Manor House");
        metro.addStation("Turnpike Lane");
        metro.addStation("Wood Green");
        metro.addStation("Bounds Green");
        metro.addStation("Arnos Grove");
        metro.addStation("Southgate");
        metro.addStation("Oakwood");
        metro.addStation("Cockfosters");
        metro.addStation("North Ealing");
        metro.addStation("Park Royal");
        metro.addStation("Alperton");
        metro.addStation("Sudbury Town");
        metro.addStation("Sudbury Hill");
        metro.addStation("South Harrow");
        metro.addStation("Rayners Lane");
        metro.addStation("Eastcode");
        metro.addStation("Ruislip Manor");
        metro.addStation("Ruislip");
        metro.addStation("Ickenham");
        metro.addStation("Hilingdon");
        metro.addStation("Uxbridge");
//      Victoria Line
        metro.addStation("Walthamstow Central");
        metro.addStation("Blackhorse Road");
        metro.addStation("Tottenham Hale");
        metro.addStation("Seven Sisters");
        metro.addStation("Pimlico");
        metro.addStation("Vauxhall");
        metro.addStation("Brixton");

        metro.addConnection("Harrow & Wealdstone", "Kenton", 5);
        metro.addConnection("Kenton", "South Kenton", 5);
        metro.addConnection("South Kenton", "North Wembley", 1);
        metro.addConnection("North Wembley", "Wembley Central", 1);
        metro.addConnection("Wembley Central", "Stonebridge Park", 1);
        metro.addConnection("Stonebridge Park", "Harlesden", 1);
        metro.addConnection("Harlesden", "Willesden Junction", 2);
        metro.addConnection("Willesden Junction", "Kensal Green", 1);
        metro.addConnection("Kensal Green", "Queen's Park", 1);
        metro.addConnection("Queen's Park", "Kilburn Park", 3);
        metro.addConnection("Kilburn Park", "Maida Vale", 1);
        metro.addConnection("Maida Vale", "Warwick Avenue", 1);
        metro.addConnection("Warwick Avenue", "Paddington(Bakerloo)", 4);
        metro.addConnection("Paddington(Bakerloo)", "Edgware Road(Bakerloo)", 3);
        metro.addConnection("Edgware Road(Bakerloo)", "Marylebone", 2);
        metro.addConnection("Paddington(Bakerloo)", "Bayswater", 1);
        metro.addConnection("Marylebone", "Baker Street", 6);
        metro.addConnection("Marylebone", "Regent's Park", 7);
        metro.addConnection("Regent's Park", "Oxford Circus", 4);
        metro.addConnection("Oxford Circus", "Piccadilly Circus", 4);
        metro.addConnection("Piccadilly Circus", "Charing Cross", 3);
        metro.addConnection("Charing Cross", "Embankment", 3);
        metro.addConnection("Embankment", "Waterloo", 4);
        metro.addConnection("Waterloo", "Bank", 1);
        metro.addConnection("Waterloo", "Lambeth North", 5);
        metro.addConnection("Lambeth North", "Elephant & Castle", 3);
        metro.addConnection("Epping", "Theydon Bois", 2);
        metro.addConnection("Theydon Bois", "Debden", 2);
        metro.addConnection("Debden", "Loughton", 2);
        metro.addConnection("Loughton", "Buckhurst Hill", 3);
        metro.addConnection("Buckhurst Hill", "Woodford", 3);
        metro.addConnection("Woodford", "Roding Valley", 4);
        metro.addConnection("Roding Valley", "Chigwell", 2);
        metro.addConnection("Chigwell", "Grange Hill", 3);
        metro.addConnection("Grange Hill", "Hainault", 1);
        metro.addConnection("Hainault", "Fairlop", 1);
        metro.addConnection("Fairlop", "Barkingside", 1);
        metro.addConnection("Barkingside", "Newbury Park", 1);
        metro.addConnection("Newbury Park", "Gants Hill", 3);
        metro.addConnection("Gants Hill", "Redbridge", 2);
        metro.addConnection("Redbridge", "Wanstead", 2);
        metro.addConnection("Wanstead", "Leytonstone", 2);
        metro.addConnection("Woodford", "South Woodford", 2);
        metro.addConnection("South Woodford", "Snaresbrook", 2);
        metro.addConnection("Snaresbrook", "Leytonstone", 3);
        metro.addConnection("Leytonstone", "Leyton", 2);
        metro.addConnection("Leyton", "Stratford", 4);
        metro.addConnection("Stratford", "Mile End", 7);
        metro.addConnection("Mile End", "Bethnal Green", 4);
        metro.addConnection("Bethnal Green", "Liverpool Street", 6);
        metro.addConnection("Liverpool Street", "Bank", 7);
        metro.addConnection("Bank", "St. Paul's", 4);
        metro.addConnection("St. Paul's", "Chancery Lane", 2);
        metro.addConnection("Chancery Lane", "Holborn", 3);
        metro.addConnection("Holborn", "Tottenham Court Road", 2);
        metro.addConnection("Tottenham Court Road", "Oxford Circus", 4);
        metro.addConnection("Oxford Circus", "Bond Street", 3);
        metro.addConnection("Bond Street", "Marble Arch", 2);
        metro.addConnection("Marble Arch", "Lancaster Gate", 2);
        metro.addConnection("Lancaster Gate", "Notting Hill Gate", 3);
        metro.addConnection("Notting Hill Gate", "Holland Park", 3);
        metro.addConnection("Holland Park", "Shepherd's Bush", 2);
        metro.addConnection("Shepherd's Bush", "White City", 5);
        metro.addConnection("White City", "East Acton", 4);
        metro.addConnection("East Acton", "North Acton", 3);
        metro.addConnection("North Acton", "West Acton", 2);
        metro.addConnection("North Acton", "Hanger Lane", 8);
        metro.addConnection("Hanger Lane", "Perivale", 5);
        metro.addConnection("Perivale", "Greenford", 6);
        metro.addConnection("Greenford", "Northolt", 5);
        metro.addConnection("Northolt", "South Ruislip", 1);
        metro.addConnection("South Ruislip", "Ruislip Gardens", 2);
        metro.addConnection("Ruislip Gardens", "West Ruislip", 7);
        metro.addConnection("Edgware Road(Circle)", "Paddington(Hammersmith & City Line)", 4);
        metro.addConnection("Edgware Road(Circle)", "Baker Street", 5);
        metro.addConnection("Baker Street", "Great Portland Street", 4);
        metro.addConnection("Great Portland Street", "Euston Square", 5);
        metro.addConnection("Euston Square", "King's Cross St. Pancras", 4);
        metro.addConnection("King's Cross St. Pancras", "Farringdon", 3);
        metro.addConnection("Farringdon", "Barbican", 2);
        metro.addConnection("Barbican", "Moorgate", 5);
        metro.addConnection("Moorgate", "Liverpool Street", 5);
        metro.addConnection("Liverpool Street", "Aldgate", 5);
        metro.addConnection("Liverpool Street", "Aldgate East", 6);
        metro.addConnection("Aldgate", "Tower Hill", 2);
        metro.addConnection("Tower Hill", "Monument", 3);
        metro.addConnection("Monument", "Cannon Street", 4);
        metro.addConnection("Monument", "Bank", 1);
        metro.addConnection("Cannon Street", "Mansion House", 2);
        metro.addConnection("Mansion House", "Blackfriars", 1);
        metro.addConnection("Blackfriars", "Temple", 2);
        metro.addConnection("Temple", "Embankment", 3);
        metro.addConnection("Embankment", "Westminster", 2);
        metro.addConnection("Westminster", "St. James's Park", 3);
        metro.addConnection("St. James's Park", "Victoria", 2);
        metro.addConnection("Victoria", "Sloane Square", 2);
        metro.addConnection("Sloane Square", "South Kensington", 3);
        metro.addConnection("South Kensington", "Gloucester Road", 1);
        metro.addConnection("Gloucester Road", "High Street Kensington", 7);
        metro.addConnection("High Street Kensington", "Notting Hill Gate", 4);
        metro.addConnection("Notting Hill Gate", "Bayswater", 3);
        metro.addConnection("Bayswater", "Edgware Road(Circle)", 3);
        metro.addConnection("Upminster", "Upminster Bridge", 2);
        metro.addConnection("Upminster Bridge", "Hornchurch", 3);
        metro.addConnection("Hornchurch", "Elm Park", 1);
        metro.addConnection("Elm Park", "Dagenham East", 1);
        metro.addConnection("Dagenham East", "Dagenham Heathway", 2);
        metro.addConnection("Dagenham Heathway", "Becontree", 2);
        metro.addConnection("Becontree", "Upney", 1);
        metro.addConnection("Upney", "Barking", 1);
        metro.addConnection("Barking", "East Ham", 3);
        metro.addConnection("East Ham", "Upton Park", 2);
        metro.addConnection("Upton Park", "Plaistow", 1);
        metro.addConnection("Plaistow", "West Ham", 1);
        metro.addConnection("West Ham", "Bromley-by-Bow", 2);
        metro.addConnection("Bromley-by-Bow", "Bow Road", 4);
        metro.addConnection("Bow Road", "Mile End", 4);
        metro.addConnection("Bow Road", "Stepney Green", 5);
        metro.addConnection("Stepney Green", "Whitechapel", 2);
        metro.addConnection("Whitechapel", "Aldgate East", 1);
        metro.addConnection("Aldgate East", "Tower Hill", 5);
        metro.addConnection("Gloucester Road", "Earl's Court", 2);
        metro.addConnection("Earl's Court", "West Kensington", 1);
        metro.addConnection("West Kensington", "Barons Court", 1);
        metro.addConnection("Barons Court", "Hammersmith", 1);
        metro.addConnection("Gloucester Road", "Kensington(Olympia)", 2);
        metro.addConnection("Hammersmith", "Ravenscourt Park", 2);
        metro.addConnection("Ravenscourt Park", "Stamford Brook", 3);
        metro.addConnection("Stamford Brook", "Turnham Green", 2);
        metro.addConnection("Turnham Green", "Chiswick Park", 2);
        metro.addConnection("Chiswick Park", "Acton Town", 2);
        metro.addConnection("Acton Town", "Ealing Common", 4);
        metro.addConnection("Ealing Common", "Ealing Broadway", 4);
        metro.addConnection("Turnham Green", "Gunnersbury", 4);
        metro.addConnection("Gunnersbury", "Kew Gardens", 3);
        metro.addConnection("Kew Gardens", "Richmond", 3);
        metro.addConnection("Earl's Court", "West Brompton", 4);
        metro.addConnection("West Brompton", "Fulham Broadway", 2);
        metro.addConnection("Fulham Broadway", "Parsons Green", 2);
        metro.addConnection("Parsons Green", "Puthey Bridge", 2);
        metro.addConnection("Puthey Bridge", "East Putney", 2);
        metro.addConnection("East Putney", "Southfields", 2);
        metro.addConnection("Southfields", "Wimbledon Park", 2);
        metro.addConnection("Wimbledon Park", "Wimbledon", 2);
        metro.addConnection("New Cross Gate", "Surrey Quays", 8);
        metro.addConnection("New Cross", "Surrey Quays", 7);
        metro.addConnection("Surrey Quays", "Canada Water", 6);
        metro.addConnection("Canada Water", "Rotherhithe", 1);
        metro.addConnection("Rotherhithe", "Wapping", 2);
        metro.addConnection("Wapping", "Shadwell", 2);
        metro.addConnection("Shadwell", "Whitechapel", 5);
        metro.addConnection("Whitechapel", "Shoreditch", 2);
        metro.addConnection("Hammersmith", "Goldhawk Road", 2);
        metro.addConnection("Goldhawk Road", "Shepherd's Bush Market", 2);
        metro.addConnection("Shepherd's Bush Market", "Latimer Road", 9);
        metro.addConnection("Latimer Road", "Ladbroke Grove", 1);
        metro.addConnection("Ladbroke Grove", "Westbourne Park", 1);
        metro.addConnection("Westbourne Park", "Royal Oak", 1);
        metro.addConnection("Royal Oak", "Paddington(Hammersmith & City Line)", 2);
        metro.addConnection("Stanmore", "Canons Park", 3);
        metro.addConnection("Canons Park", "Queensbury", 2);
        metro.addConnection("Queensbury", "Kingsbury", 2);
        metro.addConnection("Kingsbury", "Wembley Park", 2);
        metro.addConnection("Wembley Park", "Neasden", 2);
        metro.addConnection("Neasden", "Dollis Hill", 3);
        metro.addConnection("Dollis Hill", "Willesden Green", 2);
        metro.addConnection("Willesden Green", "Kilburn", 2);
        metro.addConnection("Kilburn", "West Hampstead", 3);
        metro.addConnection("West Hampstead", "Finchley Road", 2);
        metro.addConnection("Finchley Road", "Swiss Cottage", 1);
        metro.addConnection("Swiss Cottage", "St. John's Wood", 1);
        metro.addConnection("St. John's Wood", "Baker Street", 6);
        metro.addConnection("Baker Street", "Bond Street", 6);
        metro.addConnection("Bond Street", "Green Park", 4);
        metro.addConnection("Green Park", "Westminster", 7);
        metro.addConnection("Westminster", "Waterloo", 5);
        metro.addConnection("Waterloo", "Southwark", 5);
        metro.addConnection("Southwark", "London Bridge", 10);
        metro.addConnection("London Bridge", "Bermondsey", 6);
        metro.addConnection("Bermondsey", "Canada Water", 5);
        metro.addConnection("Canada Water", "Canary Wharf", 7);
        metro.addConnection("Canary Wharf", "North Greenwich", 3);
        metro.addConnection("North Greenwich", "Canning Town", 6);
        metro.addConnection("Canning Town", "West Ham", 7);
        metro.addConnection("West Ham", "Stratford", 4);
        metro.addConnection("Baker Street", "Finchley Road", 6);
        metro.addConnection("Finchley Road", "Wembley Park", 10);
        metro.addConnection("Wembley Park", "Preston Road", 3);
        metro.addConnection("Preston Road", "Northwick Park", 4);
        metro.addConnection("Northwick Park", "Harrow-on-the-Hill", 2);
        metro.addConnection("Harrow-on-the-Hill", "North Harrow", 4);
        metro.addConnection("North Harrow", "Pinner", 2);
        metro.addConnection("Pinner", "Northwood Hills", 3);
        metro.addConnection("Northwood Hills", "Northwood", 2);
        metro.addConnection("Northwood", "Moor Park", 2);
        metro.addConnection("Moor Park", "Croxley", 3);
        metro.addConnection("Croxley", "Watford", 2);
        metro.addConnection("Moor Park", "Rickmansworth", 3);
        metro.addConnection("Rickmansworth", "Chorleywood", 2);
        metro.addConnection("Chorleywood", "Chalfont & Latimer", 3);
        metro.addConnection("Chalfont & Latimer", "Amersham", 5);
        metro.addConnection("Chalfont & Latimer", "Chesham", 4);
        metro.addConnection("Harrow-on-the-Hill", "West Harrow", 5);
        metro.addConnection("High Barnet", "Totteridge & Whetstone", 3);
        metro.addConnection("Totteridge & Whetstone", "Woodside Park", 2);
        metro.addConnection("Woodside Park", "West Finchley", 2);
        metro.addConnection("West Finchley", "Finchley Central", 2);
        metro.addConnection("Mill Hill East", "Finchley Central", 4);
        metro.addConnection("Finchley Central", "East Finchley", 3);
        metro.addConnection("East Finchley", "Highgate", 2);
        metro.addConnection("Highgate", "Archway", 2);
        metro.addConnection("Archway", "Tufnell Park", 2);
        metro.addConnection("Tufnell Park", "Kentish Town", 2);
        metro.addConnection("Kentish Town", "Camden Town", 6);
        metro.addConnection("Edgware", "Burnt Oak", 2);
        metro.addConnection("Burnt Oak", "Colindale", 2);
        metro.addConnection("Colindale", "Hendon Central", 2);
        metro.addConnection("Hendon Central", "Brent Cross", 2);
        metro.addConnection("Brent Cross", "Golders Green", 2);
        metro.addConnection("Golders Green", "Hampstead", 2);
        metro.addConnection("Hampstead", "Belsize Park", 4);
        metro.addConnection("Belsize Park", "Chalk Farm", 2);
        metro.addConnection("Chalk Farm", "Camden Town", 2);
        metro.addConnection("Camden Town", "Mornington Crescent", 2);
        metro.addConnection("Camden Town", "Euston", 6);
        metro.addConnection("Mornington Crescent", "Euston", 3);
        metro.addConnection("Euston", "King's Cross St. Pancras", 3);
        metro.addConnection("Euston", "Warren Street", 4);
        metro.addConnection("Euston", "Angel", 7);
        metro.addConnection("Angel", "Old Street", 2);
        metro.addConnection("Old Street", "Moorgate", 3);
        metro.addConnection("Moorgate", "Bank", 3);
        metro.addConnection("Monument", "London Bridge", 4);
        metro.addConnection("London Bridge", "Borough", 5);
        metro.addConnection("Borough", "Elephant & Castle", 5);
        metro.addConnection("Elephant & Castle", "Kennington", 2);
        metro.addConnection("Warren Street", "Goodge Street", 3);
        metro.addConnection("Goodge Street", "Tottenham Court Road", 1);
        metro.addConnection("Tottenham Court Road", "Leicester Square", 4);
        metro.addConnection("Leicester Square", "Charing Cross", 2);
        metro.addConnection("Charing Cross", "Embankment", 1);
        metro.addConnection("Waterloo", "Kennington", 7);
        metro.addConnection("Kennington", "Oval", 3);
        metro.addConnection("Oval", "Stockwell", 2);
        metro.addConnection("Stockwell", "Clapham North", 2);
        metro.addConnection("Clapham North", "Clapham Common", 1);
        metro.addConnection("Clapham Common", "Clapham South", 1);
        metro.addConnection("Clapham South", "Balham", 2);
        metro.addConnection("Balham", "Tooting Bec", 2);
        metro.addConnection("Tooting Bec", "Tooting Broadway", 2);
        metro.addConnection("Tooting Broadway", "Colliers Wood", 2);
        metro.addConnection("Colliers Wood", "South Wimbledon", 2);
        metro.addConnection("South Wimbledon", "Morden", 2);
        metro.addConnection("Heathrow Terminal 4", "Hatton Cross", 5);
        metro.addConnection("Heathrow Terminals 1, 2, 3", "Hatton Cross", 4);
        metro.addConnection("Hatton Cross", "Hounslow West", 2);
        metro.addConnection("Hounslow West", "Hounslow Central", 3);
        metro.addConnection("Hounslow Central", "Hounslow East", 1);
        metro.addConnection("Hounslow East", "Osterley", 1);
        metro.addConnection("Osterley", "Boston Manor", 1);
        metro.addConnection("Boston Manor", "Northfields", 1);
        metro.addConnection("Northfields", "South Ealing", 2);
        metro.addConnection("South Ealing", "Acton Town", 1);
        metro.addConnection("South Kensington", "Knightsbridge", 4);
        metro.addConnection("Knightsbridge", "Hyde Park Corner", 3);
        metro.addConnection("Hyde Park Corner", "Green Park", 1);
        metro.addConnection("Green Park", "Piccadilly Circus", 5);
        metro.addConnection("Piccadilly Circus", "Leicester Square", 3);
        metro.addConnection("Leicester Square", "Covent Garden", 2);
        metro.addConnection("Covent Garden", "Holborn", 3);
        metro.addConnection("Holborn", "Russell Square", 4);
        metro.addConnection("Russell Square", "King's Cross St. Pancras", 3);
        metro.addConnection("King's Cross St. Pancras", "Caledonian Road", 10);
        metro.addConnection("Caledonian Road", "Holloway Road", 2);
        metro.addConnection("Holloway Road", "Arsenal", 2);
        metro.addConnection("Arsenal", "Finsbury Park", 2);
        metro.addConnection("Finsbury Park", "Manor House", 5);
        metro.addConnection("Manor House", "Turnpike Lane", 2);
        metro.addConnection("Turnpike Lane", "Wood Green", 2);
        metro.addConnection("Wood Green", "Bounds Green", 2);
        metro.addConnection("Bounds Green", "Arnos Grove", 2);
        metro.addConnection("Arnos Grove", "Southgate", 2);
        metro.addConnection("Southgate", "Oakwood", 2);
        metro.addConnection("Oakwood", "Cockfosters", 2);
        metro.addConnection("Ealing Common", "North Ealing", 5);
        metro.addConnection("North Ealing", "Park Royal", 2);
        metro.addConnection("Park Royal", "Alperton", 8);
        metro.addConnection("Alperton", "Sudbury Town", 2);
        metro.addConnection("Sudbury Town", "Sudbury Hill", 2);
        metro.addConnection("Sudbury Hill", "South Harrow", 2);
        metro.addConnection("South Harrow", "Rayners Lane", 4);
        metro.addConnection("West Harrow", "Rayners Lane", 3);
        metro.addConnection("Rayners Lane", "Eastcode", 2);
        metro.addConnection("Eastcode", "Ruislip Manor", 2);
        metro.addConnection("Ruislip Manor", "Ruislip", 2);
        metro.addConnection("Ruislip", "Ickenham", 3);
        metro.addConnection("Ickenham", "Hilingdon", 2);
        metro.addConnection("Hilingdon", "Uxbridge", 3);
        metro.addConnection("Walthamstow Central", "Blackhorse Road", 2);
        metro.addConnection("Blackhorse Road", "Tottenham Hale", 2);
        metro.addConnection("Tottenham Hale", "Seven Sisters", 2);
        metro.addConnection("Seven Sisters", "Finsbury Park", 4);
        metro.addConnection("Finsbury Park", "King's Cross St. Pancras", 15);
        metro.addConnection("Euston", "Oxford Circus", 6);
        metro.addConnection("Oxford Circus", "Green Park", 3);
        metro.addConnection("Green Park", "Victoria", 4);
        metro.addConnection("Victoria", "Pimlico", 6);
        metro.addConnection("Pimlico", "Vauxhall", 4);
        metro.addConnection("Vauxhall", "Stockwell", 6);
        metro.addConnection("Stockwell", "Brixton", 4);
    }

    //метод для взяття URL з шляху рисунку
    private Image getImageFromPath(String url) throws MalformedURLException {
        File file = new File(url);
        if (file.exists()) {
            return new Image(file.toURI().toURL().toExternalForm());
        }
        return null;
    }

    //настроюємо горизонтальний ряд вводу початкової станції
    private HBox setInputStartStation(Metro metro) {
        //текст для краси
        final Text textStartStation = new Text("Введіть початкову станцію: ");
        textStartStation.setFont(Font.font(14));
        //список з якого користувач вибирає станції
        ComboBox<String> inputStartStation = new ComboBox<>(
                FXCollections.observableArrayList(metro.getAllStations())
        );
        // додавання обробника подій для ComboBox
        inputStartStation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startStation = inputStartStation.getValue();

            }
        });

        HBox hboxinputStartStation = new HBox(10); // 10 - відстань між елементами
        hboxinputStartStation.getChildren().addAll(textStartStation, inputStartStation);

        return hboxinputStartStation;
    }

    //настроюємо горизонтальний ряд вводу кінцевої станції
    private HBox setInputEndStation(Metro metro) {
        //текст для краси
        Text textEndStation = new Text("Введіть кінцеву станцію: ");
        textEndStation.setFont(Font.font(14));
        //список з якого користувач вибирає станції
        ComboBox<String> inputEndStation = new ComboBox<>(
                FXCollections.observableArrayList(metro.getAllStations())
        );
        // Додавання обробника подій для ComboBox
        inputEndStation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                endStation = inputEndStation.getValue();
            }
        });

        HBox hboxinputEndStation = new HBox(10); // 10 - відстань між елементами
        hboxinputEndStation.getChildren().addAll(textEndStation, inputEndStation);

        return hboxinputEndStation;
    }

    //налаштовуємо кнопки
    private HBox setButtons(Metro metro, String url, double imageWidth, double imageHeight, double sceneWidth, double sceneHeight) {
        //створення кнопки
        final Button findButtonD = new Button("Знайти шлях(за алгоритмом дейкстри)");
        final Button findButtonF = new Button("Знайти шлях(за алгоритмом флойда)");
        HBox.setHgrow(findButtonD, Priority.ALWAYS);
        HBox.setHgrow(findButtonF, Priority.ALWAYS);
        findButtonD.setMaxWidth(Double.MAX_VALUE);
        findButtonF.setMaxWidth(Double.MAX_VALUE);

        //реалізація виконання при натискані кнопки
        findButtonD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Dijkstra dijkstra = new Dijkstra();

                // Вимірювання часу для алгоритму Дейкстри
                long startTime = System.nanoTime();

                // визов алгоритму
                int shortestDistance = dijkstra.findShortestDistance(metro, startStation, endStation);

                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                System.out.println("Time Taken: " + duration + " nanoseconds");

                //текст для виводу отриманих результатів
                Text shortesDisText = new Text("Найкоротша відстань з " + startStation + " до " + endStation + ": " + shortestDistance);
                shortesDisText.setFont(Font.font(20));
                Text shortesPathText = new Text("По такому шляху :");
                shortesPathText.setFont(Font.font(20));

                //список з прокруткою
                ObservableList<String> shortestPath = FXCollections.observableArrayList();
                shortestPath.addAll(dijkstra.getShortestPath());
                ListView<String> listView = new ListView<>(shortestPath);

                //додавання рисунку(карти метро) з ресурсів додатку
                Image image = null;
                try {
                    image = getImageFromPath(url);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageWidth);
                imageView.setFitHeight(imageHeight);

                final VBox newVbox = new VBox();
                // відстань між елементам
                newVbox.setSpacing(10);
                // встановлюємо padding у 5 пікселів у всіх напрямках
                newVbox.setPadding(new Insets(10));
                newVbox.setAlignment(Pos.CENTER);
                newVbox.getChildren().addAll(imageView, shortesDisText, shortesPathText, listView);

                Scene newScene = new Scene(newVbox, 400, 300);
                primaryStage.setHeight(sceneHeight);
                primaryStage.setWidth(sceneWidth);
                primaryStage.setScene(newScene);
            }
        });
        findButtonF.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FloydWarshall floydWarshall = new FloydWarshall();
                // Вимірювання часу для алгоритму Флойда
                long startTime = System.nanoTime();

                //визов алгоритму
                int shortestDistance = floydWarshall.findShortestDistance(metro, startStation, endStation);

                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                System.out.println("Time Taken: " + duration + " nanoseconds");

                //текст для виводу отриманих результатів
                Text shortesDisText = new Text("Найкоротша відстань з " + startStation + " до " + endStation + ": " + shortestDistance);
                shortesDisText.setFont(Font.font(20));
                //додавання рисунку(карти метро) з ресурсів додатку
                Image image = null;
                try {
                    image = getImageFromPath(url);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageWidth);
                imageView.setFitHeight(imageHeight);

                final VBox newVbox = new VBox();
                // відстань між елементам
                newVbox.setSpacing(10);
                // встановлюємо padding у 5 пікселів у всіх напрямках
                newVbox.setPadding(new Insets(10));
                newVbox.setAlignment(Pos.CENTER);
                newVbox.getChildren().addAll(imageView, shortesDisText);

                Scene newScene = new Scene(newVbox, 400, 300);
                primaryStage.setHeight(sceneHeight);
                primaryStage.setWidth(sceneWidth);
                primaryStage.setScene(newScene);
            }
        });

        // Створюємо горизонтальний ряд
        HBox hbButton = new HBox();
        // Відстань між елементами ряду
        hbButton.setSpacing(5);
        // Додаємо до ряду елементи. У нашому випадку – кнопки
        hbButton.getChildren().addAll(findButtonD, findButtonF);
        // Говоримо, що елементи в ряді мають бути вирівняні по центру
        hbButton.setAlignment(Pos.CENTER);

        return hbButton;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
