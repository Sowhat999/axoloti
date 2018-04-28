package axoloti.swingui.menus;

import axoloti.mvc.AbstractDocumentRoot;
import axoloti.swingui.mvc.UndoUI;
import axoloti.target.TargetController;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 *
 * @author jtaelman
 */
public class StandardMenubar extends JMenuBar {

    public FileMenu fileMenu;

    public StandardMenubar(AbstractDocumentRoot documentRoot) {
        fileMenu = new axoloti.swingui.menus.FileMenu("File");
        fileMenu.initComponents();
        add(fileMenu);

        if ((documentRoot != null) && (documentRoot.getUndoManager() != null)) {
            UndoUI undoUi = new UndoUI(documentRoot.getUndoManager());
            documentRoot.addUndoListener(undoUi);
            JMenu editMenu = new JMenu("Edit");
            editMenu.add(undoUi.createMenuItemUndo());
            editMenu.add(undoUi.createMenuItemRedo());
            add(editMenu);
        }

        JMenu boardMenu = new axoloti.swingui.target.TargetMenu(TargetController.getTargetController());
        add(boardMenu);

        JMenu windowMenu1 = new axoloti.swingui.menus.WindowMenu();
        add(windowMenu1);
        JMenu helpMenu1 = new axoloti.swingui.menus.HelpMenu();
        helpMenu1.setText("Help");
        add(helpMenu1);
    }
}
