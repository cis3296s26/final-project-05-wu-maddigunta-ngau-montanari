module irlquestbook {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires transitive javafx.base;
    requires com.google.gson;

    exports irlquestbook;
    exports irlquestbook.model;
    exports irlquestbook.view;
    exports irlquestbook.service;
}
