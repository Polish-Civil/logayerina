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
    @JvmStatic
     var config = Config()

    enum class Level(val color: Color) {
        DEBUG(Color.PINK), INFO(Color.WHITE), FINE(Color.GREEN), WARN(Color.YELLOW), ERROR(Color.ORANGE), FATAL(Color.RED),
    }

    fun log(level: Level, msg: Any?, classLabel: Class<*> = CallingClass.get()) {
        val className = (classLabel).name.split("$")[0]
        for (filter in config.filters) {

        }
    }

    @JvmStatic
    @JvmOverloads
    fun info(msg: Any?, classLabel: Class<*> = CallingClass.get()) {
        log(Level.INFO, msg, classLabel)
    }

    class Config(level: Level = Level.INFO) {
        internal val filters = mutableListOf<Pair<Level, String>>()

        fun set(level:Level, classPackageName:String){

        }

        fun set(level:Level, clazz:Class<*>){
            set(level, clazz.name)
        }

//        fun enable(`package`: Package) {
//            enable(`package`.name)
//        }
//
//        fun disable(`package`: Package) {
//            disable(`package`.name)
//        }
    }

    private object CallingClass : SecurityManager() {
        fun get(): Class<*> = classContext[1]
    }
}


