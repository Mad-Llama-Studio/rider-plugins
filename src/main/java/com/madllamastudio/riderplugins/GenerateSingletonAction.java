package com.madllamastudio.riderplugins;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class GenerateSingletonAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project currentProject = e.getProject();
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor != null) {
            Document document = editor.getDocument();
            VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
            if (virtualFile == null) {
                Messages.showInfoMessage("Cannot find virtual file", "Error");
                return;
            }
            CaretModel caretModel = editor.getCaretModel();
            Caret primaryCaret = caretModel.getPrimaryCaret();
            LogicalPosition logicalPosition = primaryCaret.getLogicalPosition();
            int caretOffset = primaryCaret.getOffset();
            String fileName = virtualFile.getName();
            if (!fileName.endsWith(".cs")){
                Messages.showInfoMessage("File is not a C# file", "Error");
                return;
            }
            String className = fileName.substring(0,fileName.length()-3);
            String tabs = repeat(logicalPosition.column / 4, "\t");
            WriteCommandAction.runWriteCommandAction(currentProject, () ->
                    document.insertString(caretOffset, "#region Singleton \n\n" +
                            tabs + "private static " + className + " _instance;\n\n" +
                            tabs + "public static " + className + " Instance { \n" +
                            tabs + "\tget => _instance; \n" +
                            tabs + "\tset => _instance = value;\n" +
                            tabs + "}\n\n" +
                            tabs + "private void Awake(){\n" +
                            tabs + "\tif (_instance) return;\n" +
                            tabs + "\t_instance = this;\n" +
                            tabs + "}\n\n" +
                            tabs + "#endregion \n")
            );
        } else {
            Messages.showInfoMessage("No point in editor selected", "Singleton Not Generated");
        }
    }

    public static String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

}
