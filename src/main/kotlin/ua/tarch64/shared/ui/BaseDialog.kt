package ua.tarch64.shared.ui

import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent
import javax.swing.JPanel

abstract class BaseDialog: DialogWrapper(true) {
    init { this.init() }

    override fun createCenterPanel(): JComponent? = this.render()

    protected abstract fun render(): JPanel
}