package ua.tarch64.shared.helpers

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.DataConstants
import com.intellij.openapi.project.Project

object ProjectHelper {
    val active = createMemoFunc {
        val dataContext = DataManager.getInstance().dataContext
        return@createMemoFunc dataContext.getData(DataConstants.PROJECT) as Project
    }
}