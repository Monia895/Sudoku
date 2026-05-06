package sudoku.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitDocument extends PlainDocument {

    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {

        if (str == null) return;

        if (getLength() + str.length() > 1) return;

        char c = str.charAt(0);
        if (c < '1' || c > '9') return;

        super.insertString(offset, str, attr);
    }
}
