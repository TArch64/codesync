package ua.tarch64.shared.helpers

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.project.isDirectoryBased

object ProjectHelper {
    val active = createMemoFunc {
        val dataContext = DataManager.getInstance().dataContextFromFocusAsync.blockingGet(5000)!!
        return@createMemoFunc dataContext.getData(PlatformDataKeys.PROJECT) as Project
    }

    val rootDir = createMemoFunc {
        val project = this.active()
        val projectFileDir = project.projectFile!!.parent
        if (project.isDirectoryBased) return@createMemoFunc projectFileDir.parent
        return@createMemoFunc projectFileDir
    }
}