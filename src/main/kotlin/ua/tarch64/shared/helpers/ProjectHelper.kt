package ua.tarch64.shared.helpers

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.DataConstants
import com.intellij.openapi.project.Project

object ProjectHelper {
    fun active(): Project {
        val dataContext = DataManager.getInstance().dataContext
        return dataContext.getData(DataConstants.PROJECT) as Project
    }
}