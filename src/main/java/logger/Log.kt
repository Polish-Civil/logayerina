package logger

import java.awt.Color

/*
requirements

filter by class

filter by package

unfilter by package

unfilter by class

x override class

groups

 */

fun main() {
    Log.info("ook")
}

object Log {
    class Config(level: Level = Level.INFO) {

    }

    var config = Config()

    enum class Level(val color: Color) {
        DEBUG(Color.PINK), INFO(Color.WHITE), FINE(Color.GREEN), WARN(Color.YELLOW), ERROR(Color.ORANGE), FATAL(Color.RED),
    }

    private fun log(level: Level, msg: Any?, filterOverride: Class<*>? = null) {
        val className = (filterOverride ?: CallingClass.get()).name.split("$")[0]
    }

    @JvmStatic
    @JvmOverloads
    fun info(msg: Any?, filterOverride: Class<*>? = null) {
        log(Level.INFO, msg)
    }
}

private object CallingClass : SecurityManager() {
    fun get(): Class<*> = classContext[1]
}
