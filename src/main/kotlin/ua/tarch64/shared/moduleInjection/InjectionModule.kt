package ua.tarch64.shared.moduleInjection

open class InjectionModule: IInjectionModule {
    override fun <T> injectModule(clazz: Class<T>): T {
        return ModuleInjector(clazz).inject()
    }
}

interface IInjectionModule {
    fun <T> injectModule(clazz: Class<T>): T
}