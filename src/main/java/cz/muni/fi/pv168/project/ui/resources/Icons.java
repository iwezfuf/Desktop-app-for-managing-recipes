package cz.muni.fi.pv168.project.ui.resources;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Icons {
    public static final Icon NUCLEAR_QUIT_ICON = createIcon("Nuclear.png");
    public static final Icon DELETE_ICON = createIcon("xmark-solid.png");
    public static final Icon EDIT_ICON = createIcon("edit.png");

    public static final Icon ADD_ICON = createIcon("plus-solid.png");
    public static final Icon QUIT_ICON = createIcon("power-off-solid.png");
    public static final Icon IMPORT_ICON = createIcon("file-import-solid.png");
    public static final Icon EXPORT_ICON = createIcon("file-export-solid.png");
    public static final Icon INGREDIENTS_ICON = createIcon("ingredients_small.png");
    public static final Icon CATEGORY_ICON = createIcon("list.png");
    public static final Icon BOOK_ICON = createIcon("book-solid.png");
    public static final Icon WEIGHTS_ICON = createIcon("scale-unbalanced-flip-solid.png");
    public static final Icon APP_ICON = createIcon("recipe.png");

    private Icons() {
        throw new AssertionError("This class is not instantiable");
    }

    private static ImageIcon createIcon(String name) {
        URL url = Icons.class.getResource(name);
        if (url == null) {
            throw new IllegalArgumentException("Icon resource not found on classpath: " + name);
        }
        return new ImageIcon(url);
    }
}
