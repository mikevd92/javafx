package commons;

public class TextFieldTableCell<S,T> extends AbstractEditableTableCell<S, T> {
    public TextFieldTableCell() {
    }
    @Override
    protected String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
    @SuppressWarnings("unchecked")
	@Override
    protected void commitHelper( boolean losingFocus ) {
        commitEdit(((T) textField.getText()));
    }
}
