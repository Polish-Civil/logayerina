package logger

import org.slf4j.event.Level
import java.time.LocalTime
import java.time.temporal.ChronoUnit

object Log {
    data class Policy(val path: String, val logLevel: Level)

    class Config(val defaultLogLevel: Level = Level.INFO) {
        internal val logPolicies = mutableListOf<Policy>()

        fun addPolicy(logPolicy: Policy) {
            logPolicies.add(logPolicy)
        }

        fun addPolicy(path: String, level: Level) {
            addPolicy(Policy(path, level))
        }

        fun addPolicy(clazz: Class<*>, level: Level) {
            addPolicy(clazz.name, level)
        }
    }

    private object CallingClass : SecurityManager() {
        fun get(index:Int): Class<*> {
            return classContext[index]
        }
    }

    @JvmStatic
    var config = Config()

    fun log(level: Level, message: Any?, throwable: Throwable?, classLabel: Class<*>) {
        var mostSpecificPolicy = Policy(path = "", config.defaultLogLevel)
        val topClassName = (classLabel).name.split("$")[0]//handles anon classes unless somehow a package has a "$"
        for (policy in config.logPolicies) {
            if (topClassName.contains(policy.path) && mostSpecificPolicy.path.length <= policy.path.length) {//<= because prefer last
                mostSpecificPolicy = policy
            }
        }
        if (mostSpecificPolicy.logLevel < level) return

        val time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS)
        val packageInitials = classLabel.packageName.split(".").map { it[0] }.joinToString(separator = ".")
        val classSimpleName = classLabel.simpleName
        var fullMessage = if(message is Throwable){
            message.stackTraceToString()
        }else{
            message.toString()
        }
        if (throwable != null) {
            fullMessage += "\n" + throwable.stackTraceToString()
        }
        val formattedMessage = "$time $level $packageInitials.$classSimpleName $fullMessage"

        println(formattedMessage)
    }

    @JvmStatic
    @JvmOverloads
    fun debug(message: Any?, classLabel: Class<*> = CallingClass.get(3)) {
        log(Level.DEBUG, message, null, classLabel)
    }

    @JvmStatic
    @JvmOverloads
    fun info(message: Any?, classLabel: Class<*> = CallingClass.get(3)) {
        log(Level.INFO, message, null, classLabel)
    }

    @JvmStatic
    @JvmOverloads
    fun warn(message: Any?, throwable: Throwable? = null, classLabel: Class<*> = CallingClass.get(3)) {
        log(Level.WARN, message, throwable, classLabel)
    }

    @JvmStatic
    @JvmOverloads
    fun error(message: Any?, throwable: Throwable? = null, classLabel: Class<*> = CallingClass.get(3)) {
        log(Level.ERROR, message, throwable, classLabel)
    }
}
