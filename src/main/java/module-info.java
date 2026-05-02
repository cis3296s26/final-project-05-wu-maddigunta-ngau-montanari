module irlquestbook {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires transitive javafx.base;
    requires com.google.gson;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;

    exports irlquestbook;
    exports irlquestbook.model;
    exports irlquestbook.view;
}
