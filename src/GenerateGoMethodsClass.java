import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.regex.Pattern;

public class GenerateGoMethodsClass extends EditorAction {

    public GenerateGoMethodsClass() {
        this(new UpHandler());
    }

    protected GenerateGoMethodsClass(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }

    private static class UpHandler extends EditorWriteActionHandler {
        private UpHandler() {
        }

        @Override
        public void executeWriteAction(Editor editor, @Nullable Caret caret, DataContext dataContext) {
            Document document = editor.getDocument();

            String extension = Objects.requireNonNull(FileDocumentManager.getInstance().getFile(document)).getExtension();
            if (!(extension != null && extension.toLowerCase().equals("go"))){
                return;
            }

            if (!document.isWritable()) {
                return;
            }

            CaretModel caretModel = editor.getCaretModel();
            SelectionModel selectionModel = editor.getSelectionModel();

            int selectedLineNumber = document.getLineNumber(selectionModel.getSelectionEnd());

            TextRange lineRange = new TextRange(
                    document.getLineStartOffset(selectedLineNumber),
                    document.getLineEndOffset(selectedLineNumber)
            );

            String selectedLine = document.getText().substring(lineRange.getStartOffset(), lineRange.getEndOffset()).trim();

            String[] params = selectedLine.split("\\.");
            if (params.length != 2){
                return;
            }
            if (!document.getText().contains(" " + params[0] + " ")){
                return;
            }

            String pattern;
            if (Pattern.matches("\\S+[(].*[)].*", params[1])) {
                pattern = "func (this *%s) %s {\n\t\n}";
            } else if (Pattern.matches("\\S+", params[1])) {
                pattern = "func (this *%s) %s() {\n\t\n}";
            } else {
                return;
            }

            document.replaceString(
                    lineRange.getStartOffset(),
                    lineRange.getEndOffset(),
                    String.format(pattern, params[0], params[1])
            );

            caretModel.moveToOffset(caretModel.getOffset()-2);
        }
    }
}
