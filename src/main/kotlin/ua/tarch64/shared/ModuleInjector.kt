package ua.tarch64.shared

import kotlin.reflect.KClass

typealias ModulesMap<T> = MutableMap<Class<T>, T>

class ModuleInjector<T>(private val clazz: Class<T>) {
    fun inject(): T {
        if ( this.isNewModule() ) {
            this.modules()[this.clazz] = this.clazz.newInstance()
        }
        return this.modules()[this.clazz]!!
    }

    private fun modules(): ModulesMap<T> = modules as ModulesMap<T>
    private fun isNewModule(): Boolean = !this.modules().containsKey(this.clazz)

    companion object {
        private val modules: ModulesMap<Any> = mutableMapOf()

        inline fun <reified T: Any> inject(): T = inject(T::class)
        fun <T: Any> inject(clazz: KClass<T>): T = ModuleInjector(clazz.java).inject()
    }
}