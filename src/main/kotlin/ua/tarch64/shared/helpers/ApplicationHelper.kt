package ua.tarch64.shared.helpers

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction

object ApplicationHelper {
    fun <T> runReadAction(action: () -> T): T? {
        var actionResult: T? = null
        ApplicationManager.getApplication().runReadAction { actionResult = action() }
        return actionResult
    }

    fun runWriteAction(action: () -> Unit) {
        WriteCommandAction.runWriteCommandAction(ProjectHelper.active(), action)
    }
}